package com.example.zjb.bamin.Gkuaidibao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText_kuaidi_company;
    private EditText mEditText_kuaidi_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findID();
        initUI();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.button_search).setOnClickListener(this);
    }

    private void initUI() {

    }

    private void findID() {
        mEditText_kuaidi_company = (EditText) findViewById(R.id.editText_kuaidi_company);
        mEditText_kuaidi_code = (EditText) findViewById(R.id.editText_kuaidi_code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                String url = ;
                HTTPUtils.post(SearchActivity.this, url, map, new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }

                    @Override
                    public void onResponse(String s) {

                    }
                });
                break;
        }
    }
}
