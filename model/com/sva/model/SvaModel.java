package com.sva.model;

import java.util.List;

public class SvaModel
{
    private String id;

    private String ip;

    private String name;

    private String username;

    private String password;

    private List<String> position;

    private String storeId;

    private int status;

    private int type;

    private String tokenProt;

    private String brokerProt;

    private String token;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getTokenProt()
    {
        return tokenProt;
    }

    public void setTokenProt(String tokenProt)
    {
        this.tokenProt = tokenProt;
    }

    public String getBrokerProt()
    {
        return brokerProt;
    }

    public void setBrokerProt(String brokerProt)
    {
        this.brokerProt = brokerProt;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getPosition()
    {
        return position;
    }

    public void setPosition(List<String> position)
    {
        this.position = position;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    /***
     * 比较SVA配置信息是否改变，比较内容为ID、IP、appName、password
     * 
     * @param sva
     * @return
     */
    public boolean isChange(SvaModel sva)
    {
        if (this.id.equals(sva.getId()) && this.ip.equals(sva.getIp())
                && this.username.equals(sva.getUsername())
                && this.password.equals(sva.getPassword()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String getStoreId()
    {
        return storeId;
    }

    public void setStoreId(String storeId)
    {
        this.storeId = storeId;
    }

}
