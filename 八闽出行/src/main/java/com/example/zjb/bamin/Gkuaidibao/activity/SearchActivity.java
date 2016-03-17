package com.example.zjb.bamin.Gkuaidibao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Gkuaidibao.model.KuaiDiCompanyCode;
import com.example.zjb.bamin.Gkuaidibao.model.KuaiDiInfo;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Zalipay.Base64;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private String CodeData = "[{\"kuaiDiCode\":\"7TLSWL\",\"kuaiDiCompany\":\"7天连锁物流\"},{\"kuaiDiCode\":\"AJ\",\"kuaiDiCompany\":\"安捷快递\"},{\"kuaiDiCode\":\"ANE\",\"kuaiDiCompany\":\"安能物流\"},{\"kuaiDiCode\":\"AXD\",\"kuaiDiCompany\":\"安信达快递\"},{\"kuaiDiCode\":\"BALUNZHI\",\"kuaiDiCompany\":\"巴伦支快递\"},{\"kuaiDiCode\":\"BFDF\",\"kuaiDiCompany\":\"百福东方\"},{\"kuaiDiCode\":\"BKWL\",\"kuaiDiCompany\":\"宝凯物流\"},{\"kuaiDiCode\":\"BQXHM\",\"kuaiDiCompany\":\"北青小红帽\"},{\"kuaiDiCode\":\"BSWL\",\"kuaiDiCompany\":\"邦送物流\"},{\"kuaiDiCode\":\"BTWL\",\"kuaiDiCompany\":\"百世物流\"},{\"kuaiDiCode\":\"CCES\",\"kuaiDiCompany\":\"CCES快递\"},{\"kuaiDiCode\":\"CITY100\",\"kuaiDiCompany\":\"城市100\"},{\"kuaiDiCode\":\"COE\",\"kuaiDiCompany\":\"COE东方快递\"},{\"kuaiDiCode\":\"CSCY\",\"kuaiDiCompany\":\"长沙创一\"},{\"kuaiDiCode\":\"CXWL\",\"kuaiDiCompany\":\"传喜物流\"},{\"kuaiDiCode\":\"DBL\",\"kuaiDiCompany\":\"德邦\"},{\"kuaiDiCode\":\"DCWL\",\"kuaiDiCompany\":\"德创物流\"},{\"kuaiDiCode\":\"DHWL\",\"kuaiDiCompany\":\"东红物流\"},{\"kuaiDiCode\":\"DSWL\",\"kuaiDiCompany\":\"D速物流\"},{\"kuaiDiCode\":\"DTKD\",\"kuaiDiCompany\":\"店通快递\"},{\"kuaiDiCode\":\"DTWL\",\"kuaiDiCompany\":\"大田物流\"},{\"kuaiDiCode\":\"DYWL\",\"kuaiDiCompany\":\"大洋物流快递\"},{\"kuaiDiCode\":\"EMS\",\"kuaiDiCompany\":\"EMS\"},{\"kuaiDiCode\":\"FAST\",\"kuaiDiCompany\":\"快捷速递\"},{\"kuaiDiCode\":\"FBKD\",\"kuaiDiCompany\":\"飞豹快递\"},{\"kuaiDiCode\":\"FEDEX\",\"kuaiDiCompany\":\"FedEx联邦快递\"},{\"kuaiDiCode\":\"FHKD\",\"kuaiDiCompany\":\"飞狐快递\"},{\"kuaiDiCode\":\"FKD\",\"kuaiDiCompany\":\"飞康达\"},{\"kuaiDiCode\":\"FYPS\",\"kuaiDiCompany\":\"飞远配送\"},{\"kuaiDiCode\":\"FYSD\",\"kuaiDiCompany\":\"凡宇速递\"},{\"kuaiDiCode\":\"GDEMS\",\"kuaiDiCompany\":\"广东邮政\"},{\"kuaiDiCode\":\"GDKD\",\"kuaiDiCompany\":\"冠达快递\"},{\"kuaiDiCode\":\"GHX\",\"kuaiDiCompany\":\"挂号信\"},{\"kuaiDiCode\":\"GKSD\",\"kuaiDiCompany\":\"港快速递\"},{\"kuaiDiCode\":\"GSD\",\"kuaiDiCompany\":\"共速达\"},{\"kuaiDiCode\":\"GTKD\",\"kuaiDiCompany\":\"广通速递\"},{\"kuaiDiCode\":\"GTO\",\"kuaiDiCompany\":\"国通快递\"},{\"kuaiDiCode\":\"GTSD\",\"kuaiDiCompany\":\"高铁速递\"},{\"kuaiDiCode\":\"HBJH\",\"kuaiDiCompany\":\"河北建华\"},{\"kuaiDiCode\":\"HFWL\",\"kuaiDiCompany\":\"汇丰物流\"},{\"kuaiDiCode\":\"HHKD\",\"kuaiDiCompany\":\"华航快递\"},{\"kuaiDiCode\":\"HHTT\",\"kuaiDiCompany\":\"天天快递\"},{\"kuaiDiCode\":\"HLKD\",\"kuaiDiCompany\":\"韩润物流\"},{\"kuaiDiCode\":\"HLWL\",\"kuaiDiCompany\":\"恒路物流\"},{\"kuaiDiCode\":\"HMJKD\",\"kuaiDiCompany\":\"黄马甲快递\"},{\"kuaiDiCode\":\"HMSD\",\"kuaiDiCompany\":\"海盟速递\"},{\"kuaiDiCode\":\"HOAU\",\"kuaiDiCompany\":\"天地华宇\"},{\"kuaiDiCode\":\"hq568\",\"kuaiDiCompany\":\"华强物流\"},{\"kuaiDiCode\":\"HQKY\",\"kuaiDiCompany\":\"华企快运\"},{\"kuaiDiCode\":\"HSWL\",\"kuaiDiCompany\":\"昊盛物流\"},{\"kuaiDiCode\":\"HTKY\",\"kuaiDiCompany\":\"百世汇通\"},{\"kuaiDiCode\":\"HTWL\",\"kuaiDiCompany\":\"户通物流\"},{\"kuaiDiCode\":\"HXLWL\",\"kuaiDiCompany\":\"华夏龙物流\"},{\"kuaiDiCode\":\"HYLSD\",\"kuaiDiCompany\":\"好来运快递\"},{\"kuaiDiCode\":\"JD\",\"kuaiDiCompany\":\"京东快递\"},{\"kuaiDiCode\":\"JGSD\",\"kuaiDiCompany\":\"京广速递\"},{\"kuaiDiCode\":\"JIUYE\",\"kuaiDiCompany\":\"九曳供应链\"},{\"kuaiDiCode\":\"JJKY\",\"kuaiDiCompany\":\"佳吉快运\"},{\"kuaiDiCode\":\"JLDT\",\"kuaiDiCompany\":\"嘉里大通\"},{\"kuaiDiCode\":\"JTKD\",\"kuaiDiCompany\":\"捷特快递\"},{\"kuaiDiCode\":\"JXD\",\"kuaiDiCompany\":\"急先达\"},{\"kuaiDiCode\":\"JYKD\",\"kuaiDiCompany\":\"晋越快递\"},{\"kuaiDiCode\":\"JYM\",\"kuaiDiCompany\":\"加运美\"},{\"kuaiDiCode\":\"JYSD\",\"kuaiDiCompany\":\"久易快递\"},{\"kuaiDiCode\":\"JYWL\",\"kuaiDiCompany\":\"佳怡物流\"},{\"kuaiDiCode\":\"KLWL\",\"kuaiDiCompany\":\"康力物流\"},{\"kuaiDiCode\":\"KTKD\",\"kuaiDiCompany\":\"快淘快递\"},{\"kuaiDiCode\":\"KYDSD\",\"kuaiDiCompany\":\"快优达速递\"},{\"kuaiDiCode\":\"KYWL\",\"kuaiDiCompany\":\"跨越速递\"},{\"kuaiDiCode\":\"LB\",\"kuaiDiCompany\":\"龙邦快递\"},{\"kuaiDiCode\":\"LBKD\",\"kuaiDiCompany\":\"联邦快递\"},{\"kuaiDiCode\":\"LHKD\",\"kuaiDiCompany\":\"蓝弧快递\"},{\"kuaiDiCode\":\"LHT\",\"kuaiDiCompany\":\"联昊通速递\"},{\"kuaiDiCode\":\"LJD\",\"kuaiDiCompany\":\"乐捷递\"},{\"kuaiDiCode\":\"LJS\",\"kuaiDiCompany\":\"立即送\"},{\"kuaiDiCode\":\"MB\",\"kuaiDiCompany\":\"民邦速递\"},{\"kuaiDiCode\":\"MDM\",\"kuaiDiCompany\":\"门对门\"},{\"kuaiDiCode\":\"MHKD\",\"kuaiDiCompany\":\"民航快递\"},{\"kuaiDiCode\":\"MLWL\",\"kuaiDiCompany\":\"明亮物流\"},{\"kuaiDiCode\":\"MSKD\",\"kuaiDiCompany\":\"闽盛快递\"},{\"kuaiDiCode\":\"NEDA\",\"kuaiDiCompany\":\"能达速递\"},{\"kuaiDiCode\":\"NJSBWL\",\"kuaiDiCompany\":\"南京晟邦物流\"},{\"kuaiDiCode\":\"PADTF\",\"kuaiDiCompany\":\"平安达腾飞快递\"},{\"kuaiDiCode\":\"PXWL\",\"kuaiDiCompany\":\"陪行物流\"},{\"kuaiDiCode\":\"QCKD\",\"kuaiDiCompany\":\"全晨快递\"},{\"kuaiDiCode\":\"QFKD\",\"kuaiDiCompany\":\"全峰快递\"},{\"kuaiDiCode\":\"QRT\",\"kuaiDiCompany\":\"全日通快递\"},{\"kuaiDiCode\":\"RFD\",\"kuaiDiCompany\":\"如风达\"},{\"kuaiDiCode\":\"RLWL\",\"kuaiDiCompany\":\"日昱物流\"},{\"kuaiDiCode\":\"SAD\",\"kuaiDiCompany\":\"赛澳递\"},{\"kuaiDiCode\":\"SAWL\",\"kuaiDiCompany\":\"圣安物流\"},{\"kuaiDiCode\":\"SBWL\",\"kuaiDiCompany\":\"盛邦物流\"},{\"kuaiDiCode\":\"SDHH\",\"kuaiDiCompany\":\"山东海红\"},{\"kuaiDiCode\":\"SDWL\",\"kuaiDiCompany\":\"上大物流\"},{\"kuaiDiCode\":\"SF\",\"kuaiDiCompany\":\"顺丰快递\"},{\"kuaiDiCode\":\"SFWL\",\"kuaiDiCompany\":\"盛丰物流\"},{\"kuaiDiCode\":\"SHLDHY\",\"kuaiDiCompany\":\"上海林道货运\"},{\"kuaiDiCode\":\"SHWL\",\"kuaiDiCompany\":\"盛辉物流\"},{\"kuaiDiCode\":\"SJWL\",\"kuaiDiCompany\":\"穗佳物流\"},{\"kuaiDiCode\":\"ST\",\"kuaiDiCompany\":\"速通物流\"},{\"kuaiDiCode\":\"STO\",\"kuaiDiCompany\":\"申通快递\"},{\"kuaiDiCode\":\"STSD\",\"kuaiDiCompany\":\"三态速递\"},{\"kuaiDiCode\":\"SURE\",\"kuaiDiCompany\":\"速尔快递\"},{\"kuaiDiCode\":\"SXHMJ\",\"kuaiDiCompany\":\"山西红马甲\"},{\"kuaiDiCode\":\"SYJHE\",\"kuaiDiCompany\":\"沈阳佳惠尔\"},{\"kuaiDiCode\":\"SYKD\",\"kuaiDiCompany\":\"世运快递\"},{\"kuaiDiCode\":\"THTX\",\"kuaiDiCompany\":\"通和天下\"},{\"kuaiDiCode\":\"TSSTO\",\"kuaiDiCompany\":\"唐山申通\"},{\"kuaiDiCode\":\"UAPEX\",\"kuaiDiCompany\":\"全一快递\"},{\"kuaiDiCode\":\"UC\",\"kuaiDiCompany\":\"优速快递\"},{\"kuaiDiCode\":\"WJWL\",\"kuaiDiCompany\":\"万家物流\"},{\"kuaiDiCode\":\"WTP\",\"kuaiDiCompany\":\"微特派\"},{\"kuaiDiCode\":\"WXWL\",\"kuaiDiCompany\":\"万象物流\"},{\"kuaiDiCode\":\"XBWL\",\"kuaiDiCompany\":\"新邦物流\"},{\"kuaiDiCode\":\"XFEX\",\"kuaiDiCompany\":\"信丰快递\"},{\"kuaiDiCode\":\"XGYZ\",\"kuaiDiCompany\":\"香港邮政\"},{\"kuaiDiCode\":\"XLYT\",\"kuaiDiCompany\":\"祥龙运通\"},{\"kuaiDiCode\":\"XYT\",\"kuaiDiCompany\":\"希优特\"},{\"kuaiDiCode\":\"YADEX\",\"kuaiDiCompany\":\"源安达快递\"},{\"kuaiDiCode\":\"YBJ\",\"kuaiDiCompany\":\"邮必佳\"},{\"kuaiDiCode\":\"YCWL\",\"kuaiDiCompany\":\"远成物流\"},{\"kuaiDiCode\":\"YD\",\"kuaiDiCompany\":\"韵达快递\"},{\"kuaiDiCode\":\"YDH\",\"kuaiDiCompany\":\"义达国际物流\"},{\"kuaiDiCode\":\"YFEX\",\"kuaiDiCompany\":\"越丰物流\"},{\"kuaiDiCode\":\"YFHEX\",\"kuaiDiCompany\":\"原飞航物流\"},{\"kuaiDiCode\":\"YFSD\",\"kuaiDiCompany\":\"亚风快递\"},{\"kuaiDiCode\":\"YJSD\",\"kuaiDiCompany\":\"银捷速递\"},{\"kuaiDiCode\":\"YLSY\",\"kuaiDiCompany\":\"亿领速运\"},{\"kuaiDiCode\":\"YMWL\",\"kuaiDiCompany\":\"英脉物流\"},{\"kuaiDiCode\":\"YSH\",\"kuaiDiCompany\":\"亿顺航\"},{\"kuaiDiCode\":\"YSKY\",\"kuaiDiCompany\":\"音素快运\"},{\"kuaiDiCode\":\"YTD\",\"kuaiDiCompany\":\"易通达\"},{\"kuaiDiCode\":\"YTFH\",\"kuaiDiCompany\":\"一统飞鸿\"},{\"kuaiDiCode\":\"YTKD\",\"kuaiDiCompany\":\"运通快递\"},{\"kuaiDiCode\":\"YTO\",\"kuaiDiCompany\":\"圆通速递\"},{\"kuaiDiCode\":\"YXWL\",\"kuaiDiCompany\":\"宇鑫物流\"},{\"kuaiDiCode\":\"YZPY\",\"kuaiDiCompany\":\"邮政平邮/小包\"},{\"kuaiDiCode\":\"ZENY\",\"kuaiDiCompany\":\"增益快递\"},{\"kuaiDiCode\":\"ZHQKD\",\"kuaiDiCompany\":\"汇强快递\"},{\"kuaiDiCode\":\"ZJS\",\"kuaiDiCompany\":\"宅急送\"},{\"kuaiDiCode\":\"ZMKM\",\"kuaiDiCompany\":\"芝麻开门\"},{\"kuaiDiCode\":\"ZRSD\",\"kuaiDiCompany\":\"中睿速递\"},{\"kuaiDiCode\":\"ZTE\",\"kuaiDiCompany\":\"众通快递\"},{\"kuaiDiCode\":\"ZTKY\",\"kuaiDiCompany\":\"中铁快运\"},{\"kuaiDiCode\":\"ZTO\",\"kuaiDiCompany\":\"中通速递\"},{\"kuaiDiCode\":\"ZTWL\",\"kuaiDiCompany\":\"中铁物流\"},{\"kuaiDiCode\":\"ZTWY\",\"kuaiDiCompany\":\"中天万运\"},{\"kuaiDiCode\":\"ZWYSD\",\"kuaiDiCompany\":\"中外运速递\"},{\"kuaiDiCode\":\"ZYWL\",\"kuaiDiCompany\":\"中邮物流\"},{\"kuaiDiCode\":\"ZZJH\",\"kuaiDiCompany\":\"郑州建华\"}]";
    private List<KuaiDiCompanyCode> mKuaiDiCompanyCodeList = new ArrayList<>();
    private AutoCompleteTextView mEditText_kuaidi_company;
    private AutoCompleteTextView mEditText_kuaidi_code;
    //电商ID
    private String EBusinessID = "1256347";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "64def526-4098-44f3-8415-dcd1d919064c";
    //请求url
    private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    private String companyCode;
    private String mLonghistory;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        findID();
        initUI();
        setListener();
    }

    private void initData() {
        Type type = new TypeToken<ArrayList<KuaiDiCompanyCode>>() {
        }.getType();
        mKuaiDiCompanyCodeList = GsonUtils.parseJSONArray(CodeData, type);
    }

    private void setListener() {
        findViewById(R.id.button_search).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
    }

    private void initUI() {
        String[] search = new String[mKuaiDiCompanyCodeList.size()];
        for (int i = 0; i < mKuaiDiCompanyCodeList.size(); i++) {
            search[i] = mKuaiDiCompanyCodeList.get(i).getKuaiDiCompany();
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, search);
        mEditText_kuaidi_company.setAdapter(stringArrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initHistoryCodeSP();
        initAutoComplete("history", mEditText_kuaidi_code);
    }

    /**
     * 初始化AutoCompleteTextView，最多显示5项提示，使
     * AutoCompleteTextView在一开始获得焦点时自动提示
     *
     * @param field 保存在sharedPreference中的字段名
     * @param auto  要操作的AutoCompleteTextView
     */
    private void initAutoComplete(String field, AutoCompleteTextView auto) {
        auto.setAdapter(mAdapter);
        auto.setThreshold(1);
//        auto.setCompletionHint("最近的5条记录");
//        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                AutoCompleteTextView view = (AutoCompleteTextView) v;
//                if (hasFocus) {
//                    view.showDropDown();
//                }
//            }
//        });
    }

    private void initHistoryCodeSP() {
        SharedPreferences sp = getSharedPreferences("network_url", 0);
        mLonghistory = sp.getString("history", "最近的5条记录");
        String[] hisArrays = mLonghistory.split(",");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, hisArrays);
        //只保留最近的50条的记录
        if (hisArrays.length > 50) {
            String[] newArrays = new String[50];
            System.arraycopy(hisArrays, 0, newArrays, 0, 50);
            for (int i = 0; i < newArrays.length; i++) {
                mLonghistory = newArrays[i] + ",";
            }
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, newArrays);
        }
    }

    private void findID() {
        mEditText_kuaidi_company = (AutoCompleteTextView) findViewById(R.id.editText_kuaidi_company);
        mEditText_kuaidi_code = (AutoCompleteTextView) findViewById(R.id.editText_kuaidi_code);
    }


    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    public void getOrderTracesByJson() throws Exception {
        String requestData = "{'OrderCode':'','ShipperCode':'" + companyCode + "','LogisticCode':'" + mEditText_kuaidi_code.getText().toString().trim() + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.button_search:
                companyCode = "";
                for (int i = 0; i < mKuaiDiCompanyCodeList.size(); i++) {
                    if (mEditText_kuaidi_company.getText().toString().trim().equals(mKuaiDiCompanyCodeList.get(i).getKuaiDiCompany())) {
                        companyCode = mKuaiDiCompanyCodeList.get(i).getKuaiDiCode();
                        break;
                    }
                }
                if ("".equals(companyCode)) {
                    Toast.makeText(SearchActivity.this, "请输入正确的快递公司名称", Toast.LENGTH_SHORT).show();
                }else if ("".equals(mEditText_kuaidi_code.getText().toString().trim())){
                    Toast.makeText(SearchActivity.this, "请输入快递单号", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Log.e("onClick ", "onClick??????????");
                        getOrderTracesByJson();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private void sendPost(String url, Map<String, String> params) {
        HTTPUtils.post(SearchActivity.this, url, params, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SearchActivity.this, "网络异常或正在维护", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "s" + s);
                // 这里可以设定：当搜索成功时，才执行保存操作
                saveHistory("history", mEditText_kuaidi_code);
                KuaiDiInfo kuaiDiInfo = GsonUtils.parseJSON(s, KuaiDiInfo.class);
                Intent intent = new Intent();
                intent.putExtra("kuaiDiInfo", kuaiDiInfo);
                intent.setClass(SearchActivity.this, KuaiDiDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
     *
     * @param field 保存在sharedPreference中的字段名
     * @param auto  要操作的AutoCompleteTextView
     */
    private void saveHistory(String field, AutoCompleteTextView auto) {
        String text = auto.getText().toString();
        SharedPreferences sp = getSharedPreferences("network_url", 0);
        if (!mLonghistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(mLonghistory);
            sb.insert(0, text + ",");
            sp.edit().putString("history", sb.toString()).commit();
        }
    }
}
