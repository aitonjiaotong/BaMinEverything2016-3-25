package com.example.zjb.bamin.GkuaiDiWuLiu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zjb.bamin.R;

public class KuaiDiWuLiuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_wu_liu);
        findID();
        initUI();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.imageView_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
    }

    private void initUI() {
    }

    private void findID() {
    }

}
