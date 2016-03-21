package com.example.zjb.bamin.Bchangtukepiao.constant;

/**
 * Created by Shane on 2016/1/16.
 */
public class Constant
{

    public static final class RequestAndResultCode
    {
        /**
         * --***********请求码**********--
         */
        //选择出发地及止目的地页面的相关请求码
        public static final int REQUEST_CODE_CHOOSE_SET_OUT = 1;
        public static final int REQUEST_CODE_CHOOSE_ARRIVE = 2;

        /**
         * --***********返回码**********--
         */
        //选择出发地址相关的返回码
        public static final int RESULT_CODE_SET_OUT_ADDR = 1;
        public static final int RESULT_CODE_SEARCH_ADDR = 2;
        public static final int RESULT_CODE_COMMONLY_USED_ADDR = 3;
        //选择目的地相关的返回码
        public static final int RESULT_CODE_ARRIVE_ADDR = 4;
        public static final int RESULT_CODE_ARRIVE_SEARCH_ADDR = 5;
        public static final int RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR = 6;

    }

    public static final class IntentKey
    {
        public static final String NEW_YEAR = "newyear";
        public static final String NEW_MONTH = "newmonth";
        public static final String NEW_DAY_OF_MONTH = "newday_of_month";

        public static final String CURR_YEAR = "year";
        public static final String CURR_MONTH = "month";
        public static final String CURR_DAY_OF_MONTH = "day_of_month";

        public static final String FINAIL_SET_OUT_STATION = "set_out_station";
        public static final String FINAIL_ARRIVE_STATION = "arrive_station";

        //选择出发地址相关的Key
        public static final String KEY_SET_OUT_ZONE_NAME = "SetOutZoneName";
        //选择目的地址相关的Key
        public static final String KEY_ARRIVE_ZONE_NAME = "ArriveZoneName";
    }

    public class URL
    {
        public static final String HOST = "http://www.aiton.com.cn:808/JDTTicket.asmx/";
        //获取所属公司下的
        public static final String GET_COMPANY_SUBZONE = HOST + "GetCompanySubZone?companyCode=";
        public static final String GET_ZONES_TREE = HOST + "GetZonesTree";
    }

    public class URLFromAiTon
    {
        //艾通服务器地址
        public static final String HOST_TEST = "http://120.24.46.15:8080/bmpw/";
        //八闽服务器地址
        public static final String HOST = "http://120.55.166.203:8080/bmpw/";
        //获取所属公司下的
        public static final String GET_COMPANY_SUBZONE = HOST + "front/loadsetoutzone";
        //获取所属公司下的
        public static final String GET_ZONE_STREE = HOST + "front/loadarrivezone";
        //查询所有站点
        public static final String GET_SITE = HOST + "front/loadsites";
        //服务器上Banner的地址
        public static final String GET_BANNER_IMG = HOST + "picture/getpictures";
        //红包
        public static final String RED_PACKET = HOST + "/front/getRedEnvelope";

        //检测软件升级-八闽云服务地址
        public static final String UP_GRADE = HOST + "apk/upgradea.txt";
    }

    public class UrlCode
    {
        public static final String COMPANY_CODE_YONGAN = "YongAn";
    }

    /**
     * --------真实WebViewUrl网址------Start--------
     */
    public class WebViewURL
    {
        //购票须知
        public static final String TICKET_NOTICE = "http://120.24.46.15:8080/bmpw/front/goupiaoxuzhi";
        //取票须知
        public static final String TAKE_TICKETS = "http://120.24.46.15:8080/bmpw/front/qupiaoxuzhi";
        //常见问题
        public static final String NORMAL_PROBLEM = "http://192.168.1.10:8080/bmpw/ooo";
        //票务的软件介绍
        public static final String SOFTWARE_INFO = "http://q.eqxiu.com/s/16f5zj5p";
        //八闽出行_三明绿者卡
        public static final String EVETHING_LDCARD = "http://q.eqxiu.com/s/B3xTuwQc";
        //八闽出行_软件介绍
        public static final String EVETHING_SOFTWARE_INFO = "http://u.eqxiu.com/s/bOwsz5Xr";
        //关于我们
        public static final String ABOUT_US = "http://192.168.1.10:8080/bmpw/ooo";
        //意见反馈
        public static final String FEED_BACK = "http://120.24.46.15:8080/bmpw/front/addfeedback";
        //退票须知
        public static final String BACK_TICKET = "http://120.24.46.15:8080/bmpw/front/tuipiaoxuzhi";
    }
    /**--------真实WebViewUrl网址------End--------*/


}
