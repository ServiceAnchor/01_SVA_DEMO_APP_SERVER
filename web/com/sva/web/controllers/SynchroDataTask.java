package com.sva.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sva.common.HttpUtil;
import com.sva.common.conf.GlobalConf;

public class SynchroDataTask extends HttpServlet
{

    private static Logger Log = Logger.getLogger(SynchroDataTask.class);

    private static final long serialVersionUID = 1L;

    private TaskThread synchroDataThread;

    private String ip;

    private String user;

    private String password;

    private String pythonPath;

    public SynchroDataTask()
    {
    }

    public void init()
    {

        pythonPath = this.getServletContext().getRealPath("/WEB-INF")
                + "\\python\\";
        Log.debug("get python Path:" + pythonPath);
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
        ip = properties.getProperty("sva.ip");
        user = properties.getProperty("sva.app.user");
        password = properties.getProperty("sva.app.password");
        GlobalConf.ip = ip;
        GlobalConf.user = user;
        GlobalConf.password = password;

        String dir = pythonPath;
        pythonPath = properties.getProperty("python.path") + "\\python "
                + pythonPath + "GetLocationData.py";
        Log.debug("load properties:\nip:" + ip + ",\nuser:" + user
                + ",\npassword:" + password);
        Log.debug("pythonPath:" + pythonPath);
        String str = null;
        if (str == null && synchroDataThread == null)
        {
            synchroDataThread = new TaskThread(ip, user, password, pythonPath,
                    dir);
            Log.debug("TaskThread start.");
            synchroDataThread.start(); // servlet 上下文初始化时启动 socket
            Log.debug("TaskThread end.");
        }
    }

    public void doGet(HttpServletRequest httpservletrequest,
            HttpServletResponse httpservletresponse) throws ServletException,
            IOException
    {
    }

    public void destory()
    {
        if (synchroDataThread != null && synchroDataThread.isInterrupted())
        {
            Log.debug("TaskThread destory.");
            synchroDataThread.ProcessDestroy();
            synchroDataThread.interrupt();
        }
    }

}

/**
 * 自定义一个 Class 线程类继承自线程类，重写 run() 方法，用于从后台获取并处理数据
 * 
 * @author tWX219835
 */
class TaskThread extends Thread
{
    private static Logger Log = Logger.getLogger(TaskThread.class);

    private String ip;

    private String user;

    private String password;

    private String pythonPath;

    private String dir;

    private Process proc = null;

    public TaskThread(String ip, String user, String password,
            String pythonPath, String dir)
    {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.pythonPath = pythonPath;
        this.dir = dir;
    }

    public void ProcessDestroy()
    {
        if (proc != null)
        {
            proc.exitValue();
            proc.destroy();
            Log.debug("proc destroy000000");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void destroy()
    {
        super.destroy();
        if (proc != null)
        {
            ProcessDestroy();
            Log.debug("proc destroy");
        }
    }

    public void run()
    {

        while (!this.isInterrupted())
        {// 线程未中断执行循环
            try
            {
                String url = "https://" + ip + ":9001/v3/auth/tokens";
                String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + user + "\",\"password\": \"" + password + "\"}}}}}";
                String charset = "UTF-8";
                HttpUtil capi = new HttpUtil();
                String token = capi.httpsPost(url, content, charset);
                url = "https://"
                        + ip
                        + ":9001/enabler/catalog/locationstreamanonymousreg/json/v1.0";
                content = "{\"APPID\":\"" + user + "\"}";
                String jsonStr = capi.subscription(url, content, token, "POST");
                JSONObject jsonObj = JSONObject.fromObject(jsonStr);
                JSONArray list = jsonObj.getJSONArray("Subscribe Information");
                JSONObject obj = (JSONObject) list.get(0);
                String appid = obj.getString("APPID");
                String port = obj.getString("BROKER_PORT");
                // String ip = obj.getString("BROKER_IP");
                String queue = obj.getString("QUEUE_ID");
                String cmd = pythonPath + " " + appid + " " + ip + " " + port
                        + " " + queue + " " + appid;
                Log.debug("cmd:" + cmd);
                File file = new File(dir);
                proc = Runtime.getRuntime().exec(cmd, null, file);
                Log.debug("runtime exe returnCode:" + proc.waitFor());
                Thread.sleep(2000);
            }
            catch (IOException e)

            {
                Log.error("IOException.", e);
            }
            catch (InterruptedException e)
            {
                Log.error("InterruptedException.", e);
            }
            catch (KeyManagementException e)
            {
                Log.error("KeyManagementException.", e);
            }
            catch (NoSuchAlgorithmException e)
            {
                Log.error("NoSuchAlgorithmException.", e);
            }
            finally
            {
                if (proc != null)
                {
                    proc.destroy();
                }
            }
        }
    }
}
