package com.sva.model;

public class RegisterModel
{
    private String ip;

    private String userName;

    private String passWord;

    private String phoneNumber;

    private long times;

    private int status;

    private int role;

    private long otherPhone;

    private int isTrue;

    public long getOtherPhone()
    {
        return otherPhone;
    }

    public void setOtherPhone(long otherPhone)
    {
        this.otherPhone = otherPhone;
    }

    public int getIsTrue()
    {
        return isTrue;
    }

    public void setIsTrue(int isTrue)
    {
        this.isTrue = isTrue;
    }

    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
    {
        this.role = role;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public long getTimes()
    {
        return times;
    }

    public void setTimes(long times)
    {
        this.times = times;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

}
