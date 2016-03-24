package com.example.zjb.bamin.Cdachezuche;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.MainFragment;
import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.MoreFragment;
import com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderFragment;
import com.example.zjb.bamin.R;

public class DaCheZuCheMainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[]{
            "首页",
            "订单",
            "更多"
    };
    private Class[] fragment = new Class[]{
            MainFragment.class,
            OrderFragment.class,
            MoreFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.shouye_selector,
            R.drawable.dingdan_selector,
            R.drawable.gengduo_selector
    };
    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_che_zu_che_main);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);

        for (int i = 0; i < imgRes.length; i++)
        {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(inflate), fragment[i], null);
        }
    }
}
