package com.sva.web.models;

import java.math.BigDecimal;

public class SellerMngModel
{
    private String place;

    private int placeId;

    private BigDecimal xSpot;

    private BigDecimal ySpot;

    private String floor;

    private String info;

    private String isEnable;

    private String pictruePath;

    private String moviePath;

    private String isVip;

    private String id;

    private String floorNo;

    public int getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(int placeId)
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

    public String getIsVip()
    {
        return isVip;
    }

    public void setIsVip(String isVip)
    {
        this.isVip = isVip;
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

    public String getIsEnable()
    {
        return isEnable;
    }

    public void setIsEnable(String isEnable)
    {
        this.isEnable = isEnable;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
}
