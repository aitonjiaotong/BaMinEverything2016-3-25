package com.example.zjb.bamin.Cdachezuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.R;

public class ZuCheOrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;
    private Button mBtn_dache_commit_order;
    private View mConfirm_order_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_order_detail);

        findViewID();
        setListener();
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
        mBtn_dache_commit_order.setOnClickListener(this);
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_zuche_choose_car_type_back);
        mBtn_dache_commit_order = (Button) findViewById(R.id.btn_dache_commit_order);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_car_type_back:
                finish();
                break;
            case R.id.btn_dache_commit_order:
                showConfirmOrderDialog();
                break;

        }
    }

    public void showConfirmOrderDialog()
    {
        //TODO 弹出订单确认企业账号信息的对话框
        mConfirm_order_dialog = getLayoutInflater().inflate(R.layout.dachezuche_order_detail_dailog_layout, null);
        new AlertDialog.Builder(ZuCheOrderDetailActivity.this)
                .setView(mConfirm_order_dialog)
                .create()
                .show();
        final EditText et_dachezuche_dialog_unit_of_account = (EditText) mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_account);
        final EditText et_dachezuche_dialog_unit_of_password = (EditText) mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_password);
        final EditText et_dachezuche_dialog_unit_of_phone = (EditText) mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_phone);
        final LinearLayout ll_dache_reminder_prog = (LinearLayout) mConfirm_order_dialog.findViewById(R.id.ll_dache_reminder_prog);
        Button btn_dachezuche_dialog_comfire = (Button) mConfirm_order_dialog.findViewById(R.id.btn_dachezuche_dialog_comfire);
        btn_dachezuche_dialog_comfire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String unitOfAccount = et_dachezuche_dialog_unit_of_account.getText().toString();
                String unitOfpassword = et_dachezuche_dialog_unit_of_password.getText().toString();
                String unitOfphone = et_dachezuche_dialog_unit_of_phone.getText().toString();
                ll_dache_reminder_prog.setVisibility(View.VISIBLE);
                verifyTheUnitOfAccount(unitOfAccount, unitOfpassword, unitOfphone);
                startActivity(new Intent(ZuCheOrderDetailActivity.this, OrderSuccessfullyActivity.class));
            }
        });
    }

    private void verifyTheUnitOfAccount(String unitOfAccount, String unitOfpassword, String unitOfphone)
    {
        //TODO Http请求发送用户输入的相关信息对后台服务验证其信息的正确性
        //TODO 拼接get请求网址，添加对应参数
        HTTPUtils.get(ZuCheOrderDetailActivity.this, ConstantDaCheZuChe.Url.DACHEZUCHE_COMFIRE_UNIT_INFO, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                //TODO 1.通过服务端返回的Json进行解析

                //TODO 2.根据服务端对传入的数据进行对比返回的结果，进行相关控件的判断

                //如果验证通过则跳转页面到订单完成界面
                startActivity(new Intent(ZuCheOrderDetailActivity.this, OrderSuccessfullyActivity.class));
            }
        });
    }
}
