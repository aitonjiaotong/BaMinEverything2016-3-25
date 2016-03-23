package com.example.zjb.bamin.Cdachezuche;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.fragment.BannerFragment;
import com.example.zjb.bamin.Bchangtukepiao.models.about_banner.BannerInfo;
import com.example.zjb.bamin.Cdachezuche.QiYeZuChe.ZuChenJiGouYongCheActivity;
import com.example.zjb.bamin.Cdachezuche.ZiJiaZuChe.ZiJiaZuCheActivity;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.ZcustomView.ViewPagerIndicator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();
    private ViewPager mViewPager_banner;
    private int[] mImageID = new int[]{R.mipmap.banner01, R.mipmap.banner02, R.mipmap.banner03};
    private int mPagerCount = Integer.MAX_VALUE / 3;
    private boolean mDragging;
    private boolean isFrist = true;
    private ViewPagerIndicator mViewPagerIndicator;
    private ImageView mIv_qicheguanjia_back;
    private View mInflate;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_main2, null);
            initBannerData();
            findViewID();
            initUI();
            setListener();
        }
        return mInflate;
    }

    private void setListener() {
        mIv_qicheguanjia_back.setOnClickListener(this);
        mInflate.findViewById(R.id.rela_zijiazuche).setOnClickListener(this);
        mInflate.findViewById(R.id.rela_jigouyongche).setOnClickListener(this);
    }

    private void initUI() {
        initBanner();
    }

    private void findViewID() {
        mIv_qicheguanjia_back = (ImageView) mInflate.findViewById(R.id.iv_qicheguanjia_back);
        mViewPager_banner = (ViewPager) mInflate.findViewById(R.id.vp_headerview_pager);
        mViewPagerIndicator = (ViewPagerIndicator) mInflate.findViewById(R.id.ViewPagerIndicator);
    }

    private void initBannerData() {
        HTTPUtils.get(getActivity(), Constant.URLFromAiTon.GET_BANNER_IMG, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
                Type type = new TypeToken<ArrayList<BannerInfo>>() {
                }.getType();
                bannerData = GsonUtils.parseJSONArray(s, type);
            }
        });
    }

    /**
     * 设置广告条
     */
    private void initBanner() {
        mViewPager_banner.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mViewPager_banner.addOnPageChangeListener(new BannerOnPageChangeListener());
        if (isFrist) {
            autoScroll();
        }
    }

    private void autoScroll() {
        mViewPager_banner.setCurrentItem(mPagerCount / 2);
        mViewPager_banner.postDelayed(new Runnable() {
            public void run() {
                int position = mViewPager_banner.getCurrentItem() + 1;
                if (!mDragging) {
                    isFrist = false;
                    mViewPager_banner.setCurrentItem(position);
                }
                mViewPager_banner.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_qicheguanjia_back:
                getActivity().finish();
                break;
            case R.id.rela_zijiazuche:
                intent.setClass(getActivity(), ZiJiaZuCheActivity.class);
                startActivity(intent);
                break;
            case R.id.rela_jigouyongche:
                intent.setClass(getActivity(), ZuChenJiGouYongCheActivity.class);
                startActivity(intent);
                break;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if (bannerData.size() == 0) {
                int pager_index = position % mImageID.length;
                return new BannerFragment(pager_index, mImageID[pager_index]);
            } else {
                int pager_index = position % bannerData.size();
                return new BannerFragment(pager_index, bannerData.get(pager_index).getUrl(), bannerData.get(pager_index).getUrl2());
            }
        }

        @Override
        public int getCount() {
            return mPagerCount;
        }
    }

    class BannerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    mDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    mDragging = false;
                    break;
                default:
                    break;
            }
        }

        public void onPageScrolled(int position, float arg1, int arg2) {
            position = position % 3;
            mViewPagerIndicator.move(arg1, position);
        }

        public void onPageSelected(int arg0) {
        }
    }

}
