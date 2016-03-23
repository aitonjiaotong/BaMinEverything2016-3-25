package com.example.zjb.bamin.Cdachezuche.QiYeZuChe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.UILUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Cdachezuche.StoresMapActivity;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.Cdachezuche.models.ChooseFristInfo;
import com.example.zjb.bamin.Cdachezuche.models.SingleCarInfo;
import com.example.zjb.bamin.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ZuCheOrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;
    private Button mBtn_dache_commit_order;
    private LinearLayout mLl_dache_jg_order_get_car;
    private LinearLayout mLl_dache_jg_order_return_car;
    private TextView mTv_dache_jg_store_name_return;
    private TextView mTv_dache_jg_store_name_get;
    private ChooseFristInfo mChooseFristInfo;
    private TextView mTv_dache_get_time_date;
    private TextView mTv_dache_get_time_time;
    private TextView mTv_dache_how_long;
    private TextView mTv_dache_return_time_date;
    private TextView mTv_dache_return_time_time;
    private TextView mTv_car_name;
    private TextView mTv_carriage_count;
    private TextView mTv_displacement;
    private TextView mTv_car_seat_count;
    private SingleCarInfo mSingleCarInfo;
    private ImageView mIv_car_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_order_detail);

        initGetIntent();
        findViewID();
        initUI();
        setListener();
        initData();

    }

    private void initData()
    {
        initCarInfoData();
    }

    private void initCarInfoData()
    {
        Map<String, String> params = new HashMap<>();
        params.put("lei", mChooseFristInfo.getCarType() + "");
        HTTPUtils.post(ZuCheOrderDetailActivity.this, ConstantDaCheZuChe.Url.GET_CAR_INFO, params, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                mSingleCarInfo = GsonUtils.parseJSON(s, SingleCarInfo.class);
                if (mSingleCarInfo != null)
                {
                    mTv_car_name.setText(mSingleCarInfo.getCar().getModel() + mSingleCarInfo.getCar().getType());
                    mTv_carriage_count.setText(mSingleCarInfo.getCar().getBox());
                    if (mSingleCarInfo.getCar().getZidong() == 0)
                    {
                        mTv_displacement.setText(mSingleCarInfo.getCar().getPailiang() + "自动");
                    } else
                    {
                        mTv_displacement.setText(mSingleCarInfo.getCar().getPailiang() + "手动");
                    }
                    mTv_displacement.setText(mSingleCarInfo.getCar().getPailiang());
                    mTv_car_seat_count.setText("乘坐" + mSingleCarInfo.getCar().getSeat() + "人");
                    if (mSingleCarInfo.getCar().getImage() != null && !"".equals(mSingleCarInfo.getCar().getImage()))
                    {
                        UILUtils.displayImageNoAnim(mSingleCarInfo.getCar().getImage(), mIv_car_img);
                    }
                }
            }
        });

    }

    private void initUI()
    {
        mTv_dache_get_time_date.setText(getDateToString(mChooseFristInfo.getGetCarTime()));
        mTv_dache_get_time_time.setText(getTimeToString(mChooseFristInfo.getGetCarTime()));
        mTv_dache_how_long.setText(getHowLong(mChooseFristInfo.getGetCarTime(), mChooseFristInfo.getReturnCarTime()));
        mTv_dache_return_time_date.setText(getDateToString(mChooseFristInfo.getReturnCarTime()));
        mTv_dache_return_time_time.setText(getTimeToString(mChooseFristInfo.getReturnCarTime()));
    }

    private String getHowLong(long starttime, long endting)
    {
        long howLong = (endting + (2 * 3600 * 1000)) - starttime;
        long l = howLong / (24 * 3600 * 1000);//得到多少天
        if (l > 30)
        {
            int month = (int) (l / (24 * 3600 * 1000 * 30));
            long day = l % (24 * 3600 * 1000 * 30);
            return month + "个月;" + (int) day + "天";
        } else
        {
            return (int) l + "天";
        }
    }

    private String getDateToString(long l)
    {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MM-dd");
        String date_format = mSimpleDateFormat.format(l);
        return date_format;
    }

    private String getTimeToString(long l)
    {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE HH:mm");
        String time_format = mSimpleDateFormat.format(l);
        return time_format;
    }

    private void initGetIntent()
    {
        Intent data = getIntent();
        mChooseFristInfo = (ChooseFristInfo) data.getSerializableExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_FRIST_INFO);
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
        mBtn_dache_commit_order.setOnClickListener(this);
        mLl_dache_jg_order_get_car.setOnClickListener(this);
        mLl_dache_jg_order_return_car.setOnClickListener(this);
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_zuche_choose_car_type_back);
        mBtn_dache_commit_order = (Button) findViewById(R.id.btn_dache_commit_order);
        mLl_dache_jg_order_get_car = (LinearLayout) findViewById(R.id.ll_dache_jg_order_get_car);
        mLl_dache_jg_order_return_car = (LinearLayout) findViewById(R.id.ll_dache_jg_order_return_car);
        mTv_dache_jg_store_name_return = (TextView) findViewById(R.id.tv_dache_jg_store_name_return);
        mTv_dache_jg_store_name_get = (TextView) findViewById(R.id.tv_dache_jg_store_name_get);

        mTv_dache_get_time_date = (TextView) findViewById(R.id.tv_dache_get_time_date);
        mTv_dache_get_time_time = (TextView) findViewById(R.id.tv_dache_get_time_time);
        mTv_dache_how_long = (TextView) findViewById(R.id.tv_dache_how_long);
        mTv_dache_return_time_date = (TextView) findViewById(R.id.tv_dache_return_time_date);
        mTv_dache_return_time_time = (TextView) findViewById(R.id.tv_dache_return_time_time);

        mIv_car_img = (ImageView) findViewById(R.id.iv_car_img);
        mTv_car_name = (TextView) findViewById(R.id.tv_car_name);
        mTv_carriage_count = (TextView) findViewById(R.id.tv_carriage_count);
        mTv_displacement = (TextView) findViewById(R.id.tv_displacement);
        mTv_car_seat_count = (TextView) findViewById(R.id.tv_car_seat_count);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_car_type_back:
                finish();
                break;
            case R.id.btn_dache_commit_order:
                //TODO 提交订单
                break;
            case R.id.ll_dache_jg_order_get_car:
                if (ContextCompat.checkSelfPermission(ZuCheOrderDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ZuCheOrderDetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else
                {
                    intent.setClass(ZuCheOrderDetailActivity.this, StoresMapActivity.class);
                    intent.putExtra(ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_KEY, ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_GET);
                    intent.putExtra(ConstantDaCheZuChe.IntentKey.CITY, ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_GET);
                    startActivityForResult(intent, ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_TAKE_CAR_MAP);
                }

                break;
            case R.id.ll_dache_jg_order_return_car:
                if (ContextCompat.checkSelfPermission(ZuCheOrderDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ZuCheOrderDetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else
                {
                    intent.setClass(ZuCheOrderDetailActivity.this, StoresMapActivity.class);
                    intent.putExtra(ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_KEY, ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_RETURN);
                    startActivityForResult(intent, ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_RETURN_CAR_MAP);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            switch (requestCode)
            {
                //取车门店
                case ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_TAKE_CAR_MAP:
                    String stringExtra_get = data.getStringExtra(ConstantDaCheZuChe.IntentKey.STORES_MAP_KEY);
                    mTv_dache_jg_store_name_get.setText(stringExtra_get);
                    break;
                //还车门店
                case ConstantDaCheZuChe.RequestCode.JIGOUZUCHE_RETURN_CAR_MAP:
                    String stringExtra_return = data.getStringExtra(ConstantDaCheZuChe.IntentKey.STORES_MAP_KEY);
                    mTv_dache_jg_store_name_return.setText(stringExtra_return);
                    break;
            }

        }
    }
}
