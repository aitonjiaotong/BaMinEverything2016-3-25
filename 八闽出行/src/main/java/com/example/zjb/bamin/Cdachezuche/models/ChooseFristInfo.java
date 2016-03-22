package com.example.zjb.bamin.Cdachezuche.models;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ChooseFristInfo implements Serializable
{
    private String cityName;
    private long getCarTime;
    private long returnCarTime;
    private int hasDriver;
    private int driverID;
    private int carType;

    public ChooseFristInfo(String cityName, long getCarTime, long returnCarTime, int hasDriver, int driverID, int carType)
    {
        this.cityName = cityName;
        this.getCarTime = getCarTime;
        this.returnCarTime = returnCarTime;
        this.hasDriver = hasDriver;
        this.driverID = driverID;
        this.carType = carType;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public long getGetCarTime()
    {
        return getCarTime;
    }

    public void setGetCarTime(long getCarTime)
    {
        this.getCarTime = getCarTime;
    }

    public long getReturnCarTime()
    {
        return returnCarTime;
    }

    public void setReturnCarTime(long returnCarTime)
    {
        this.returnCarTime = returnCarTime;
    }

    public int getHasDriver()
    {
        return hasDriver;
    }

    public void setHasDriver(int hasDriver)
    {
        this.hasDriver = hasDriver;
    }

    public int getDriverID()
    {
        return driverID;
    }

    public void setDriverID(int driverID)
    {
        this.driverID = driverID;
    }

    public int getCarType()
    {
        return carType;
    }

    public void setCarType(int carType)
    {
        this.carType = carType;
    }
}
