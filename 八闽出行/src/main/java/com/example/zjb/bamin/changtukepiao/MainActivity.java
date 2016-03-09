package com.example.zjb.bamin.changtukepiao;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.fragment.Fragment01;
import com.example.zjb.bamin.fragment.Fragment02;
import com.example.zjb.bamin.fragment.MineFragment;
import com.example.zjb.bamin.utils.DialogShow;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[]{
            "查询",
            "订单",
            "我的",
    };
    private Class[] fragment = new Class[]{
            Fragment01.class,
            Fragment02.class,
            MineFragment.class,
    };
    private int[] imgRes = new int[]{
            R.drawable.ic_home_search_selector,
            R.drawable.ic_home_order_selector,
            R.drawable.ic_home_me_selector
    };
    private FragmentTabHost mTabHost;
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderDeatilActivity = intent.getStringExtra("OrderDeatilActivity");
            if ("OrderDeatilActivity".equals(orderDeatilActivity)) {
                mTabHost.setCurrentTab(1);
            } else {
                mTabHost.setCurrentTab(0);
            }
        }
    };
    private String mDeviceId;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //检查是否在其他设备上登陆登录
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
        mDeviceId = sp.getString("DeviceId", "");
//        检查是否在其他设备上登陆
        checkIsLoginOnOtherDevice();
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
    }

    /**
     * 检查是否在其他设备上登陆
     */
    private void checkIsLoginOnOtherDevice() {
        if (!"".equals(mDeviceId)) {
            String url = Constant.URLFromAiTon.HOST + "account/findLogin_id";
//            String url = "https://218.5.80.24:3061/api/Busline/Get";
            Map<String, String> map = new HashMap<>();
            map.put("account_id", mId);
            HTTPUtils.post(MainActivity.this, url, map, new VolleyListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    DialogShow.setDialog(MainActivity.this, "网络连接异常或正在维护", "确认");
                }

                @Override
                public void onResponse(String s) {
                    if (!s.equals(mDeviceId)) {
                        setDialog("你的账号在其他设备上登录\n请重新登录", "确定");
                    }
                }
            });
        }
    }

    //dialog提示
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //清除用户登录信息
                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SmsLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intent = new IntentFilter();
        intent.addAction("OrderDeatilActivity");
        registerReceiver(recevier, intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            animFromBigToSmallOUT();
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }
    ;

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
