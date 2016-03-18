package com.example.zjb.bamin.Ddaibanpaotui.activity_daibanpaotui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class SiRenDingZhiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_si_ren_ding_zhi);
    }

    public void xiayibu(View view){
        Intent intent=new Intent();
        intent.setClass(SiRenDingZhiActivity.this, SiRenDingZhi02Activity.class);
        startActivity(intent);
    }
}
