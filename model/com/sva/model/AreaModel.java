package com.sva.model;

import java.math.BigDecimal;

public class AreaModel
{
    private String id;

    private String place;

    private int placeId;

    private String floor;

    private int categoryId;

    private String category;

    private BigDecimal floorNo;

    private String areaName;

    private BigDecimal xSpot;

    private BigDecimal ySpot;

    private BigDecimal x1Spot;

    private BigDecimal y1Spot;

    private int status;

    private int zoneId;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public int getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(int placeId)
    {
        this.placeId = placeId;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public BigDecimal getFloorNo()
    {
        return floorNo;
    }

    public void setFloorNo(BigDecimal floorNo)
    {
        this.floorNo = floorNo;
    }

    public BigDecimal getxSpot()
    {
        return xSpot;
    }

    public void setxSpot(BigDecimal xSpot)
    {
        this.xSpot = xSpot;
    }

    public BigDecimal getySpot()
    {
        return ySpot;
    }

    public void setySpot(BigDecimal ySpot)
    {
        this.ySpot = ySpot;
    }

    public BigDecimal getX1Spot()
    {
        return x1Spot;
    }

    public void setX1Spot(BigDecimal x1Spot)
    {
        this.x1Spot = x1Spot;
    }

    public BigDecimal getY1Spot()
    {
        return y1Spot;
    }

    public void setY1Spot(BigDecimal y1Spot)
    {
        this.y1Spot = y1Spot;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

}
