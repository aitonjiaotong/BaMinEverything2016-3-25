package com.example.zjb.bamin.Gkuaidibao.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zjb on 2016/3/17.
 */
public class KuaiDiCompanyCode implements Parcelable
{
    private String kuaiDiCode;
    private String kuaiDiCompany;

    protected KuaiDiCompanyCode(Parcel in)
    {
        kuaiDiCode = in.readString();
        kuaiDiCompany = in.readString();
    }

    public static final Creator<KuaiDiCompanyCode> CREATOR = new Creator<KuaiDiCompanyCode>()
    {
        @Override
        public KuaiDiCompanyCode createFromParcel(Parcel in)
        {
            return new KuaiDiCompanyCode(in);
        }

        @Override
        public KuaiDiCompanyCode[] newArray(int size)
        {
            return new KuaiDiCompanyCode[size];
        }
    };

    public String getKuaiDiCode()
    {
        return kuaiDiCode;
    }

    public void setKuaiDiCode(String kuaiDiCode)
    {
        this.kuaiDiCode = kuaiDiCode;
    }

    public String getKuaiDiCompany()
    {
        return kuaiDiCompany;
    }

    public void setKuaiDiCompany(String kuaiDiCompany)
    {
        this.kuaiDiCompany = kuaiDiCompany;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(kuaiDiCode);
        dest.writeString(kuaiDiCompany);
    }
}

