package com.example.zjb.bamin.Bchangtukepiao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.R;
import com.umeng.analytics.MobclickAgent;

public class Ours extends AppCompatActivity implements View.OnClickListener
{

    private WebView mWebViewTicketNotice;
    private LinearLayout mLl_ours_customer_telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ours);

        findViewID();
        initUI();
        setListener();
    }

    private void findViewID()
    {
        mWebViewTicketNotice = (WebView) findViewById(R.id.webview_about_us);
        mLl_ours_customer_telephone = (LinearLayout) findViewById(R.id.ll_ours_customer_telephone);
    }

    private void initUI()
    {
        initWebView();
    }

    private void initWebView()
    {

        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebViewTicketNotice.setWebViewClient(new WebViewClient());
        mWebViewTicketNotice.loadUrl(Constant.WebViewURL.ABOUT_US);
    }

    private void setListener()
    {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mLl_ours_customer_telephone.setOnClickListener(this);
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
            case R.id.ll_ours_customer_telephone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4000593330"));
                startActivity(intent);
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
