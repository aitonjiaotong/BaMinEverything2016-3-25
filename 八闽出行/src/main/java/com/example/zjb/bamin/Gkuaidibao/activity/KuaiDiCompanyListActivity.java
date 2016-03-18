package com.example.zjb.bamin.Gkuaidibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjb.bamin.Gkuaidibao.model.KuaiDiCompanyCode;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.ZcustomView.IndexListView;

import java.util.ArrayList;
import java.util.List;

public class KuaiDiCompanyListActivity extends AppCompatActivity implements View.OnClickListener {
    private static String KEY = "kuaidi_list_company_name";
    private static int RESULTCODE = 0;
    private List<KuaiDiCompanyCode> mKuaiDiCompanyCodeList = new ArrayList<KuaiDiCompanyCode>();
    private ListView mLv_kuaidi_company_list;
    private IndexListView mIndexlistview;
    private TextView mTv_letter;
    private IndexListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_company_list);

        initIntent();
        findViewID();
        initUI();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.imageView_back).setOnClickListener(this);
    }

    private void findViewID()
    {
        mLv_kuaidi_company_list = (ListView) findViewById(R.id.lv_kuaidi_company_list);
        mIndexlistview = (IndexListView) findViewById(R.id.indexlistview);
        mTv_letter = (TextView) findViewById(R.id.tv_letter);
    }

    private void initUI()
    {
        mAdapter = new IndexListAdapter();
        mLv_kuaidi_company_list.setAdapter(mAdapter);
        mLv_kuaidi_company_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                setResultRerun(mKuaiDiCompanyCodeList.get(position).getKuaiDiCompany());
                finish();
                animFromBigToSmallOUT();
            }
        });

        mIndexlistview.setOnGetLetterListener(new IndexListView.GetLetterListener()
        {
            @Override
            public void onLetterChanged(String letter)
            {
                mTv_letter.setVisibility(View.VISIBLE);
                mTv_letter.setText(letter);
                //更新ListView的行数显示
                int searchLetter_index = searchLetter(letter);
                mLv_kuaidi_company_list.setSelection(searchLetter_index);
            }

            @Override
            public void onActionUp()
            {
                mTv_letter.setVisibility(View.GONE);
            }
        });
    }

    private void initIntent()
    {
        Intent intent = getIntent();
        mKuaiDiCompanyCodeList = intent.getParcelableArrayListExtra("data");
    }

    @Override
    public void onClick(View v) {
        finish();
        AnimFromRightToLeftOUT();
    }

    class IndexListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mKuaiDiCompanyCodeList.size();
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
            View layout = getLayoutInflater().inflate(R.layout.layout_index_letter_list_item, null);

            TextView tv_frist_letter = (TextView) layout.findViewById(R.id.tv_fristletter);
            TextView tv_kuaidi_company_name = (TextView) layout.findViewById(R.id.tv_kuaidi_company_name);
            if (mKuaiDiCompanyCodeList != null && mKuaiDiCompanyCodeList.size() > 0)
            {
                String substring_fristletter = mKuaiDiCompanyCodeList.get(position).getKuaiDiCode().substring(0, 1).toUpperCase();
                tv_frist_letter.setText(substring_fristletter);
                tv_kuaidi_company_name.setText(mKuaiDiCompanyCodeList.get(position).getKuaiDiCompany());
                if (position > 0)
                {
                    //上一行首字母
                    String lastletter = mKuaiDiCompanyCodeList.get(position - 1).getKuaiDiCode().substring(0, 1).toUpperCase();
                    if (substring_fristletter.equals(lastletter))
                    {
                        tv_frist_letter.setVisibility(View.GONE);
                    }
                }
            }
            return layout;
        }
    }


    /**
     * 设置回传至调用该页面的相关数据
     *
     * @param value
     */
    private void setResultRerun(String value)
    {
        Intent data = new Intent();
        data.putExtra(KEY, value);
        setResult(RESULTCODE, data);
    }

    /**
     * 搜索用户点击自定义的IndexListView控件所返回的字母
     */
    public int searchLetter(String letter)
    {
        for (int i = 0; i < mKuaiDiCompanyCodeList.size(); i++)
        {
            String string = mKuaiDiCompanyCodeList.get(i).getKuaiDiCode();
            if (string.toUpperCase().startsWith(letter))
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }
    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }
}
