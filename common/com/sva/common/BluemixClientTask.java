package com.sva.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sva.common.conf.GlobalConf;
import com.sva.common.conf.Params;
import com.sva.dao.BluemixDao;
import com.sva.model.BluemixModel;

public class BluemixClientTask
{

    private static Logger Log = Logger.getLogger(BluemixClientTask.class);

    @Autowired
    private BluemixDao bluemixDao;

    private String pythonPath;

    public void init()
    {
        String path = getClass().getResource("/").getPath();
        String separator = "/";
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows"))
        {
            path = path.substring(1, path.indexOf("/classes"));
        }
        else
        {
            path = path.substring(0, path.indexOf("/classes"));
        }
        pythonPath = path + separator + "python" + separator;
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
        String pathConf = properties.getProperty("python.path");
        if (StringUtils.isEmpty(pathConf))
        {
            pythonPath = "python " + pythonPath + "BluemixClient.py";
        }
        else
        {
            pythonPath = properties.getProperty("python.path") + "\\python "
                    + pythonPath + "BluemixClient.py";
        }
        Collection<BluemixModel> list = null;
        for (int i = 0; i < 1; i++)
        {
            list = bluemixDao.queryAllStatus();
            try
            {
                for (BluemixModel client : list)
                {
                    if (!checkThreadIsRun(client))
                    {
                        BluemixClientTaskThread thread = new BluemixClientTaskThread(
                                client, pythonPath, dir);
                        GlobalConf.addBluemixClientThreadPool(thread);
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
    private boolean checkThreadIsRun(BluemixModel client)
    {
        BluemixClientTaskThread currThread = null;
        int globaSize = GlobalConf.getBluemixClientThreadPoolSize();
        BluemixClientTaskThread thread = null;
        for (int i = 0; i < globaSize; i++)
        {
            thread = (BluemixClientTaskThread) GlobalConf
                    .getBluemixClientThreadPool(i);
            if (client.getId() == thread.getBluemixModel().getId())
            {
                currThread = thread;
            }
        }

        if (currThread != null)
        {
            // 当前线程不为空，线程需要停止同步数据
            if (client.getStatus() == 0)
            {
                currThread.destroyProcess();
                GlobalConf.removeBluemixClientThreadPool(currThread);
                return true;
            }
            else
            {
                if (!(Thread.State.TERMINATED == currThread.getState()))
                {
                    return true;
                }
                else
                {
                    currThread.destroyProcess();
                    GlobalConf.removeBluemixClientThreadPool(currThread);
                }
            }
        }

        if (client.getStatus() == Params.STATUS_DELETE)
        {
            try
            {
                bluemixDao.deleteById(String.valueOf(client.getId()));
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                Log.error("bluemix delete SQLException.", e);
            }
            catch (Exception e)
            {
                Log.error("Exception.", e);
            }
            // 不需要启动
            return true;
        }

        if (client.getStatus() == Params.STATUS_STOP)
        {
            // 不需要启动
            return true;
        }

        return false;
    }

}
