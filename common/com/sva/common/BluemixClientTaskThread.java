package com.sva.common;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sva.model.BluemixModel;

/**
 * 自定义一个 Class 线程类继承自线程类，重写 run() 方法，用于从后台获取并处理数据
 * 
 * @author tWX219835
 */
public class BluemixClientTaskThread extends Thread
{
    private static Logger Log = Logger.getLogger(BluemixClientTaskThread.class);

    private BluemixModel client;

    private String pythonPath;

    private String dir;

    private Process proc = null;

    private boolean isStop = false;

    public BluemixModel getBluemixModel()
    {
        return client;
    }

    public void setSva(BluemixModel client)
    {
        this.client = client;
    }

    public BluemixClientTaskThread(BluemixModel client, String pythonPath,
            String dir)
    {
        this.client = client;
        this.pythonPath = pythonPath;
        this.dir = dir;
    }

    public void destroyProcess()
    {
        isStop = true;
        unsubscribe();
        if (proc != null)
        {
            proc.destroy();
            proc = null;
            Log.debug("BluemixClientTaskThread destroyProcess.svaId:"
                    + client.getId() + ",ip:" + client.getIp());
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
        if (client.getToken() == null || "".equals(client.getToken()))
        {
            return;
        }
        String url = "";
        String content = "";
        HttpUtil capi = new HttpUtil();

        try
        {
            url = "https://" + client.getIp() + ':' + client.getTokenProt()
                    + "/enabler/catalog/locationstreamunreg/json/v1.0";
            content = "{\"APPID\":\"" + client.getSvaUser() + "\"}";
            String jsonStr = capi.subscription(url, content, client.getToken(),
                    "DELETE");
            Log.debug("unsubscribe,jsonStr:" + jsonStr);

            url = "https://" + client.getIp() + ':' + client.getTokenProt()
                    + "/enabler/catalog/locationstreamanonymousunreg/json/v1.0";
            content = "{\"APPID\":\"" + client.getSvaUser() + "\"}";
            jsonStr = capi.subscription(url, content, client.getToken(),
                    "DELETE");
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
        File file = null;
        String charset = null;
        String url = null;
        String content = null;
        String token = null;
        String appid = null;
        String queue = null;
        String cmd = null;
        HttpUtil capi = null;
        while (!isStop && !this.isInterrupted())
        {
            // 线程未中断执行循环
            try
            {
                Thread.sleep(2000);
                Log.debug("runtime BluemixClient currentThreadId:"
                        + Thread.currentThread().getId() + ",id:"
                        + client.getId() + ",ip:" + client.getIp());
                url = "https://" + client.getIp() + ':' + client.getTokenProt()
                        + "/v3/auth/tokens";
                content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + client.getSvaUser()
                        + "\",\"password\": \""
                        + client.getSvaPassword() + "\"}}}}}";
                charset = "UTF-8";
                capi = new HttpUtil();

                token = capi.httpsPost(url, content, charset);
                url = "https://" + client.getIp() + ':' + client.getTokenProt()
                        + "/enabler/catalog/locationstreamreg/json/v1.0";
                content = "{\"APPID\":\"" + client.getSvaUser() + "\"}";
                String jsonStr = capi.subscription(url, content, token, "POST");
                JSONObject jsonObj = JSONObject.fromObject(jsonStr);
                JSONArray list = jsonObj.getJSONArray("Subscribe Information");
                JSONObject obj = (JSONObject) list.get(0);
                appid = obj.getString("APPID");
                // String port = obj.getString("BROKER_PORT");
                // String ip = obj.getString("BROKER_IP");
                queue = obj.getString("QUEUE_ID");
                /*
                 * String appid = "app0"; String port = "4703"; String queue =
                 * "app0.ssfgerjhndlskdhf";
                 */
                /*
                 * appid + IP + port + queue + url + Site + IbmUser +
                 * IbmPassword + appid
                 */
                cmd = pythonPath + ' ' + appid + ' ' + client.getIp() + ' '
                        + client.getBrokerProt() + ' ' + queue + ' '
                        + client.getUrl() + ' ' + client.getSite() + ' '
                        + client.getIbmUser() + ' ' + client.getIbmPassword()
                        + " Bluemix" + appid;
                Log.debug("BluemixClient cmd:" + cmd);

                file = new File(dir);
                proc = Runtime.getRuntime().exec(cmd, null, file);

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