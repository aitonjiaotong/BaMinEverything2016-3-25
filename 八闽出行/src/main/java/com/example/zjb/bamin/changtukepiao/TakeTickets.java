package com.example.zjb.bamin.changtukepiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zjb.bamin.R;
import com.example.zjb.bamin.constant.Constant;

public class TakeTickets extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebViewTicketNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_tickets);

        initUI();
        setListener();
    }

    private void initUI()
    {
        initWebView();
    }

    private void initWebView()
    {
        mWebViewTicketNotice = (WebView) findViewById(R.id.webview_take_ticket);
        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebViewTicketNotice.setWebViewClient(new WebViewClient());
        mWebViewTicketNotice.loadUrl(Constant.WebViewURL.TAKE_TICKETS);
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

}
