package com.example.zjb.bamin.Ddaibanpaotui.fragment_dabanpaotui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.fragment.BannerFragment;
import com.example.zjb.bamin.Bchangtukepiao.models.about_banner.BannerInfo;
import com.example.zjb.bamin.Ddaibanpaotui.model.DaBanPaoTuiGridViewItemInfo;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.ZcustomView.ViewPagerIndicator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DaiBanPaoTuiFragment extends Fragment implements View.OnClickListener
{

    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();
    private View mLayout;
    private ViewPager mViewPager_banner;
    private int[] mImageID = new int[]{R.mipmap.banner01, R.mipmap.banner02, R.mipmap.banner03};
    private int mPagerCount = Integer.MAX_VALUE / 3;
    private boolean mDragging;
    private boolean isFrist = true;
    private ViewPagerIndicator mViewPagerIndicator;
    private ImageView mIv_dabanpaotui_back;
    private GridView mGv_dabanpaogui_classify;
    private List<DaBanPaoTuiGridViewItemInfo> mGridViewItemInfo = new ArrayList<DaBanPaoTuiGridViewItemInfo>();
    private DaiBanPaoTuiGridViewAdapter mDaiBanPaoTuiGridViewAdapter;

    public DaiBanPaoTuiFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayout = inflater.inflate(R.layout.fragment_main_dai_ban_pao_tui, null);
        initBannerData();
        initGridViewItemData();
        findViewID();
        initUI();
        setListener();

        return mLayout;
    }

    private void initGridViewItemData()
    {
        mGridViewItemInfo.clear();
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("跑腿", R.mipmap.daibanpaotui_01paotui));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("代驾", R.mipmap.daibanpaotui_02daijia));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("缴费罚款", R.mipmap.daibanpaotui_03jifeifakuan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("送餐", R.mipmap.daibanpaotui_04songcan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("家政保洁", R.mipmap.daibanpaotui_05jiazheng));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("维修疏通", R.mipmap.daibanpaotui_06weixiushutong));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("月嫂保姆", R.mipmap.daibanpaotui_07yuesaobaomu));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("开锁换锁", R.mipmap.daibanpaotui_08kaisuo));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("租房租赁", R.mipmap.daibanpaotui_09zufang));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("家教托管", R.mipmap.daibanpaotui_10jiajiao));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("鲜花蛋糕", R.mipmap.daibanpaotui_11xianhua));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("新房二手房", R.mipmap.daibanpaotui_12ershoufang));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("代买彩票", R.mipmap.daibanpaotui_13daimaicaipiao));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("除四害\n测甲醛", R.mipmap.daibanpaotui_14cejiaquan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("罚款", R.mipmap.daibanpaotui_15fakuan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("更多",R.mipmap.daibanpaotui_16gengduo));
    }

    private void setListener()
    {
        mIv_dabanpaotui_back.setOnClickListener(this);
    }

    private void initUI()
    {
        initBanner();
        initGridView();
    }

    private void initGridView()
    {
        mDaiBanPaoTuiGridViewAdapter = new DaiBanPaoTuiGridViewAdapter();
        mGv_dabanpaogui_classify.setAdapter(mDaiBanPaoTuiGridViewAdapter);
    }

    private void findViewID()
    {
        mIv_dabanpaotui_back = (ImageView) mLayout.findViewById(R.id.iv_dabanpaotui_back);
        mViewPager_banner = (ViewPager) mLayout.findViewById(R.id.vp_headerview_pager);
        mViewPagerIndicator = (ViewPagerIndicator) mLayout.findViewById(R.id.ViewPagerIndicator);
        mGv_dabanpaogui_classify = (GridView) mLayout.findViewById(R.id.gv_dabanpaogui_classify);
    }

    private void initBannerData()
    {
        HTTPUtils.get(getActivity(), Constant.URLFromAiTon.GET_BANNER_IMG, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<BannerInfo>>()
                {
                }.getType();
                bannerData = GsonUtils.parseJSONArray(s, type);
            }
        });
    }

    /**
     * 设置广告条
     */
    private void initBanner()
    {
        mViewPager_banner.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mViewPager_banner.addOnPageChangeListener(new BannerOnPageChangeListener());
        if (isFrist)
        {
            autoScroll();
        }
    }

    private void autoScroll()
    {
        mViewPager_banner.setCurrentItem(mPagerCount / 2);
        mViewPager_banner.postDelayed(new Runnable()
        {
            public void run()
            {
                int position = mViewPager_banner.getCurrentItem() + 1;
                if (!mDragging)
                {
                    isFrist = false;
                    mViewPager_banner.setCurrentItem(position);
                }
                mViewPager_banner.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_dabanpaotui_back:
                getActivity().finish();
                break;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter
    {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (bannerData.size() == 0)
            {
                int pager_index = position % mImageID.length;
                return new BannerFragment(pager_index, mImageID[pager_index]);
            } else
            {
                int pager_index = position % bannerData.size();
                return new BannerFragment(pager_index, bannerData.get(pager_index).getUrl(), bannerData.get(pager_index).getUrl2());
            }
        }

        @Override
        public int getCount()
        {
            return mPagerCount;
        }
    }

    class BannerOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageScrollStateChanged(int state)
        {
            switch (state)
            {
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

        public void onPageScrolled(int position, float arg1, int arg2)
        {
            position = position % 3;
            mViewPagerIndicator.move(arg1, position);
        }

        public void onPageSelected(int arg0)
        {
        }
    }

    class DaiBanPaoTuiGridViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mGridViewItemInfo.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_dabanpaotui_gridview_item, null);
            ImageView iv_item_img = (ImageView) layout.findViewById(R.id.iv_dabanpaotui_grid_item_img);
            TextView tv_item_name = (TextView) layout.findViewById(R.id.tv_dabanpaotui_grid_item_name);
            if (mGridViewItemInfo != null && mGridViewItemInfo.size() > 0)
            {
                iv_item_img.setImageResource(mGridViewItemInfo.get(position).getIconId());
                tv_item_name.setText(mGridViewItemInfo.get(position).getName());
            }

            return layout;
        }
    }
}
