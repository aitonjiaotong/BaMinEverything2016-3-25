package com.example.zjb.bamin.Cdachezuche;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        Intent intent = new Intent();
        intent.setClass(MainDacheActivity.this, ZuChenJiGouYongCheActivity.class);
        startActivity(intent);
    }

    public void zijiazuche(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS},
                    0);
            Toast.makeText(MainDacheActivity.this, "没授权", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainDacheActivity.this, "授权了？", Toast.LENGTH_SHORT).show();
        }
    }
}
