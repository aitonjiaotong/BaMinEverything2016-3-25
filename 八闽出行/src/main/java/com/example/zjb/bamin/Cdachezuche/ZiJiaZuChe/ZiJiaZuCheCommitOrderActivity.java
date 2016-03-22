package com.example.zjb.bamin.Cdachezuche.ZiJiaZuChe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class ZiJiaZuCheCommitOrderActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_jia_zu_che_commit_order);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.button_commit_order).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_commit_order:
                intent.setClass(this,ZiJiaZuCheCommitOrderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
