package com.example.zjb.bamin.Bchangtukepiao.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.models.about_ticket.TicketInfo;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Zutils.DateCompareUtil;
import com.example.zjb.bamin.Zutils.DialogShow;
import com.example.zjb.bamin.Zutils.LogUtil;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Calendar c = Calendar.getInstance();

    private ListView mLv_ticket;
    private TicketListViewAdapter mAdapter = new TicketListViewAdapter();
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private TextView mTv_today;
    private TextView mTv_yesterday;
    private TextView mTv_tomorrow;
    private ImageView mBack;
    private String mResult;
    private List<TicketInfo> mTicketInfoList = new ArrayList<>();
    private String start;
    private String end;
    private String mDateMath;
    private String mCheckedTime;
    private String mCurrentTime;
    private ProgressBar mRefrash;
    private TextView mTv_order_logout;
    private String mDeviceId;
    private String mId;
    //控制车票查询次数，防止金点通偶尔网络异常
    private int queryTicketCount = 0;
    private String mPhoneNum;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        //检查是否在其他设备上登陆登录
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
        mDeviceId = sp.getString("DeviceId", "");
        initIntent();
        initUI();
        initData();
        setOnclick();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initData() {
        mTicketInfoList.clear();
        mRefrash.setVisibility(View.VISIBLE);
        mLv_ticket.setVisibility(View.GONE);
        mTv_order_logout.setVisibility(View.GONE);
        String url_web = Constant.URL.HOST +
                "GetSellableScheduleByStartCityName?executeDate=" + mCheckedTime +
                "&startCityName=" + URLEncoder.encode(start) +
                "&endSiteNamePart=" + URLEncoder.encode(end);
        HTTPUtils.get(TicketActivity.this, url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if (queryTicketCount < 3) {
                    initData();
                    queryTicketCount++;
                } else {
                    Toast.makeText(TicketActivity.this, "票务系统连接中", Toast.LENGTH_SHORT).show();
                }

            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    mResult = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    Type type = new TypeToken<ArrayList<TicketInfo>>() {
                    }.getType();
                    mTicketInfoList = GsonUtils.parseJSONArray(mResult, type);
                    /**
                     * 没票提示
                     */
                    if (mTicketInfoList.size() == 0) {
                        mTv_order_logout.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                    mRefrash.setVisibility(View.GONE);
                    mLv_ticket.setVisibility(View.VISIBLE);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();

        mYear = intent.getIntExtra(Constant.IntentKey.CURR_YEAR, -1);
        mMonth = intent.getIntExtra(Constant.IntentKey.CURR_MONTH, -1);
        mDayOfMonth = intent.getIntExtra(Constant.IntentKey.CURR_DAY_OF_MONTH, -1);
        start = intent.getStringExtra(Constant.IntentKey.FINAIL_SET_OUT_STATION);
        LogUtil.show("initIntent TicketActivity-->start:", start);
        end = intent.getStringExtra(Constant.IntentKey.FINAIL_ARRIVE_STATION);
        LogUtil.show("initIntent TicketActivity-->end", end);
    }

    private void initUI() {
        mBack = (ImageView) findViewById(R.id.iv_back);
        mRefrash = (ProgressBar) findViewById(R.id.refrash);
        mTv_order_logout = (TextView) findViewById(R.id.tv_order_logout);
        initBtnForTranTime();
        initTicketListView();
    }

    private void initBtnForTranTime() {
        mTv_today = (TextView) findViewById(R.id.tv_today);
        mCheckedTime = mYear + "-" + mMonth + "-" + mDayOfMonth;
        mCurrentTime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        mTv_today.setText(mCheckedTime);
        mTv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        mTv_tomorrow = (TextView) findViewById(R.id.tv_tomorrow);
        checkTimeBtn();
    }

    private void checkTimeBtn() {
        try {
            mDateMath = DateCompareUtil.DateMath(mCheckedTime, mCurrentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("等于".equals(mDateMath)) {
            mTv_yesterday.setEnabled(false);
            mTv_yesterday.setTextColor(Color.parseColor("#C0C0C0"));
        } else {
            mTv_yesterday.setEnabled(true);
            mTv_yesterday.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if ("等于10".equals(mDateMath)) {
            mTv_tomorrow.setEnabled(false);
            mTv_tomorrow.setTextColor(Color.parseColor("#C0C0C0"));
        } else {
            mTv_tomorrow.setEnabled(true);
            mTv_tomorrow.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void initTicketListView() {
        mLv_ticket = (ListView) findViewById(R.id.lv_ticket);
        mLv_ticket.setAdapter(mAdapter);
    }

    private void setOnclick() {
        mTv_yesterday.setOnClickListener(this);
        mTv_tomorrow.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTv_today.setOnClickListener(this);
        mLv_ticket.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_today:
                showDatePickerDialog();
                break;
            case R.id.tv_yesterday:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //得到指定模范的时间
                try {
                    Date d = sdf.parse(mCheckedTime);
                    long d1 = d.getTime() - 24 * 3600 * 1000;
                    mCheckedTime = sdf.format(d1);
                    mTv_today.setText(mCheckedTime);
                    checkTimeBtn();
                    updateDate();

                    initData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_tomorrow:
                //得到指定模范的时间
                SimpleDateFormat sdf01 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date d = sdf01.parse(mCheckedTime);
                    long d1 = d.getTime() + 24 * 3600 * 1000;
                    mCheckedTime = sdf01.format(d1);
                    mTv_today.setText(mCheckedTime);
                    checkTimeBtn();
                    updateDate();
                    initData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
        }
    }

    private void updateDate() {
        /**
         * 将字符截成数组
         */
        String[] split = mCheckedTime.split("-");
        mYear = Integer.parseInt(split[0]);
        if (split[1].startsWith("0")) {
            split[1] = split[1].replace("0", "");
        }
        if (split[2].startsWith("0")) {
            split[2] = split[2].replace("0", "");
        }
        mMonth = Integer.parseInt(split[1]);
        mDayOfMonth = Integer.parseInt(split[2]);
    }

    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String setoutTime = mTicketInfoList.get(position).getSetoutTime();
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        long currentTimeMillis = System.currentTimeMillis();
        if (isLogin) {
            if ((longtime-currentTimeMillis)<3600L*1000L){
                DialogShow.setDialog(TicketActivity.this, "据发车时间一小时内，停止售票", "确认");
            }else{
                checkIsLoginOnOtherDevice(mTicketInfoList.get(position));
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(TicketActivity.this, SmsLoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mDeviceId = sp.getString("DeviceId", "");
        mId = sp.getString("id", "");
        if ("".equals(mPhoneNum)) {
            isLogin = false;
        } else {
            isLogin = true;
        }
        MobclickAgent.onResume(this);
    }


    class TicketListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTicketInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = getLayoutInflater().inflate(R.layout.list_item_ticket, null);
            TextView start_time = (TextView) layout.findViewById(R.id.start_time);
            TextView start_station = (TextView) layout.findViewById(R.id.start_station);
            TextView destination = (TextView) layout.findViewById(R.id.destination);
            TextView ticket_price = (TextView) layout.findViewById(R.id.ticket_price);
            final TicketInfo ticketInfo = mTicketInfoList.get(position);
            TextView reserve = (TextView) layout.findViewById(R.id.reserve);
            reserve.setText("预订\n余票:" + ticketInfo.getFreeSeats());
            String outTime = timeFormate(ticketInfo.getSetoutTime());
            start_time.setText(outTime);
            start_station.setText(ticketInfo.getStationName());
            destination.setText(ticketInfo.getEndSiteName());
            ticket_price.setText("¥" + ticketInfo.getFullPrice());
            return layout;
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    private String timeFormate(String setoutTime) {
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(longtime);
    }

    //重写back方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查是否在其他设备上登陆
     */
    private void checkIsLoginOnOtherDevice(final TicketInfo ticketInfo) {
        if (!"".equals(mDeviceId)) {
            String url = Constant.URLFromAiTon.HOST + "account/findLogin_id";
//            String url = "https://218.5.80.24:3061/api/Busline/Get";
            Map<String, String> map = new HashMap<>();
            map.put("account_id", mId);
            HTTPUtils.post(TicketActivity.this, url, map, new VolleyListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(TicketActivity.this, "网络连接异常或正在维护", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String s) {
                    if (!s.equals(mDeviceId)) {
                        setDialog("你的账号在其他设备上登录\n请重新登录", "确定");
                    }
                    {
                        Intent intent = new Intent();
                        intent.putExtra("ticketInfo", ticketInfo);
                        intent.setClass(TicketActivity.this, FillinOrderActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    }
                }
            });
        }
    }

    //一个按钮的dialog提示
    public void setDialog01(String messageTxt, String iSeeTxt) {
        final AlertDialog dialog;
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
        dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //dialog提示
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //清除用户登录信息
                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Intent intent = new Intent();
                intent.setClass(TicketActivity.this, SmsLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean mDateCompare;

    public void showDatePickerDialog() {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                try {
                    mDateCompare = DateCompareUtil.DateCompare(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mDateCompare) {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDayOfMonth = dayOfMonth;
                    mTv_today.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                    mCheckedTime = mYear + "-" + mMonth + "-" + mDayOfMonth;
                    initData();
                    checkTimeBtn();
                } else {
                    Toast.makeText(TicketActivity.this, "预售11天内的车票，请重新选择！", Toast.LENGTH_SHORT).show();
                    mTv_today.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                }
            }
        }, mYear, mMonth - 1, mDayOfMonth).show(TicketActivity.this.getFragmentManager(), "datePicker");

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
