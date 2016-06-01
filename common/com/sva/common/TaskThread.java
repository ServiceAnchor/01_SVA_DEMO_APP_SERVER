package com.sva.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sva.model.SvaModel;

/**
 * 自定义一个 Class 线程类继承自线程类，重写 run() 方法，用于从后台获取并处理数据
 * 
 * @author tWX219835
 */
class TaskThread extends Thread
{
    private static Logger Log = Logger.getLogger(TaskThread.class);

    private SvaModel sva;

    private String pythonPath;

    private String dir;

    private String db;

    private String isDesignated;

    private String serverId;

    private String type;

    private Process proc = null;

    private boolean isStop = false;

    public SvaModel getSva()
    {
        return sva;
    }

    public void setSva(SvaModel sva)
    {
        this.sva = sva;
    }

    public TaskThread(SvaModel sva, String pythonPath, String dir, String db,
            String isDesignated, String type, String serverId)
    {
        this.sva = sva;
        this.pythonPath = pythonPath;
        this.dir = dir;
        this.db = db;
        this.isDesignated = isDesignated;
        this.type = type;
        this.serverId = serverId;
    }

    public void destroyProcess()
    {
        isStop = true;
        unsubscribe();
        if (proc != null)
        {
            proc.destroy();
            proc = null;
            Log.debug("destroyProcess.svaId:" + sva.getId() + ",ip:"
                    + sva.getIp());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void destroy()
    {
        super.destroy();
        if (proc != null)
        {
            destroyProcess();
            Log.debug("proc destroy");
        }
    }

    public void unsubscribe()
    {
        if (sva.getToken() == null || "".equals(sva.getToken()))
        {
            return;
        }
        String url = "";
        String content = "";
        HttpUtil capi = new HttpUtil();

        try
        {
            url = "https://" + sva.getIp() + ":"
                    + sva.getTokenProt() + "/v3/auth/tokens";
            content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                    + sva.getUsername()
                    + "\",\"password\": \""
                    + sva.getPassword() + "\"}}}}}";
            String charset = "UTF-8";
            String token = capi.httpsPost(url, content, charset);
            sva.setToken(token);
            
            url = "https://" + sva.getIp() + ":" + sva.getTokenProt()
                    + "/enabler/catalog/locationstreamunreg/json/v1.0";
            content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
            String jsonStr = capi.subscription(url, content, sva.getToken(),
                    "DELETE");
            Log.debug("unsubscribe,jsonStr:" + jsonStr);

            url = "https://" + sva.getIp() + ":" + sva.getTokenProt()
                    + "/enabler/catalog/locationstreamanonymousunreg/json/v1.0";
            content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
            jsonStr = capi.subscription(url, content, sva.getToken(), "DELETE");
            Log.debug("unsubscribe,jsonStr:" + jsonStr);

        }
        catch (KeyManagementException e)
        {
            // TODO Auto-generated catch block
            Log.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            Log.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            Log.error("IOException.", e);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            Log.error("Exception.", e);
        }
    }

    public void run()
    {
        while (!isStop && !this.isInterrupted())
        {
            // 线程未中断执行循环
            try
            {
                Thread.sleep(2000);
                Log.debug("runtime currentThreadId:"
                        + Thread.currentThread().getId() + ",svaId:"
                        + sva.getId() + ",ip:" + sva.getIp() + ",port:"
                        + sva.getTokenProt());
                String url = "https://" + sva.getIp() + ":"
                        + sva.getTokenProt() + "/v3/auth/tokens";
                String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + sva.getUsername()
                        + "\",\"password\": \""
                        + sva.getPassword() + "\"}}}}}";
                String charset = "UTF-8";
                HttpUtil capi = new HttpUtil();
                String token = capi.httpsPost(url, content, charset);
                sva.setToken(token);
                if (sva.getType() == 0)
                {
                    url = "https://" + sva.getIp() + ":" + sva.getTokenProt()
                            + "/enabler/catalog/locationstreamreg/json/v1.0";
                    content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
                }
                else if (sva.getType() == 1)
                {
                    url = "https://"
                            + sva.getIp()
                            + ":"
                            + sva.getTokenProt()
                            + "/enabler/catalog/locationstreamanonymousreg/json/v1.0";
                    content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
                }
                else if (sva.getType() == 2)
                {
                    url = "https://" + sva.getIp() + ":" + sva.getTokenProt()
                            + "/enabler/catalog/locationstreamreg/json/v1.0";
                    content = "{\"APPID\":\"" + sva.getUsername()
                            + "\",\"useridlist\":[\""
                            + ConvertUtil.convertMacOrIp("10.10.10.10")
                            + "\"]}";
                }

                String jsonStr = capi.subscription(url, content, token, "POST");
                Log.debug("jsonStr:" + jsonStr);
                JSONObject jsonObj = JSONObject.fromObject(jsonStr);
                Log.debug("jsonObj:" + jsonObj);

                JSONArray list = jsonObj.getJSONArray("Subscribe Information");
                Log.debug("list:" + list);
                JSONObject obj = (JSONObject) list.get(0);
                Log.debug("obj:" + obj);
                String appid = obj.getString("APPID");
                // String port = obj.getString("BROKER_PORT");
                // String ip = obj.getString("BROKER_IP");
                String queue = obj.getString("QUEUE_ID");
                String cmd = "";
                int svaport = Integer.parseInt(sva.getId()) + 50000
                        + Integer.parseInt(serverId) * 1000;
                if ("python".equals(this.type))
                {
                    cmd = pythonPath + " " + appid + " " + sva.getIp() + " "
                            + sva.getBrokerProt() + " " + queue + " "
                            + sva.getStoreId() + " " + this.db + " "
                            + this.isDesignated;
                }
                else if ("cppWindows".equals(this.type))
                {
                    cmd = pythonPath + " " + appid + " " + sva.getIp() + " "
                            + sva.getBrokerProt() + " " + queue + " "
                            + sva.getStoreId() + " " + this.db + " "
                            + this.isDesignated + " 1" + " " + svaport;
                }
                else
                {
                    cmd = pythonPath + " " + appid + " msp.huawei.com "
                            + sva.getBrokerProt() + " " + queue + " "
                            + sva.getStoreId() + " " + this.db + " "
                            + this.isDesignated;
                }

                // String path = getClass().getResource("/").getPath();
                // path = path.substring(1, path.indexOf("/classes"));
                // String cppPath = path + "\\cpp\\";
                // String cmd = cppPath + "GetLocationData " + appid + " " +
                // sva.getIp() + " " + sva.getBrokerProt() + " " + queue + " " +
                // sva.getStoreId() + " " + this.db + " " + this.isDesignated;

                if (sva.getType() == 0)
                {
                    cmd = cmd.replaceAll("GetLocationData",
                            "GetLocationDataForAll");
                }
                Log.debug("cmd:" + cmd);
                Log.debug("file:" + dir);

                File file = new File(dir);
                proc = Runtime.getRuntime().exec(cmd, null, file);

//                InputStreamReader ir = new InputStreamReader(
//                        proc.getInputStream());
//                LineNumberReader input = new LineNumberReader(ir);
//                String line;
//
//                while ((line = input.readLine()) != null)
//                {
//                    if (!"".equals(line))
//                    {
//                        // reponse.add(line);
//                        Log.debug("runtime exe log:" + line);
//                    }
//                }

                Log.debug("runtime exe returnCode:" + proc.waitFor());
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