package com.example.zjb.bamin.Ddaibanpaotui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zjb.bamin.Ddaibanpaotui.fragment_dabanpaotui.MainDaiBanPaoTuiFragment;
import com.example.zjb.bamin.Ddaibanpaotui.fragment_dabanpaotui.MainDaiBanPaoTuiOrderFragment;
import com.example.zjb.bamin.R;

public class DaiBanPaoTuiMainActivity extends AppCompatActivity
{
    private Class[] fragment = new Class[]{MainDaiBanPaoTuiFragment.class, MainDaiBanPaoTuiOrderFragment.class};
    private int[] imgRes = new int[]{R.drawable.ic_everyting_home_index_selector, R.drawable.ic_everyting_home_order_selector};
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_daibanpaotui);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);

        for (int i = 0; i < imgRes.length; i++)
        {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item_everyting, null);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(inflate), fragment[i], null);
        }
    }
}
