package com.example.zjb.bamin.Zeverything.everything_fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Bchangtukepiao.activity.SmsLoginActivity;
import com.example.zjb.bamin.Zeverything.EveryThingSoftInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineEverythingFragment extends Fragment implements View.OnClickListener {


    private String mPhoneNum;
    private String mId;
    private boolean isLogined;
    private View mInflate;
    private TextView mTextView_unLogin;
    private RelativeLayout mRela_login;
    private TextView mTextView_login;
    private RelativeLayout mLoginED;
    private Button button_cancle_login;

    public MineEverythingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflate = inflater.inflate(R.layout.fragment_mine_everything, null);
        findID();
        initUI();
        setListener();
        return mInflate;
    }

    private void setListener() {
        mLoginED.setOnClickListener(this);
        button_cancle_login.setOnClickListener(this);
        mInflate.findViewById(R.id.rl_mine_evething_soft_info).setOnClickListener(this);
    }

    private void findID() {
        mTextView_unLogin = (TextView) mInflate.findViewById(R.id.textView_unLogin);
        mRela_login = (RelativeLayout) mInflate.findViewById(R.id.rela_login);
        mTextView_login = (TextView) mInflate.findViewById(R.id.textView_login);
        mLoginED = (RelativeLayout) mInflate.findViewById(R.id.loginED);
        button_cancle_login = (Button) mInflate.findViewById(R.id.button_cancle_login);
    }

    private void initUI() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initLogin();
    }

    private void initLogin() {
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mId = sp.getString("id", "");
        if ("".equals(mPhoneNum)) {
            isLogined = false;
            mTextView_unLogin.setVisibility(View.VISIBLE);
            mRela_login.setVisibility(View.INVISIBLE);
            mTextView_login.setVisibility(View.INVISIBLE);
            button_cancle_login.setVisibility(View.GONE);
        } else {
            isLogined = true;
            mTextView_unLogin.setVisibility(View.INVISIBLE);
            mRela_login.setVisibility(View.VISIBLE);
            mTextView_login.setVisibility(View.VISIBLE);
            mTextView_login.setText(mPhoneNum);
            button_cancle_login.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_cancle_login:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提醒");
                builder.setMessage("确定要退出吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        button_cancle_login.setVisibility(View.INVISIBLE);
                        mTextView_unLogin.setVisibility(View.VISIBLE);
                        mRela_login.setVisibility(View.INVISIBLE);
                        mTextView_login.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.loginED:
                if (isLogined){

                }else {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_mine_evething_soft_info:
                intent.setClass(getActivity(), EveryThingSoftInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
