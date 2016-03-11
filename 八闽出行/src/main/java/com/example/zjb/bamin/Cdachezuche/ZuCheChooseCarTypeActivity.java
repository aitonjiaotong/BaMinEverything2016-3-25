package com.example.zjb.bamin.Cdachezuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class ZuCheChooseCarTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car_type);
    }

    public void jigouyongche02(View view) {
        Intent intent= new Intent();
        intent.setClass(ZuCheChooseCarTypeActivity.this,JiGouYongChe03Activity.class);
        startActivity(intent);
    }
}
