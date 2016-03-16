package com.example.zjb.bamin.Cdachezuche.models;

/**
 * Created by Administrator on 2016/3/16.
 */
public class StoresMarkerInfo
{
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //标题内容
    private String title;

    public StoresMarkerInfo()
    {
    }

    public StoresMarkerInfo(double latitude, double longitude, String title)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoresMarkerInfo that = (StoresMarkerInfo) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        return title.equals(that.title);

    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "StoresMarkerInfo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", title='" + title + '\'' +
                '}';
    }
}
