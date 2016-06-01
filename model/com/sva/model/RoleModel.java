package com.sva.model;

import java.util.Date;

public class RoleModel
{
    private int id;

    private String name;

    private String stores;

    private String storesid;

    private String menues;

    private String menukey;

    private Date updateTime;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getStores()
    {
        return stores;
    }

    public void setStores(String stores)
    {
        this.stores = stores;
    }

    public String getMenues()
    {
        return menues;
    }

    public void setMenues(String menues)
    {
        this.menues = menues;
    }

    public String getStoresid()
    {
        return storesid;
    }

    public void setStoresid(String storesid)
    {
        this.storesid = storesid;
    }

    public String getMenukey()
    {
        return menukey;
    }

    public void setMenukey(String menukey)
    {
        this.menukey = menukey;
    }

}
