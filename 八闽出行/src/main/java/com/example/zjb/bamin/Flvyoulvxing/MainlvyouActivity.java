package com.example.zjb.bamin.Flvyoulvxing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zjb.bamin.R;

public class MainlvyouActivity extends AppCompatActivity implements View.OnClickListener
{

    private String mWebViewUrl = "http://u.ctrip.com/HotelCSPSit/PhoenixPage/index.html?allianceid=310999&Sid=790059";
    private WebView mWv_journey;
    private ImageView mIv_journey_back;
    private LinearLayout mLl_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lvyou);

        findViewID();
        initUI();
        setListener();
    }

    private void findViewID()
    {
        mIv_journey_back = (ImageView) findViewById(R.id.iv_journey_back);
        mWv_journey = (WebView) findViewById(R.id.wv_journey);
        mLl_loading = (LinearLayout) findViewById(R.id.ll_loading);
    }

    private void initUI()
    {
        initWebView();
    }

    private void initWebView()
    {
        WebSettings settings = mWv_journey.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        mWv_journey.setWebViewClient(new MyWebViewClient());
        mWv_journey.loadUrl(mWebViewUrl);
    }

    private void setListener()
    {
        mIv_journey_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_journey_back:
                finish();
                break;
        }
    }

    class MyWebViewClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            mLl_loading.setVisibility(View.GONE);
            mWv_journey.setVisibility(View.VISIBLE);
        }
    }
}
