package com.example.zjb.bamin.Cdachezuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZuChenJiGouYongCheActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIv_dache_jg_back;
    private LinearLayout mLl_dache_jg_choose_city;
    private LinearLayout mLl_dache_jg_choose_time_get;
    private LinearLayout mLl_dache_jg_return_time;
    private RadioGroup mRg_dache_jg_months;
    private LinearLayout mLl_dache_jg_choose_car_type;
    private ImageView mIv_btn_dache_jg_minus;
    private TextView mTv_dache_jg_car_count;
    private ImageView mIv_btn_dache_jg_add;
    private CheckBox mCb_dache_jg_hasdriver;
    private Button mBtn_dache_jg_next;
    private TextView mTv_dache_jg_get_time;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd EE HH:mm");
    private long mCurrentTimeMillis;
    private TextView mTv_dache_jg_return_time;
    private TextView mTv_dache_jg_city_name;
    //车辆数量
    private int carCount = 0;
    private SlideDateTimeListener GetslideDateTimePickerListener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            boolean before = date.before(mGetDate);
            if (!before) {
                mTv_dache_jg_get_time.setText(mSimpleDateFormat.format(date));
            } else {
                Toast.makeText(ZuChenJiGouYongCheActivity.this, "请选择合理的取车时间", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SlideDateTimeListener slideDateTimeListener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Date date1 = new Date(mGetDate.getTime() + 24 * 3600 + 1000);
            boolean before = date.before(date1);
            if (!before) {
                mRg_dache_jg_months.clearCheck();
                mTv_dache_jg_return_time.setText(mSimpleDateFormat.format(date) + "");
            } else {
                Toast.makeText(ZuChenJiGouYongCheActivity.this, "至少租车一天", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Date mGetDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_gou_yong_che);

        findViewID();
        initUI();
        setListener();

    }


    private void findViewID() {
        mIv_dache_jg_back = (ImageView) findViewById(R.id.iv_dache_jg_back);
        mLl_dache_jg_choose_city = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_city);
        mLl_dache_jg_choose_time_get = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_time_get);
        mLl_dache_jg_return_time = (LinearLayout) findViewById(R.id.ll_dache_jg_return_time);
        mRg_dache_jg_months = (RadioGroup) findViewById(R.id.rg_dache_jg_months);
        mLl_dache_jg_choose_car_type = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_car_type);
        mIv_btn_dache_jg_add = (ImageView) findViewById(R.id.iv_btn_dache_jg_add);
        mTv_dache_jg_car_count = (TextView) findViewById(R.id.tv_dache_jg_car_count);
        mIv_btn_dache_jg_minus = (ImageView) findViewById(R.id.iv_btn_dache_jg_minus);
        mCb_dache_jg_hasdriver = (CheckBox) findViewById(R.id.cb_dache_jg_hasdriver);
        mBtn_dache_jg_next = (Button) findViewById(R.id.btn_dache_jg_next);

        mTv_dache_jg_get_time = (TextView) findViewById(R.id.tv_dache_jg_get_time);
        mTv_dache_jg_return_time = (TextView) findViewById(R.id.tv_dache_jg_return_time);

        mTv_dache_jg_city_name = (TextView) findViewById(R.id.tv_dache_jg_city_name);
    }

    private void initUI() {
        mTv_dache_jg_car_count.setText(carCount + "");
        mTv_dache_jg_get_time.setText(getCurrentTimeMillisToString());
        mRg_dache_jg_months.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_dache_jg_three_months:
                        mTv_dache_jg_return_time.setText(addCurrentTimeMillisToString(3L, 30L));
                        break;
                    case R.id.rb_dache_jg_six_months:
                        mTv_dache_jg_return_time.setText(addCurrentTimeMillisToString(6L, 30L));
                        break;
                    case R.id.rb_dache_jg_twelve_months:
                        mTv_dache_jg_return_time.setText(addCurrentTimeMillisToString(12L, 30L));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setListener() {
        mIv_dache_jg_back.setOnClickListener(this);
        mLl_dache_jg_choose_city.setOnClickListener(this);
        mLl_dache_jg_choose_time_get.setOnClickListener(this);
        mLl_dache_jg_return_time.setOnClickListener(this);
        mLl_dache_jg_choose_car_type.setOnClickListener(this);
        mIv_btn_dache_jg_add.setOnClickListener(this);
        mIv_btn_dache_jg_minus.setOnClickListener(this);
        mBtn_dache_jg_next.setOnClickListener(this);
    }

    /**
     * 查询是否附带司机
     *
     * @return hasDriver
     */
    private boolean hasDriver() {
        boolean hasDriver = mCb_dache_jg_hasdriver.isChecked();
        return hasDriver;
    }

    /**
     * 获取系统时间并转换时间格式 "yy-MM-dd EE HH:mm:ss"
     *
     * @param
     */
    public String getCurrentTimeMillisToString() {
        //默认推迟2小时
        mCurrentTimeMillis = System.currentTimeMillis() + 2 * 3600 * 1000;
        mGetDate = new Date(mCurrentTimeMillis);
        String format = mSimpleDateFormat.format(mCurrentTimeMillis);
        return format;
    }

    /**
     * 根据系统时间自动增加相应的时间天数
     *
     * @param months      多少个月
     * @param daysofmonth 每个月的天数
     * @return
     */
    public String addCurrentTimeMillisToString(Long months, Long daysofmonth) {
        long resultsOfAdd = mCurrentTimeMillis + (24L * 3600L * 1000L) * daysofmonth * months;
        String format = mSimpleDateFormat.format(resultsOfAdd);
        return format;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_dache_jg_back:
                finish();
                break;
            case R.id.ll_dache_jg_choose_city:
                //跳转到城市选择列表界面
//                Toast.makeText(ZuChenJiGouYongCheActivity.this, "此功能暂时仅针对三明地区开放", Toast.LENGTH_SHORT).show();
                intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheChooseCityActivity.class);
                startActivityForResult(intent, ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CITY_REQUEST_CODE);
                break;
            case R.id.ll_dache_jg_choose_time_get:
                //默认推迟两小时
                mGetDate = new Date(System.currentTimeMillis() + 2 * 3600 * 1000);
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(GetslideDateTimePickerListener)
                        .setInitialDate(mGetDate)
//                      .setMinDate(minDate)
//                      .setMaxDate(maxDate)
                        .setIs24HourTime(true)
//                      .setTheme(SlideDateTimePicker.HOLO_DARK)
//                      .setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
                break;
            case R.id.ll_dache_jg_return_time:
                //默认两天后还车
                Date date = new Date(System.currentTimeMillis() + 24 * 2 * 3600 * 1000);
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(slideDateTimeListener)
                        .setInitialDate(date)
//                      .setMinDate(minDate)
//                      .setMaxDate(maxDate)
                        .setIs24HourTime(true)
//                      .setTheme(SlideDateTimePicker.HOLO_DARK)
//                      .setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
                break;
            case R.id.ll_dache_jg_choose_car_type:
                // 跳转到选择车型
                intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheChooseCarTypeActivity.class);
                startActivityForResult(intent, ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CAR_TYPE_REQUEST_CODE);
                break;
            case R.id.iv_btn_dache_jg_add:
                mIv_btn_dache_jg_minus.setEnabled(true);
                carCount++;
                mTv_dache_jg_car_count.setText(carCount + "");
                break;
            case R.id.iv_btn_dache_jg_minus:
                if (carCount > 0) {
                    carCount--;
                    mTv_dache_jg_car_count.setText(carCount + "");
                } else {
                    mIv_btn_dache_jg_minus.setEnabled(false);
                }
                break;
            case R.id.btn_dache_jg_next:
                // 跳转到订单详情界面
                intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheOrderDetailActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CITY_REQUEST_CODE) {
                if (resultCode == ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CITY_RESULT_CODE){
                    mTv_dache_jg_city_name.setText(data.getStringExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_CITY));
                    carCount = 1;
                    mTv_dache_jg_car_count.setText(carCount + "");
                }
            } else if (requestCode==ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CAR_TYPE_REQUEST_CODE){
                if (resultCode == ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CITY_RESULT_CODE){
                    
                }
            }

        }
    }
}
