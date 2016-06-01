package com.sva.model;

import java.math.BigDecimal;

/**
 * 
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
public class MessagePush
{
    private int id;

    private int placeId;

    private BigDecimal floorNo;

    private String floor;

    private String place;

    private double pushRight;

    private double pushWrong;

    private double notPush;

    private String centerRadius;

    private String centerReality;

    private int isRigth;

    private long updateTime;

    public String getCenterRadius()
    {
        return centerRadius;
    }

    public void setCenterRadius(String centerRadius)
    {
        this.centerRadius = centerRadius;
    }

    public long getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(long updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(int placeId)
    {
        this.placeId = placeId;
    }

    public BigDecimal getFloorNo()
    {
        return floorNo;
    }

    public void setFloorNo(BigDecimal floorNo)
    {
        this.floorNo = floorNo;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public double getPushRight()
    {
        return pushRight;
    }

    public void setPushRight(double pushRight)
    {
        this.pushRight = pushRight;
    }

    public double getPushWrong()
    {
        return pushWrong;
    }

    public void setPushWrong(double pushWrong)
    {
        this.pushWrong = pushWrong;
    }

    public double getNotPush()
    {
        return notPush;
    }

    public void setNotPush(double notPush)
    {
        this.notPush = notPush;
    }

    public String getCenterReality()
    {
        return centerReality;
    }

    public void setCenterReality(String centerReality)
    {
        this.centerReality = centerReality;
    }

    public int getIsRigth()
    {
        return isRigth;
    }

    public void setIsRigth(int isRigth)
    {
        this.isRigth = isRigth;
    }

}
