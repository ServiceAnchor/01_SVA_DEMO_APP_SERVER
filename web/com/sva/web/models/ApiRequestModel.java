package com.sva.web.models;

public class ApiRequestModel
{
    /**
     * 手机端ip地址
     */
    private String ip;

    /**
     * 是否推送开关
     */
    private int isPush = 1;

    public int getIsPush()
    {
        return isPush;
    }

    public void setIsPush(int isPush)
    {
        this.isPush = isPush;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

}
