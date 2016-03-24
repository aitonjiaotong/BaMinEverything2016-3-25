package com.example.zjb.bamin.Cdachezuche.ZiJiaZuChe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zjb.bamin.Cdachezuche.DaCheZhuCheActivity.ZuCheChooseCityActivity;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.R;

public class ZiJiaZuCheActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView_take_car_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_jia_zu_che);
        findID();
        setListener();
    }

    private void findID() {
        mTextView_take_car_city = (TextView) findViewById(R.id.textView_take_car_city);
    }

    private void setListener() {
        findViewById(R.id.button_lijixuanche).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
        findViewById(R.id.rela_take_car_city).setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            if (requestCode == ConstantDaCheZuChe.RequestCode.ZIJIAZUCHE_TAKE_CAR_CITY && resultCode == ConstantDaCheZuChe.ResultCode.ZIJIAZUCHE_TAKE_CAR_CITY)
            {
                mTextView_take_car_city.setText(data.getStringExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_CITY));
            }
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_lijixuanche:
                intent.setClass(this, ZuCheChooseCarTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_back:
                finish();
                break;
            case R.id.rela_take_car_city:
                //跳转到城市选择列表界面
                intent.setClass(this, ZuCheChooseCityActivity.class);
                startActivityForResult(intent, ConstantDaCheZuChe.RequestCode.ZIJIAZUCHE_TAKE_CAR_CITY);
                break;
        }
    }
}
