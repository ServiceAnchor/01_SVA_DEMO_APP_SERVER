package com.sva.model;

import java.math.BigDecimal;

/**
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
public class LocationDelay
{

    private int id;

    private String place;

    private int placeId;

    private BigDecimal floorNo;

    private String floor;

    private double dataDelay;

    private double positionDelay;

    private long updateTime;

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public long getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(long updateTime)
    {
        this.updateTime = updateTime;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getDataDelay()
    {
        return dataDelay;
    }

    public void setDataDelay(double dataDelay)
    {
        this.dataDelay = dataDelay;
    }

    public double getPositionDelay()
    {
        return positionDelay;
    }

    public void setPositionDelay(double positionDelay)
    {
        this.positionDelay = positionDelay;
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

}
