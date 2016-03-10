package com.example.zjb.bamin.Abusline.busline_aition_constants;

/**
 * Created by Administrator on 2015-12-16.
 */
public class MyConstants
{
    public class Str
    {
        public static final String CITY = "厦门";
    }

    public class IntentKey
    {
        public static final String INPUT_TYPE_KEY = "inputType";
        public static final String CHOOSE_CITY_KEY = "choosedCity";
    }

    public class RequestAndResultCode
    {
        public static final int ME_FRAGMENT_CHOOSE_CITY_REQUEST = 0;
        public static final int CHOOSE_CITY_ACTIVITY_RESUL = 1;
        //选择出发地请求码
        public static final int CHOOSE_START = 0;
        //选择目地地请求码
        public static final int CHOOSE_ARRIVE = 1;
        //返回码
        public static final int CHOOSED = 2;
    }
}
