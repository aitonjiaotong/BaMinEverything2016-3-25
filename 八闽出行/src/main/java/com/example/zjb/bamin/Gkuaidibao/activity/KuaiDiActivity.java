package com.example.zjb.bamin.Gkuaidibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.zjb.bamin.R;

public class KuaiDiActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di);
        findID();
        initUI();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.rela_search).setOnClickListener(this);
        findViewById(R.id.rela_jijian).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
    }

    private void initUI() {

    }

    private void findID() {

    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent();
        switch (v.getId()){
            case R.id.imageView_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
            case R.id.rela_search:
                intent.setClass(KuaiDiActivity.this, SearchActivity.class);
                startActivity(intent);
                animFromSmallToBigIN();
                break;
            case R.id.rela_jijian:
                Toast.makeText(KuaiDiActivity.this, "功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }
    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }
}
