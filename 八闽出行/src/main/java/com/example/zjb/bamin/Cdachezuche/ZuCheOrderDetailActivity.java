package com.example.zjb.bamin.Cdachezuche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zjb.bamin.R;

public class ZuCheOrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;

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
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_zuche_choose_car_type_back);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_car_type_back:
                finish();
                break;
        }
    }
}
