package com.sva.model;

import java.math.BigDecimal;

public class MapsModel
{
    private String scale;

    private String xo;

    private String yo;

    private String floor;

    private String coordinate;

    private float angle;

    private String path;

    private String svg;

    private String route;

    private String pathFile;

    private String updateTime;

    private int imgWidth;

    private int imgHeight;

    private BigDecimal floorNo;

    private int placeId;

    private String place;

    private BigDecimal floorid;

    private String id;

    private int Mapid;

    public int getMapid()
    {
        return Mapid;
    }

    public void setMapid(int mapid)
    {
        Mapid = mapid;
    }

    public String getPathFile()
    {
        return pathFile;
    }

    public void setPathFile(String pathFile)
    {
        this.pathFile = pathFile;
    }

    public String getRoute()
    {
        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getSvg()
    {
        return svg;
    }

    public void setSvg(String svg)
    {
        this.svg = svg;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public BigDecimal getFloorid()
    {
        return floorid;
    }

    public void setFloorid(BigDecimal floorid)
    {
        this.floorid = floorid;
    }

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

    public int getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(int placeId)
    {
        this.placeId = placeId;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getScale()
    {
        return scale;
    }

    public void setScale(String scale)
    {
        this.scale = scale;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public int getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth)
    {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight()
    {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight)
    {
        this.imgHeight = imgHeight;
    }

    public String getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }

    public float getAngle()
    {
        return angle;
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

}
