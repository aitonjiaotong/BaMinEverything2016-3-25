package com.example.zjb.bamin.Cdachezuche;

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

import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.R;

public class ZuCheOrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;
    private Button mBtn_dache_commit_order;
    private LinearLayout mLl_dache_jg_order_get_car;
    private LinearLayout mLl_dache_jg_order_return_car;
    private TextView mTv_dache_jg_store_name_return;
    private TextView mTv_dache_jg_store_name_get;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_order_detail);

        findViewID();
        setListener();
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
                    startActivityForResult(intent, ConstantDaCheZuChe.RequestAndResultCode.STORES_MAP_GET_REQUEST_CODE);
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
                    startActivityForResult(intent, ConstantDaCheZuChe.RequestAndResultCode.STORES_MAP_RETRUN_REQUEST_CODE);
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
                case ConstantDaCheZuChe.RequestAndResultCode.STORES_MAP_GET_REQUEST_CODE:
                    String stringExtra_get = data.getStringExtra(ConstantDaCheZuChe.IntentKey.STORES_MAP_KEY);
                    mTv_dache_jg_store_name_get.setText(stringExtra_get);
                    break;
                //还车门店
                case ConstantDaCheZuChe.RequestAndResultCode.STORES_MAP_RETRUN_REQUEST_CODE:
                    String stringExtra_return = data.getStringExtra(ConstantDaCheZuChe.IntentKey.STORES_MAP_KEY);
                    mTv_dache_jg_store_name_return.setText(stringExtra_return);
                    break;
            }

        }
    }
}
