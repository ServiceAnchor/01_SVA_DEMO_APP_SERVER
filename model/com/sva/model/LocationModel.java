package com.sva.model;

import java.math.BigDecimal;

public class LocationModel
{
    private String idType;

    private BigDecimal timestamp;

    private String dataType;

    private BigDecimal x;

    private BigDecimal y;

    private BigDecimal z;

    private String userID;

    private String path;

    private String xo;

    private String yo;

    private String scale;

    public String getXo()
    {
        return xo;
    }

    public void setXo(String xo)
    {
        this.xo = xo;
    }

    public String getYo()
    {
        return yo;
    }

    public void setYo(String yo)
    {
        this.yo = yo;
    }

    public String getScale()
    {
        return scale;
    }

    public void setScale(String scale)
    {
        this.scale = scale;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getIdType()
    {
        return idType;
    }

    public void setIdType(String idType)
    {
        this.idType = idType;
    }

    public BigDecimal getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(BigDecimal timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public BigDecimal getX()
    {
        return x;
    }

    public void setX(BigDecimal x)
    {
        this.x = x;
    }

    public BigDecimal getY()
    {
        return y;
    }

    public void setY(BigDecimal y)
    {
        this.y = y;
    }

    public BigDecimal getZ()
    {
        return z;
    }

    public void setZ(BigDecimal z)
    {
        this.z = z;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

}
