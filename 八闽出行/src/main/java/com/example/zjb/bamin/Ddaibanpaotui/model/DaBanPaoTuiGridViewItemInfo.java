package com.example.zjb.bamin.Ddaibanpaotui.model;

/**
 * Created by Administrator on 2016/3/18.
 */
public class DaBanPaoTuiGridViewItemInfo
{
    String name;
    int iconId;

    public DaBanPaoTuiGridViewItemInfo()
    {

    }

    public DaBanPaoTuiGridViewItemInfo(String name, int iconId)
    {
        this.name = name;
        this.iconId = iconId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIconId()
    {
        return iconId;
    }

    public void setIconId(int iconId)
    {
        this.iconId = iconId;
    }

    @Override
    public String toString()
    {
        return "DaBanPaoTuiGridViewItemInfo{" +
                "name='" + name + '\'' +
                ", iconId=" + iconId +
                '}';
    }
}
