package com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment;


import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderViewPagerFagment.DaCheOrderFragment;
import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderViewPagerFagment.DaiJiaOrderFragment;
import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderViewPagerFagment.JiGouYongCheOrderFragment;
import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderViewPagerFagment.ZuCheOrderFragment;
import com.example.zjb.bamin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {


    private View mInflate;
    private int[] tabsID = new int[]{
            R.id.textView_jigouyongche,
            R.id.textView_zuche,
            R.id.textView_dache,
            R.id.textView_daijai
    };
    private int[] imgAnim = new int[]{
            R.id.textView_anim,
            R.id.textView_anim02,
            R.id.textView_anim03,
            R.id.textView_anim04
    };
    private TextView[] tabs_tv = new TextView[4];
    private ImageView[] tabs_img = new ImageView[4];
    private int tabsPosition = 0;
    private ImageView mImage_anim;
    private int offset = 0;// 动画图片偏移量
    private ViewPager mViewPager;
    private boolean isTabsClick = false;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_order, null);
            findID();
            initUI();
            setListener();
        }
        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        tabs_img[(0 + tabsPosition) % 4].setVisibility(View.VISIBLE);
        tabs_img[(1 + tabsPosition) % 4].setVisibility(View.INVISIBLE);
        tabs_img[(2 + tabsPosition) % 4].setVisibility(View.INVISIBLE);
        tabs_img[(3 + tabsPosition) % 4].setVisibility(View.INVISIBLE);
    }

    private void initUI() {
        initAnim();
    }

    private void initAnim() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        Log.e("screenW", "InitImageView " + screenW);
        offset = screenW / 4;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mImage_anim.setImageMatrix(matrix);// 设置动画初始位置
    }

    private void setListener() {
        for (int i = 0; i < tabsID.length; i++) {
            tabs_tv[i].setOnClickListener(this);
        }
        mViewPager.addOnPageChangeListener(new MyPagerChangeListener());
    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (!isTabsClick) {
                setTabs(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void findID() {
        for (int i = 0; i < tabsID.length; i++) {
            tabs_tv[i] = (TextView) mInflate.findViewById(tabsID[i]);
            tabs_img[i]= (ImageView) mInflate.findViewById(imgAnim[i]);
        }
        mImage_anim = (ImageView) mInflate.findViewById(R.id.textView_anim);
        mViewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyViewPager(getFragmentManager()));
    }

    class MyViewPager extends FragmentPagerAdapter {

        public MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if (position == 0) {
                JiGouYongCheOrderFragment jiGouYongCheOrderFragment = new JiGouYongCheOrderFragment();
                return jiGouYongCheOrderFragment;
            } else if (position == 1) {
                ZuCheOrderFragment zuCheOrderFragment = new ZuCheOrderFragment();
                return zuCheOrderFragment;
            } else if (position == 2) {
                DaCheOrderFragment daCheOrderFragment = new DaCheOrderFragment();
                return daCheOrderFragment;
            } else if (position == 3) {
                DaiJiaOrderFragment daiJiaOrderFragment = new DaiJiaOrderFragment();
                return daiJiaOrderFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView_jigouyongche:
                setTabs(0);
                break;
            case R.id.textView_zuche:
                setTabs(1);
                break;
            case R.id.textView_dache:
                setTabs(2);
                break;
            case R.id.textView_daijai:
                setTabs(3);
                break;
        }
    }

    private void setTabs(int currPosition) {
        isTabsClick = true;
        if (tabsPosition != currPosition) {
            tabs_img[0].setVisibility(View.VISIBLE);
            tabs_img[1].setVisibility(View.INVISIBLE);
            tabs_img[2].setVisibility(View.INVISIBLE);
            tabs_img[3].setVisibility(View.INVISIBLE);
            mViewPager.setCurrentItem(currPosition);
            Animation animation = new TranslateAnimation(offset * tabsPosition, offset * currPosition, 0, 0);
            tabsPosition = currPosition;
            int basic_color = getResources().getColor(R.color.title_bar);
            int system_gray = getResources().getColor(R.color.dachezuche_gray);
            tabs_tv[(0 + tabsPosition) % 4].setTextColor(basic_color);
            tabs_tv[(1 + tabsPosition) % 4].setTextColor(system_gray);
            tabs_tv[(2 + tabsPosition) % 4].setTextColor(system_gray);
            tabs_tv[(3 + tabsPosition) % 4].setTextColor(system_gray);
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            mImage_anim.startAnimation(animation);
        }
        isTabsClick = false;
    }
}
