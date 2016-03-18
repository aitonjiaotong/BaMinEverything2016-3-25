package com.example.zjb.bamin.Eqicheguanjia.fragenmt_qicheguanjia;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class QiCheGuanJiaFragment extends Fragment implements View.OnClickListener
{

    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();
    private View mLayout;
    private ViewPager mViewPager_banner;
    private int[] mImageID = new int[]{R.mipmap.banner01, R.mipmap.banner02, R.mipmap.banner03};
    private int mPagerCount = Integer.MAX_VALUE / 3;
    private boolean mDragging;
    private boolean isFrist = true;
    private ViewPagerIndicator mViewPagerIndicator;
    private ImageView mIv_qicheguanjia_back;
    private GridView mGv_qicheguanjia_classify;
    private List<DaBanPaoTuiGridViewItemInfo> mGridViewItemInfo = new ArrayList<DaBanPaoTuiGridViewItemInfo>();
    private QiCheGuanJiaGridViewAdapter mQiCheGuanJiaGridAdapter;

    public QiCheGuanJiaFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayout = inflater.inflate(R.layout.fragment_qi_che_guan_jia, null);
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
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("查办违章", R.mipmap.qicheguanjia_01chabanweizhan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("代缴罚款", R.mipmap.qicheguanjia_02daijiaofakuan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("车辆年检", R.mipmap.qicheguanjia_03cheliangnianjian));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("驾照换证", R.mipmap.qicheguanjia_04jiazhaohuanzheng));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("道路救援", R.mipmap.qicheguanjia_05daolujiuyuan));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("特价保险", R.mipmap.qicheguanjia_06tejiabaoxian));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("优惠维修", R.mipmap.qicheguanjia_07youhuiweixiu));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("特价汽配", R.mipmap.qicheguanjia_08tejiaqipei));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("加油优惠", R.mipmap.qicheguanjia_09jiayouyouhui));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("ETC优惠", R.mipmap.qicheguanjia_10etcyouhui));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("二手车贷", R.mipmap.qicheguanjia_11ershouchedai));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("特价新车", R.mipmap.qicheguanjia_12tejiaxinche));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("代驾", R.mipmap.qicheguanjia_13daijia));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("特价培训", R.mipmap.qicheguanjia_14tejiapeixun));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("北斗GPS", R.mipmap.qicheguanjia_15gps));
        mGridViewItemInfo.add(new DaBanPaoTuiGridViewItemInfo("更多", R.mipmap.qicheguanjia_16gengduo));
    }

    private void setListener()
    {
        mIv_qicheguanjia_back.setOnClickListener(this);
    }

    private void initUI()
    {
        initBanner();
        initGridView();
    }

    private void initGridView()
    {
        mQiCheGuanJiaGridAdapter = new QiCheGuanJiaGridViewAdapter();
        mGv_qicheguanjia_classify.setAdapter(mQiCheGuanJiaGridAdapter);
    }

    private void findViewID()
    {
        mIv_qicheguanjia_back = (ImageView) mLayout.findViewById(R.id.iv_qicheguanjia_back);
        mViewPager_banner = (ViewPager) mLayout.findViewById(R.id.vp_headerview_pager);
        mViewPagerIndicator = (ViewPagerIndicator) mLayout.findViewById(R.id.ViewPagerIndicator);
        mGv_qicheguanjia_classify = (GridView) mLayout.findViewById(R.id.gv_qicheguanjia_classify);
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
            case R.id.iv_qicheguanjia_back:
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

    class QiCheGuanJiaGridViewAdapter extends BaseAdapter
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
