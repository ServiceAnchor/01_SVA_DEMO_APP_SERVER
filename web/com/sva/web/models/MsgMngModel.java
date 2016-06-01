package com.sva.web.models;

import java.math.BigDecimal;

public class MsgMngModel
{
    private String place;

    private String placeId;

    private String shopName;

    private int timeInterval;

    private BigDecimal xSpot;

    private BigDecimal ySpot;

    private BigDecimal x1Spot;

    private BigDecimal y1Spot;

    private String floor;

    private BigDecimal rangeSpot;

    private String pictruePath;

    private String moviePath;

    private String id;

    private String floorNo;

    private int shopId;

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

    public int getShopId()
    {
        return shopId;
    }

    public void setShopId(int shopId)
    {
        this.shopId = shopId;
    }

    public String getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(String placeId)
    {
        this.placeId = placeId;
    }

    public String getFloorNo()
    {
        return floorNo;
    }

    public void setFloorNo(String floorNo)
    {
        this.floorNo = floorNo;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPictruePath()
    {
        return pictruePath;
    }

    public void setPictruePath(String pictruePath)
    {
        this.pictruePath = pictruePath;
    }

    public String getMoviePath()
    {
        return moviePath;
    }

    public void setMoviePath(String moviePath)
    {
        this.moviePath = moviePath;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public BigDecimal getRangeSpot()
    {
        return rangeSpot;
    }

    public void setRangeSpot(BigDecimal rangeSpot)
    {
        this.rangeSpot = rangeSpot;
    }

    private String message;

    private String isEnable;

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
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

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getIsEnable()
    {
        return isEnable;
    }

    public void setIsEnable(String isEnable)
    {
        this.isEnable = isEnable;
    }

    public int getTimeInterval()
    {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval)
    {
        this.timeInterval = timeInterval;
    }

}
