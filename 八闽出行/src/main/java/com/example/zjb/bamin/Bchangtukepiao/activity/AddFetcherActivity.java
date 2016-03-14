package com.example.zjb.bamin.Bchangtukepiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.models.about_used_contact.UsedContactInfo;
import com.example.zjb.bamin.Bchangtukepiao.models.about_used_contact.UsedPersonID;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Zutils.DialogShow;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFetcherActivity extends Activity implements View.OnClickListener {

    private TextView mCancel;
    private EditText mPassager_name;
    private EditText mPerson_id;
    private EditText mPassager_phoneNum;
    private String mBianji;
    private UsedContactInfo mTicketPassager;
    private String mPhoneNum;
    private String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fetcher);
        initItent();
        /**
         * 获取用户id
         */
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mId = sp.getString("id", "");
        initUI();
        setOnclick();
    }

    private void initItent() {
        Intent intent = getIntent();
        mTicketPassager = (UsedContactInfo) intent.getSerializableExtra("ticketPassager");
        mBianji = intent.getStringExtra("bianji");
    }

    private void initUI() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mPassager_name = (EditText) findViewById(R.id.passager_name);
        mPerson_id = (EditText) findViewById(R.id.person_ID);
        mPassager_phoneNum = (EditText) findViewById(R.id.passager_phoneNum);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        if ("MineFragment".equals(mBianji)) {
            tv_title.setText("编辑乘客");
            mPassager_name.setText(mTicketPassager.getName());
            mPerson_id.setText(mTicketPassager.getIdcard());
            mPassager_phoneNum.setText(mTicketPassager.getPhone());
            mPassager_name.setSelection(mTicketPassager.getName().length());
        }
    }

    private void setOnclick() {
        mCancel.setOnClickListener(this);
        findViewById(R.id.tv_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                final String passagerName = mPassager_name.getText().toString().trim();
                final String personID = mPerson_id.getText().toString().trim();
                final String passagerPhoneNum = mPassager_phoneNum.getText().toString().trim();
                if ("".equals(passagerName)) {
                    DialogShow.setDialog(AddFetcherActivity.this,"请输入您的真实姓名", "确定");
                } else {
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
                    Matcher m = p.matcher(passagerName);
                    if (m.matches()) {
                        if (passagerName.length() < 2 || passagerName.length() > 15) {
                            DialogShow.setDialog(AddFetcherActivity.this, "姓名长度为2-15个汉字！", "确定");
                        } else {
                            String yanzhengdizhi = "http://apis.juhe.cn/idcard/index?key=86c3cf00ca77641e108196c609ca3c08&cardno="+personID;
                            HTTPUtils.get(AddFetcherActivity.this, yanzhengdizhi, new VolleyListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    DialogShow.setDialog(AddFetcherActivity.this,"网络连接异常或正在维护","确认");
                                }

                                @Override
                                public void onResponse(String s) {
                                    UsedPersonID usedPersonID = GsonUtils.parseJSON(s, UsedPersonID.class);
                                    int error_code = usedPersonID.getError_code();
                                    if (0==error_code) {


                                        //成功
                                        boolean mobileNO = isMobileNO(passagerPhoneNum);
                                        if (mobileNO) {
                                            if ("MineFragment".equals(mBianji)){
                                                String url = Constant.URLFromAiTon.HOST + "person/updateperson";
                                                Map<String, String> map = new HashMap<>();
                                                map.put("id",mTicketPassager.getId()+"");
                                                map.put("name",passagerName);
                                                map.put("idcard",personID);
                                                map.put("phone",passagerPhoneNum);
                                                map.put("account_id",mId);
                                                HTTPUtils.post(AddFetcherActivity.this, url, map, new VolleyListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        DialogShow.setDialog(AddFetcherActivity.this,"网络连接异常或正在维护","确认");
                                                    }

                                                    @Override
                                                    public void onResponse(String s) {
                                                        if ("0".equals(s)){
                                                            finish();
                                                            animFromLeftToRightOUT();
                                                        }else {
                                                            DialogShow.setDialog(AddFetcherActivity.this,"身份证号重复","确认");
                                                        }
                                                    }
                                                });
                                            }else{
                                                String url = Constant.URLFromAiTon.HOST + "person/addperson";
                                                Map<String, String> map = new HashMap<>();
                                                map.put("name",passagerName);
                                                map.put("idcard",personID);
                                                map.put("phone",passagerPhoneNum);
                                                map.put("account_id",mId);
                                                HTTPUtils.post(AddFetcherActivity.this, url, map, new VolleyListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {

                                                    }

                                                    @Override
                                                    public void onResponse(String s) {
                                                        if ("0".equals(s)){
                                                            finish();
                                                            animFromLeftToRightOUT();
                                                        }else {
                                                            DialogShow.setDialog(AddFetcherActivity.this,"身份证号重复","确认");
                                                        }
                                                    }
                                                });
                                            }

                                        } else {
                                            DialogShow.setDialog(AddFetcherActivity.this,"请输入正确的手机号", "确定");
                                        }
                                    } else if (203801==error_code) {
                                        DialogShow.setDialog(AddFetcherActivity.this,usedPersonID.getReason(), "确定");
                                    } else if (203802==error_code) {
                                        DialogShow.setDialog(AddFetcherActivity.this,usedPersonID.getReason(), "确定" );
                                    } else if (203803==error_code) {
                                        DialogShow.setDialog(AddFetcherActivity.this,usedPersonID.getReason(), "确定" );
                                    }else if (203804==error_code) {
                                        DialogShow.setDialog(AddFetcherActivity.this,usedPersonID.getReason(), "确定" );
                                    } else {
                                        DialogShow.setDialog(AddFetcherActivity.this,"未知错误/未联网", "确定");
                                    }
                                }
                            });
                        }
                    } else {
                        DialogShow.setDialog(AddFetcherActivity.this,"姓名不要包含数字和字符", "谢谢" );
                    }
                }
                break;
            case R.id.tv_cancel:
                finish();
                animFromLeftToRightOUT();
                break;
        }
    }

    /**
     * 验证手机格式
     */
    public boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    /**
     * 界面跳转动画
     */
    private void animFromLeftToRightOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_right_out);
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            animFromLeftToRightOUT();
        }
        return super.onKeyDown(keyCode, event);
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
