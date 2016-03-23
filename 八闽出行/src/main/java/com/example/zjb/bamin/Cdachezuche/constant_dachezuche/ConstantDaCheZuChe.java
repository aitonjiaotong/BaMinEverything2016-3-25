package com.example.zjb.bamin.Cdachezuche.constant_dachezuche;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ConstantDaCheZuChe
{
    /**
     * --------打车租车服务器网址--------------
     */
    public class Url
    {
        public static final String HOST = "http://120.24.46.15:8080/bmpw/";
        //取车城市列表接口地址 传入的参数:page 默认值0
        public static final String CITY_LIST = HOST + "zc/store/loadcities";

        //机构认证服务接口 String code,String password，code为机构编号  password为密码，返回true和false
        public static final String DACHEZUCHE_COMFIRE_UNIT_INFO = HOST + "/zc/institutions/checkinstitutions";

        //司机列表接口地址 传入的参数:page 默认值0
        public static final String DRIVER_LIST = HOST + "zc/driver/loadfreedriver";

        //获取车辆信息传入参数Integer lei
        public static final String GET_CAR_INFO = HOST +"zc/order/loadcarbylei";

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
        //选择车型返回码
        public static final int CHOOSE_CAR_TYPE_RETRUN_CODE = 6;

        //选择司机的请求码
        public static final int CHOOSE_DRIVER_REQUEST_CODE = 7;
        //选择司机的返回码
        public static final int CHOOSE_DRIVER_RESULT_CODE = 8;
    }

    public class IntentKey
    {
        //跳转到门店地图_取车城市的KEY
        public static final String CITY = "city_name";

        public static final String GET_MAP_LOC_KEY = "get_map_loc";
        //跳转到门店地图_取车的KEY
        public static final int GET_MAP_LOC_GET = 1;
        //跳转到门店地图_还车的KEY
        public static final int GET_MAP_LOC_RETURN = 2;

        //选择城市地区的KEY
        public static final String CHOOSE_CITY = "choose_city";

        //取车门店返回值的KEY
        public static final String STORES_MAP_KEY = "stores_map_marker_title";

        //选择司机的返回值的KEY
        public static final String DRIVER_NAME = "driverName";
        public static final String DRIVER_ID = "driverID";

        //第一次选择时要传递的对象值的KEY
        public static final String CHOOSE_FRIST_INFO = "choose_frist_info";

    }
}
