package com.sva.model;

import java.util.Date;

public class ParamModel
{
    private int id;

    private Double banThreshold; // 禁止门限

    private Double filterLength; // 滤波窗长

    private Double horizontalWeight; // 横向权重

    private Double ongitudinalWeight; // 纵向权重

    private Double maxDeviation; // 最大偏离

    private Double exceedMaxDeviation; // 超过最大偏移

    private Double correctMapDirection; // 矫正地图方向

    private Double restTimes; // 静止次数

    private Double filterPeakError; // 过滤波峰误差

    private long updateTime; // 修改时间

    private Double peakWidth; // 波峰阔值

    private Double step; // 步长

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

    public Double getBanThreshold()
    {
        return banThreshold;
    }

    public void setBanThreshold(Double banThreshold)
    {
        this.banThreshold = banThreshold;
    }

    public Double getFilterLength()
    {
        return filterLength;
    }

    public void setFilterLength(Double filterLength)
    {
        this.filterLength = filterLength;
    }

    public Double getHorizontalWeight()
    {
        return horizontalWeight;
    }

    public void setHorizontalWeight(Double horizontalWeight)
    {
        this.horizontalWeight = horizontalWeight;
    }

    public Double getOngitudinalWeight()
    {
        return ongitudinalWeight;
    }

    public void setOngitudinalWeight(Double ongitudinalWeight)
    {
        this.ongitudinalWeight = ongitudinalWeight;
    }

    public Double getMaxDeviation()
    {
        return maxDeviation;
    }

    public void setMaxDeviation(Double maxDeviation)
    {
        this.maxDeviation = maxDeviation;
    }

    public Double getExceedMaxDeviation()
    {
        return exceedMaxDeviation;
    }

    public void setExceedMaxDeviation(Double exceedMaxDeviation)
    {
        this.exceedMaxDeviation = exceedMaxDeviation;
    }

    public Double getCorrectMapDirection()
    {
        return correctMapDirection;
    }

    public void setCorrectMapDirection(Double correctMapDirection)
    {
        this.correctMapDirection = correctMapDirection;
    }

    public Double getRestTimes()
    {
        return restTimes;
    }

    public void setRestTimes(Double restTimes)
    {
        this.restTimes = restTimes;
    }

    public Double getFilterPeakError()
    {
        return filterPeakError;
    }

    public void setFilterPeakError(Double filterPeakError)
    {
        this.filterPeakError = filterPeakError;
    }

    public Double getPeakWidth()
    {
        return peakWidth;
    }

    public void setPeakWidth(Double peakWidth)
    {
        this.peakWidth = peakWidth;
    }

    public Double getStep()
    {
        return step;
    }

    public void setStep(Double step)
    {
        this.step = step;
    }

}
