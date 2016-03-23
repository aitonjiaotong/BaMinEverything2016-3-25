package com.example.zjb.bamin.Cdachezuche.QiYeZuChe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Cdachezuche.ZuCheChooseCityActivity;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.Cdachezuche.models.ChooseFristInfo;
import com.example.zjb.bamin.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ZuChenJiGouYongCheActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_dache_jg_back;
    private LinearLayout mLl_dache_jg_choose_city;
    private LinearLayout mLl_dache_jg_choose_time_get;
    private LinearLayout mLl_dache_jg_return_time;
    private RadioGroup mRg_dache_jg_months;
    private Button mBtn_dache_jg_next;
    private TextView mTv_dache_jg_get_time;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd EE HH:mm");
    private long mGetCarTimeMillis;
    private long mReturnCarTimeMillis;
    private TextView mTv_dache_jg_return_time;
    private TextView mTv_dache_jg_city_name;
    private SlideDateTimeListener GetslideDateTimePickerListener = new SlideDateTimeListener()
    {
        @Override
        public void onDateTimeSet(Date date)
        {
            boolean before = date.before(mGetDate);
            if (!before)
            {
                mGetCarTimeMillis = date.getTime();
                Log.e("onDateTimeSet ", "onDateTimeSetget " + mGetCarTimeMillis);
                mTv_dache_jg_get_time.setText(mSimpleDateFormat.format(date));
            } else
            {
                Toast.makeText(ZuChenJiGouYongCheActivity.this, "请选择合理的取车时间", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SlideDateTimeListener slideDateTimeListener = new SlideDateTimeListener()
    {
        @Override
        public void onDateTimeSet(Date date)
        {
            Date date1 = new Date(mGetDate.getTime() + 24 * 3600 + 1000);
            boolean before = date.before(date1);
            if (!before)
            {
                mReturnCarTimeMillis = date.getTime();
                Log.e("onDateTimeSet ", "onDateTimeSetreturn " + mReturnCarTimeMillis);
                mRg_dache_jg_months.clearCheck();
                mTv_dache_jg_return_time.setText(mSimpleDateFormat.format(date) + "");
            } else
            {
                Toast.makeText(ZuChenJiGouYongCheActivity.this, "至少租车一天", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Date mGetDate;


    private View mConfirm_order_dialog;
    private EditText mEt_dachezuche_dialog_unit_of_account;
    private EditText mEt_dachezuche_dialog_unit_of_password;
    private LinearLayout mLl_dache_reminder_prog;
    private Button mBtn_dachezuche_dialog_comfire;
    private AlertDialog.Builder mDialog;
    private AlertDialog mAlertDialog;
    private TextView mTv_check_failure_reminder;
    private LinearLayout mLl_dache_choos_driver;
    private LinearLayout mLl_dache_jg_choose_type_gongwuyi;
    private LinearLayout mLl_dache_jg_choose_type_gongwuer;
    private LinearLayout mLl_dache_jg_choose_type_shangwu;
    private RadioButton mRb_dache_gongwuyi;
    private RadioButton mRb_dache_gongwuer;
    private RadioButton mRb_dache_shangwu;
    private int mCarType = 0;
    private int mHasDriver = 0;
    private int mDriverID;
    private TextView mTv_dache_jg_driver_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_gou_yong_che);

        //弹出确认企业账号信息的对话框
        showConfirmOrderDialog();
        findViewID();
        initUI();
        setListener();

    }

    public void showConfirmOrderDialog()
    {
        mConfirm_order_dialog = getLayoutInflater().inflate(R.layout.dachezuche_order_detail_dailog_layout, null);
        mDialog = new AlertDialog.Builder(ZuChenJiGouYongCheActivity.this);
        mDialog.setView(mConfirm_order_dialog);
        mAlertDialog = mDialog.create();
        mAlertDialog.show();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mEt_dachezuche_dialog_unit_of_account = (EditText) mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_account);
        mEt_dachezuche_dialog_unit_of_password = (EditText) mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_password);
        mLl_dache_reminder_prog = (LinearLayout) mConfirm_order_dialog.findViewById(R.id.ll_dache_reminder_prog);
        mBtn_dachezuche_dialog_comfire = (Button) mConfirm_order_dialog.findViewById(R.id.btn_dachezuche_dialog_comfire);
        mTv_check_failure_reminder = (TextView) mConfirm_order_dialog.findViewById(R.id.tv_check_failure_reminder);

        mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    dialog.dismiss();
                    //此处把dialog dismiss掉，然后把本身的activity finish掉
                    finish();
                    return true;
                } else
                {
                    return false;
                }
            }
        });


    }

    private void verifyTheUnitOfAccount(String unitOfAccount, String unitOfpassword)
    {
        Map<String, String> params = new HashMap<>();
        params.put("code", unitOfAccount);
        params.put("password", unitOfpassword);
        HTTPUtils.post(ZuChenJiGouYongCheActivity.this, ConstantDaCheZuChe.Url.DACHEZUCHE_COMFIRE_UNIT_INFO, params, new VolleyListener()
        {
            @Override
            public void onResponse(String s)
            {
                if (s.equals("true"))
                {
                    mLl_dache_reminder_prog.setVisibility(View.GONE);
                    mAlertDialog.dismiss();
                } else
                {
                    mLl_dache_reminder_prog.setVisibility(View.GONE);
                    mTv_check_failure_reminder.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        });
    }


    private void findViewID()
    {
        mIv_dache_jg_back = (ImageView) findViewById(R.id.iv_dache_jg_back);
        mLl_dache_jg_choose_city = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_city);
        mLl_dache_jg_choose_time_get = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_time_get);
        mLl_dache_jg_return_time = (LinearLayout) findViewById(R.id.ll_dache_jg_return_time);
        mLl_dache_choos_driver = (LinearLayout) findViewById(R.id.ll_dache_choos_driver);
        mRg_dache_jg_months = (RadioGroup) findViewById(R.id.rg_dache_jg_months);
        mBtn_dache_jg_next = (Button) findViewById(R.id.btn_dache_jg_next);
        mTv_dache_jg_get_time = (TextView) findViewById(R.id.tv_dache_jg_get_time);
        mTv_dache_jg_return_time = (TextView) findViewById(R.id.tv_dache_jg_return_time);
        mTv_dache_jg_city_name = (TextView) findViewById(R.id.tv_dache_jg_city_name);
        mLl_dache_jg_choose_type_gongwuyi = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_gongwuyi);
        mLl_dache_jg_choose_type_gongwuer = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_gongwuer);
        mLl_dache_jg_choose_type_shangwu = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_shangwu);
        mRb_dache_gongwuyi = (RadioButton) findViewById(R.id.rb_dache_gongwuyi);
        mRb_dache_gongwuer = (RadioButton) findViewById(R.id.rb_dache_gongwuer);
        mRb_dache_shangwu = (RadioButton) findViewById(R.id.rb_dache_shangwu);
        mTv_dache_jg_driver_name = (TextView) findViewById(R.id.tv_dache_jg_driver_name);
    }

    private void initUI()
    {
        mTv_dache_jg_get_time.setText(getCurrentTimeMillisToString());
        mRg_dache_jg_months.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
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

    private void setListener()
    {
        mIv_dache_jg_back.setOnClickListener(this);
        mLl_dache_jg_choose_city.setOnClickListener(this);
        mLl_dache_jg_choose_time_get.setOnClickListener(this);
        mLl_dache_jg_return_time.setOnClickListener(this);
        mBtn_dache_jg_next.setOnClickListener(this);
        mBtn_dachezuche_dialog_comfire.setOnClickListener(this);
        mLl_dache_choos_driver.setOnClickListener(this);
        mLl_dache_jg_choose_type_gongwuyi.setOnClickListener(this);
        mLl_dache_jg_choose_type_gongwuer.setOnClickListener(this);
        mLl_dache_jg_choose_type_shangwu.setOnClickListener(this);
    }


    /**
     * 获取系统时间并转换时间格式 "yy-MM-dd EE HH:mm:ss"
     *
     * @param
     */
    public String getCurrentTimeMillisToString()
    {
        //默认推迟2小时
        mGetCarTimeMillis = System.currentTimeMillis() + 2 * 3600 * 1000;
        mGetDate = new Date(mGetCarTimeMillis);
        String format = mSimpleDateFormat.format(mGetCarTimeMillis);
        return format;
    }

    /**
     * 根据系统时间自动增加相应的时间天数
     *
     * @param months      多少个月
     * @param daysofmonth 每个月的天数
     * @return
     */
    public String addCurrentTimeMillisToString(Long months, Long daysofmonth)
    {
        long resultsOfAdd = mGetCarTimeMillis + (24L * 3600L * 1000L) * daysofmonth * months;
        mReturnCarTimeMillis = resultsOfAdd;
        String format = mSimpleDateFormat.format(resultsOfAdd);
        return format;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.iv_dache_jg_back:
                finish();
                break;
            case R.id.ll_dache_jg_choose_city:
                //跳转到城市选择列表界面
                intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheChooseCityActivity.class);
                startActivityForResult(intent, ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_TAKE_CAR_CITY);
                break;
            case R.id.ll_dache_jg_choose_time_get:
                //默认推迟两小时
                mGetDate = new Date(System.currentTimeMillis() + 2 * 3600 * 1000);
                new SlideDateTimePicker.Builder(getSupportFragmentManager()).setListener(GetslideDateTimePickerListener).setInitialDate(mGetDate)
//                      .setMinDate(minDate)
//                      .setMaxDate(maxDate)
                        .setIs24HourTime(true)
//                      .setTheme(SlideDateTimePicker.HOLO_DARK)
//                      .setIndicatorColor(Color.parseColor("#990000"))
                        .build().show();
                break;
            case R.id.ll_dache_jg_return_time:
                //默认两天后还车
                Date date = new Date(System.currentTimeMillis() + 24 * 2 * 3600 * 1000);
                new SlideDateTimePicker.Builder(getSupportFragmentManager()).setListener(slideDateTimeListener).setInitialDate(date)
//                      .setMinDate(minDate)
//                      .setMaxDate(maxDate)
                        .setIs24HourTime(true)
//                      .setTheme(SlideDateTimePicker.HOLO_DARK)
//                      .setIndicatorColor(Color.parseColor("#990000"))
                        .build().show();
                break;
            case R.id.btn_dache_jg_next:
                // 跳转到订单详情界面
                if(!"".equals(mTv_dache_jg_return_time.getText().toString()))
                {
                    String city_name = mTv_dache_jg_city_name.getText().toString();
                    intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheOrderDetailActivity.class);
                    ChooseFristInfo chooseFristInfo = new ChooseFristInfo(city_name, mGetCarTimeMillis, mReturnCarTimeMillis, mHasDriver, mDriverID, mCarType);
                    intent.putExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_FRIST_INFO, chooseFristInfo);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(ZuChenJiGouYongCheActivity.this,"请选择还车时间",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_dachezuche_dialog_comfire:
                //企业认证确认按钮
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String unitOfAccount = mEt_dachezuche_dialog_unit_of_account.getText().toString();
                String unitOfpassword = mEt_dachezuche_dialog_unit_of_password.getText().toString();
                if ("".equals(unitOfAccount) || "".equals(unitOfpassword))
                {
                    Toast.makeText(ZuChenJiGouYongCheActivity.this, "请填写完整的企业账号及密码！", Toast.LENGTH_SHORT).show();
                } else
                {
                    mLl_dache_reminder_prog.setVisibility(View.VISIBLE);
                    verifyTheUnitOfAccount(unitOfAccount, unitOfpassword);
                }
                break;
            case R.id.ll_dache_choos_driver:
                //选择司机
                intent.setClass(ZuChenJiGouYongCheActivity.this, ZuCheChooseDriverActivity.class);
                startActivityForResult(intent, ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_CHOOSE_DRIVER);
                break;
            case R.id.ll_dache_jg_choose_type_gongwuyi:
                mCarType = 0;
                mRb_dache_gongwuyi.setChecked(true);
                mRb_dache_gongwuer.setChecked(false);
                mRb_dache_shangwu.setChecked(false);
                break;
            case R.id.ll_dache_jg_choose_type_gongwuer:
                mCarType = 1;
                mRb_dache_gongwuyi.setChecked(false);
                mRb_dache_gongwuer.setChecked(true);
                mRb_dache_shangwu.setChecked(false);
                break;
            case R.id.ll_dache_jg_choose_type_shangwu:
                mCarType = 2;
                mRb_dache_gongwuyi.setChecked(false);
                mRb_dache_gongwuer.setChecked(false);
                mRb_dache_shangwu.setChecked(true);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            if (requestCode == ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_TAKE_CAR_CITY && resultCode == ConstantDaCheZuChe.ResultCode.JIGOUZUCHE_TAKE_CAR_CITY)
            {
                mTv_dache_jg_city_name.setText(data.getStringExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_CITY));
            }
            if(requestCode == ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_CHOOSE_DRIVER && resultCode == ConstantDaCheZuChe.ResultCode.JIGOUZUCHE_CHOOSE_DRIVER)
            {
                mHasDriver = 1;
                mDriverID = data.getIntExtra(ConstantDaCheZuChe.IntentKey.DRIVER_ID,-1);
                mTv_dache_jg_driver_name.setText(data.getStringExtra(ConstantDaCheZuChe.IntentKey.DRIVER_NAME));
            }
        }
    }

}
