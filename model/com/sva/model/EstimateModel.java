package com.sva.model;

import java.math.BigDecimal;

public class EstimateModel
{
    private String id;

    private String place;

    private int placeId;

    private BigDecimal floorNo;

    private String floor;

    private BigDecimal a;

    private BigDecimal b;

    private int n;

    private BigDecimal type;

    private BigDecimal d;

    private BigDecimal deviation;

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

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public BigDecimal getA()
    {
        return a;
    }

    public void setA(BigDecimal a)
    {
        this.a = a;
    }

    public BigDecimal getB()
    {
        return b;
    }

    public void setB(BigDecimal b)
    {
        this.b = b;
    }

    public int getN()
    {
        return n;
    }

    public void setN(int n)
    {
        this.n = n;
    }

    public BigDecimal getType()
    {
        return type;
    }

    public void setType(BigDecimal type)
    {
        this.type = type;
    }

    public BigDecimal getD()
    {
        return d;
    }

    public void setD(BigDecimal d)
    {
        this.d = d;
    }

    public BigDecimal getDeviation()
    {
        return deviation;
    }

    public void setDeviation(BigDecimal deviation)
    {
        this.deviation = deviation;
    }

}
