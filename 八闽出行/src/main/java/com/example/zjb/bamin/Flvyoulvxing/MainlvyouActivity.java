package com.example.zjb.bamin.Flvyoulvxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zjb.bamin.R;

public class MainlvyouActivity extends AppCompatActivity implements View.OnClickListener
{

    //    private String mWebViewUrl = "http://m.ctrip.com/webapp/hotel/?allianceid=xxx&sid=xxx&popup=close&autoawaken=close";
    private String mWebViewUrl = "http://m.ctrip.com/html5/?allianceid=280196&sid=727211#";
    //    private String mWebViewUrl = "http://m.ctrip.com/html5/flight/?allianceid=280196&sid=727211#";
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

        mWv_journey.setWebViewClient(new MyWebViewClient());
        mWv_journey.setWebChromeClient(new MyWebChromeClient());
        mWv_journey.loadUrl(mWebViewUrl);

        //启用数据库
        settings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        //启用地理定位
        settings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        settings.setDomStorageEnabled(true);


//        // 设置可以支持缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        //自适应屏幕
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
        mWv_journey.requestFocusFromTouch();


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
                if (mWv_journey.canGoBack())
                {
                    mWv_journey.goBack();
                } else
                {
                    finish();
                }
                break;
        }
    }

    class MyWebChromeClient extends WebChromeClient
    {
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback)
        {
            super.onShowCustomView(view, callback);
        }

        //配置权限（同样在WebChromeClient中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)
        {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
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

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWv_journey.canGoBack())
        {
            mWv_journey.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


