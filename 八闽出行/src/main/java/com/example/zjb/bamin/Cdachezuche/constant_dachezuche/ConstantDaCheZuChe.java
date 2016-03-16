package com.example.zjb.bamin.Cdachezuche.constant_dachezuche;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ConstantDaCheZuChe
{
    /**--------打车租车服务器网址--------------*/
    public class Url
    {
        public static final String DACHEZUCHE_COMFIRE_UNIT_INFO = "";
    }

    public class RequestAndResultCode
    {
        //选择城市地区的请求码
        public static final int CHOOSE_CITY_REQUEST_CODE = 0;

        //选择城市地区的返回码
        public static final int CHOOSE_CITY_RESULT_CODE = 1;

        //取车门店的请求码StoresMapActivity
        public static final int STORES_MAP_GET_REQUEST_CODE = 2;
        //还车门店的请求码StoresMapActivity
        public static final int STORES_MAP_RETRUN_REQUEST_CODE = 3;
        //地图门店选择后的返回码
        public static final int STORES_MAP_RESULT_CODE = 4;

        //选择车型的请求码
        public static final int CHOOSE_CAR_TYPE_REQUEST_CODE = 5;
    }

    public class IntentKey
    {
        public static final String GET_MAP_LOC_KEY = "get_map_loc";
        //跳转到门店地图_取车的KEY
        public static final int GET_MAP_LOC_GET = 1;
        //跳转到门店地图_还车的KEY
        public static final int GET_MAP_LOC_RETURN = 2;

        //选择城市地区的KEY
        public static final String CHOOSE_CITY= "choose_city";

        //取车门店返回值的KEY
        public static final String STORES_MAP_KEY = "stores_map_marker_title";
    }
}
