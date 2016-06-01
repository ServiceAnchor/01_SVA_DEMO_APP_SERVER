package com.sva.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccuracyModel
{
    private int id;

    private String place;

    private int placeId;

    private BigDecimal floorNo;

    private String floor;

    private String origin;

    private String destination;

    private Date startdate;

    private Date enddate;

    private String triggerIp;

    private BigDecimal offset;

    private BigDecimal variance;

    private BigDecimal deviation;

    private int count3;

    private int count5;

    private int count10;

    private int count10p;

    private String detail;

    private String type;

    private BigDecimal averDevi;

    public Date getStartdate()
    {
        return startdate;
    }

    public void setStartdate(Date startdate)
    {
        this.startdate = startdate;
    }

    public Date getEnddate()
    {
        return enddate;
    }

    public void setEnddate(Date enddate)
    {
        this.enddate = enddate;
    }

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

    public BigDecimal getDeviation()
    {
        return deviation;
    }

    public void setDeviation(BigDecimal deviation)
    {
        this.deviation = deviation;
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
