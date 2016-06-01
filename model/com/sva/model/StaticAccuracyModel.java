package com.sva.model;

import java.math.BigDecimal;
import java.util.Date;

public class StaticAccuracyModel
{
    private int id;

    private String place;

    private int placeId;

    private BigDecimal floorNo;

    private String floor;

    private String origin;// 起点

    private String destination; // 终点

    private Date startdate;

    private Date enddate;

    private BigDecimal avgeOffset; // 平均误差

    private BigDecimal maxOffset; // 最大误差

    private BigDecimal staicAccuracy; // 静态精度

    private BigDecimal offsetCenter; // 偏移重心

    private BigDecimal offsetNumber; // 偏移量

    private BigDecimal stability; // 稳定度

    private String triggerIp;

    private int count3;

    private int count5;

    private int count10;

    private int count10p;

    private String detail;

    public String getTriggerIp()
    {
        return triggerIp;
    }

    public void setTriggerIp(String triggerIp)
    {
        this.triggerIp = triggerIp;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public BigDecimal getAvgeOffset()
    {
        return avgeOffset;
    }

    public void setAvgeOffset(BigDecimal avgeOffset)
    {
        this.avgeOffset = avgeOffset;
    }

    public BigDecimal getMaxOffset()
    {
        return maxOffset;
    }

    public void setMaxOffset(BigDecimal maxOffset)
    {
        this.maxOffset = maxOffset;
    }

    public BigDecimal getStaicAccuracy()
    {
        return staicAccuracy;
    }

    public void setStaicAccuracy(BigDecimal staicAccuracy)
    {
        this.staicAccuracy = staicAccuracy;
    }

    public BigDecimal getOffsetCenter()
    {
        return offsetCenter;
    }

    public void setOffsetCenter(BigDecimal offsetCenter)
    {
        this.offsetCenter = offsetCenter;
    }

    public BigDecimal getOffsetNumber()
    {
        return offsetNumber;
    }

    public void setOffsetNumber(BigDecimal offsetNumber)
    {
        this.offsetNumber = offsetNumber;
    }

    public BigDecimal getStability()
    {
        return stability;
    }

    public void setStability(BigDecimal stability)
    {
        this.stability = stability;
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

}
