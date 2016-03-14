package com.example.zjb.bamin.Bchangtukepiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.R;
import com.umeng.analytics.MobclickAgent;

public class NormalProblem extends AppCompatActivity implements View.OnClickListener
{

    private WebView mWebViewTicketNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_problem);

        initUI();
        setListener();
    }

    private void initUI()
    {
        initWebView();
    }

    private void initWebView()
    {
        mWebViewTicketNotice = (WebView) findViewById(R.id.webview_normal_problem);
        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebViewTicketNotice.setWebViewClient(new WebViewClient());
        mWebViewTicketNotice.loadUrl(Constant.WebViewURL.NORMAL_PROBLEM);
    }

    private void setListener()
    {
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
        }
    }
    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    };
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
