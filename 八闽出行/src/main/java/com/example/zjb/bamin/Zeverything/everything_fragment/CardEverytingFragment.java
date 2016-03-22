package com.example.zjb.bamin.Zeverything.everything_fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.R;
import com.umeng.analytics.MobclickAgent;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardEverytingFragment extends Fragment {


    private View mInflate;
    private WebView mWebViewTicketNotice;
    private LinearLayout mLl_loading;
    public CardEverytingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate==null){
            mInflate = inflater.inflate(R.layout.fragment_card_everyting, null);
            initUI();
            setListener();
        }
        return mInflate;
    }
    private void initUI() {
        initLoading();
        initWebView();
    }

    private void initLoading() {
        mLl_loading = (LinearLayout) mInflate.findViewById(R.id.ll_loading);
    }

    private void initWebView() {
        mWebViewTicketNotice = (WebView) mInflate.findViewById(R.id.webview_soft_info);
        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebViewTicketNotice.setWebViewClient(new MyWebViewClient());
        mWebViewTicketNotice.loadUrl(Constant.WebViewURL.EVETHING_LDCARD);
    }

    private void setListener() {
    }


    private void AnimFromRightToLeft() {
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLl_loading.setVisibility(View.GONE);
            mWebViewTicketNotice.setVisibility(View.VISIBLE);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        mWebViewTicketNotice.reload();
    }

}
