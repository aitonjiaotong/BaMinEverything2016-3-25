package com.example.zjb.bamin.Zeverything;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.shane_library.shane.upgrade.UpgradeUtils;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Zeverything.everything_fragment.CardEverytingFragment;
import com.example.zjb.bamin.Zeverything.everything_fragment.MainEverytingFragment;
import com.example.zjb.bamin.Zeverything.everything_fragment.MineEverythingFragment;
import com.example.zjb.bamin.Zeverything.everything_fragment.OrderEverythingFragment;

public class EverytingActivity extends AppCompatActivity
{
    private Class[] fragment = new Class[]{
            MainEverytingFragment.class,
            CardEverytingFragment.class,
            OrderEverythingFragment.class,
            MineEverythingFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.ic_everyting_home_index_selector,
            R.drawable.ic_everyting_home_card_selector,
            R.drawable.ic_everyting_home_order_selector,
            R.drawable.ic_everyting_home_me_selector
    };
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyting);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);

        for (int i = 0; i < imgRes.length; i++)
        {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item_everyting, null);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(""+i).setIndicator(inflate), fragment[i], null);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== 0){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                UpgradeUtils.checkUpgrade(this, Constant.URLFromAiTon.UP_GRADE);
            }else{
            }
        }
    }
/**
 * 双击退出应用
 */
//    private long currentTime = 0;
//    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
//        if(keyCode== KeyEvent.KEYCODE_BACK){
//            if(System.currentTimeMillis()-currentTime>1000){
//                Toast toast = Toast.makeText(MainActivity.this, "双击退出应用", Toast.LENGTH_SHORT);
//                toast.show();
//                currentTime=System.currentTimeMillis();
//                return false;
//            }else{
//                return super.onKeyDown(keyCode, event);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    };

}
