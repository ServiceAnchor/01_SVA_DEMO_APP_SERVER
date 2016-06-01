package com.sva.web.models;

import java.math.BigDecimal;

public class AccuracyApiModel
{
    private int id;

    private int placeId;

    private String floorNo;

    private String origin;

    private String destination;

    private String startdate;

    private String enddate;

    private String triggerIp;

    private BigDecimal offset;

    private BigDecimal variance;

    private int count3;

    private int count5;

    private int count10;

    private int count10p;

    private String detail;

    private String type;

    private BigDecimal averDevi;

    public int getCount3()
    {
        return count3;
    }

    public void setCount3(int count3)
    {
        this.count3 = count3;
    }

    public int getCount5()
    {
        return count5;
    }

    public void setCount5(int count5)
    {
        this.count5 = count5;
    }

    public int getCount10()
    {
        return count10;
    }

    public void setCount10(int count10)
    {
        this.count10 = count10;
    }

    public int getCount10p()
    {
        return count10p;
    }

    public void setCount10p(int count10p)
    {
        this.count10p = count10p;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getStartdate()
    {
        return startdate;
    }

    public void setStartdate(String startdate)
    {
        this.startdate = startdate;
    }

    public String getEnddate()
    {
        return enddate;
    }

    public void setEnddate(String enddate)
    {
        this.enddate = enddate;
    }

    public String getTriggerIp()
    {
        return triggerIp;
    }

    public void setTriggerIp(String triggerIp)
    {
        this.triggerIp = triggerIp;
    }

    public BigDecimal getOffset()
    {
        return offset;
    }

    public void setOffset(BigDecimal offset)
    {
        this.offset = offset;
    }

    public BigDecimal getVariance()
    {
        return variance;
    }

    public void setVariance(BigDecimal variance)
    {
        this.variance = variance;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public BigDecimal getAverDevi()
    {
        return averDevi;
    }

    public void setAverDevi(BigDecimal averDevi)
    {
        this.averDevi = averDevi;
    }
}
