package com.example.zjb.bamin.Cdachezuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zjb.bamin.R;

public class MainDacheActivity extends AppCompatActivity {

    private LinearLayout mJiemian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dache);
        mJiemian = (LinearLayout) findViewById(R.id.jiemian);
    }

    public void jigouyongche(View view) {
        Intent intent= new Intent();
        intent.setClass(MainDacheActivity.this,ZuChenJiGouYongCheActivity.class);
        startActivity(intent);
    }
}
