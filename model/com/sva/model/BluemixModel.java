package com.sva.model;

import java.util.Date;

public class BluemixModel
{
    private int id;

    private String ip;

    private String svaUser;

    private String svaPassword;

    private String url;

    private String site;

    private String ibmUser;

    private String ibmPassword;

    private int status;

    private Date updateTime;

    private Date createTime;

    private String token;

    private String tokenProt;

    private String brokerProt;

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

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getSvaUser()
    {
        return svaUser;
    }

    public void setSvaUser(String svaUser)
    {
        this.svaUser = svaUser;
    }

    public String getSvaPassword()
    {
        return svaPassword;
    }

    public void setSvaPassword(String svaPassword)
    {
        this.svaPassword = svaPassword;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    public String getIbmUser()
    {
        return ibmUser;
    }

    public void setIbmUser(String ibmUser)
    {
        this.ibmUser = ibmUser;
    }

    public String getIbmPassword()
    {
        return ibmPassword;
    }

    public void setIbmPassword(String ibmPassword)
    {
        this.ibmPassword = ibmPassword;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

}
