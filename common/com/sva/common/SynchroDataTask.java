package com.sva.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sva.common.conf.GlobalConf;
import com.sva.common.conf.Params;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;

public class SynchroDataTask
{
    private static Logger Log = Logger.getLogger(SynchroDataTask.class);

    @Autowired
    private SvaDao svaDao;

    @Value("${mysql.db}")
    private String db;

    private static final long serialVersionUID = 1L;

    private String pythonPath;

    public void init()
    {
        String path = getClass().getResource("/").getPath();
        path = path.substring(1, path.indexOf("/classes"));
        pythonPath = path;
        InputStream in = getClass().getClassLoader().getResourceAsStream(
                "sva.properties");
        Properties properties = new Properties();

        try
        {
            properties.load(in);
        }
        catch (IOException e)
        {
            Log.error("load properties ERROR.", e);
        }

        String dir = pythonPath;
        String type = properties.getProperty("exec.type");
        if ("python".equals(type))
        {
            dir = dir + "/python/";
            pythonPath = properties.getProperty("python.path") + "/python "
                    + pythonPath + "/python/GetLocationData.py";
        }
        else if ("cppWindows".equals(type))
        {
            dir = dir + "/cpp/";
            pythonPath = pythonPath + "/cpp/qpid_with_ssl.exe";
        }
        else
        {
            dir = "/" + dir;
            pythonPath = "/" + pythonPath + "/cpp/GetLocationData";
        }

        for (int i = 0; i < 1; i++)
        {
            Collection<SvaModel> list = svaDao.doqueryByAll();
            try
            {
                for (SvaModel sva : list)
                {
                    if (!checkThreadIsRun(sva))
                    {
                        TaskThread thread = new TaskThread(sva, pythonPath,
                                dir, db,
                                properties.getProperty("bashow.isDesignated"),
                                type, properties.getProperty("bashow.serverId",
                                        "1"));
                        GlobalConf.addThreadPool(thread);
                        thread.start();
                    }
                }
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                Log.error("InterruptedException.", e);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                Log.error("Exception.", e);
            }
        }

    }

    /***
     * 检查SVA配置信息是否需要启动数据同步线程 true：需要启动，false：不需要启动
     * 
     * @param sva
     * @return boolean
     */
    private boolean checkThreadIsRun(SvaModel sva)
    {
        TaskThread currThread = null;
        for (int i = 0; i < GlobalConf.getThreadPoolSize(); i++)
        {
            TaskThread thread = (TaskThread) GlobalConf.getThreadPool(i);
            if (sva.getId().equals(thread.getSva().getId()))
            {
                currThread = thread;
            }
        }

        if (currThread != null)
        {
            // 当前线程不为空，线程需要停止同步数据
            if (sva.getStatus() == Params.STATUS_STOP)
            {
                currThread.destroyProcess();
                GlobalConf.removeThreadPool(currThread);
                return true;
            }
            else
            {
                if (sva.isChange(currThread.getSva()))
                {
                    // 修改信息需要重启
                    currThread.destroyProcess();
                    GlobalConf.removeThreadPool(currThread);
                    return false;
                }
                else if (!(Thread.State.TERMINATED == currThread.getState()))
                {
                    return true;
                }
                else
                {
                    currThread.destroyProcess();
                    GlobalConf.removeThreadPool(currThread);
                }
            }
        }

        if (sva.getStatus() == Params.STATUS_DELETE)
        {
            // 删除状态
            try
            {
                if (!StringUtils.isEmpty(sva.getId()))
                {
                    svaDao.deleteMapBySva(Integer.parseInt(sva.getId()));
                }
                svaDao.deleteSva(sva.getId());
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                Log.error("SQLException.", e);
            }
            catch (Exception e)
            {
                Log.error("Exception.", e);
            }
            return true;
        }

        if (sva.getStatus() == Params.STATUS_STOP)
        {
            // 不需要启动
            return true;
        }

        return false;
    }

}
