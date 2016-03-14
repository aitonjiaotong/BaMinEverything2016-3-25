package com.example.zjb.bamin.Cdachezuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.List;

public class ZuCheChooseCityActivity extends AppCompatActivity implements View.OnClickListener
{

    private ListView mLv_dachezuche_city_list;
    private List<String> mCity_list_data = new ArrayList<String>();
    private MyCityListAdapter mMyCityListAdapter;
    private ImageView mIv_zuche_choose_city_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_choose_city);

        //测试数据
        mCity_list_data.add("三明");
        mCity_list_data.add("永安");
        mCity_list_data.add("宁化");
        mCity_list_data.add("沙县");
        mCity_list_data.add("泰宁");
        findViewID();
        setListener();
        initUI();
        initData();
    }

    private void setListener()
    {
        mIv_zuche_choose_city_back.setOnClickListener(this);
    }

    private void initData()
    {
        //TODO 通过后台服务端加载取车城市列表数据

    }

    private void initUI()
    {
        mMyCityListAdapter = new MyCityListAdapter();
        mLv_dachezuche_city_list.setAdapter(mMyCityListAdapter);
        mLv_dachezuche_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                data.putExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_CITY,mCity_list_data.get(position));
                setResult(ConstantDaCheZuChe.RequestAndResultCode.CHOOSE_CITY_RESULT_CODE,data);
                finish();
            }
        });
    }

    private void findViewID()
    {
        mLv_dachezuche_city_list = (ListView) findViewById(R.id.lv_dachezuche_city_list);
        mIv_zuche_choose_city_back = (ImageView) findViewById(R.id.iv_zuche_choose_city_back);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_city_back:
                finish();
                break;
        }
    }

    class MyCityListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mCity_list_data.size();
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
            View layout = getLayoutInflater().inflate(R.layout.dachezuche_city_list_item, null);
            TextView tv_dache_city_name = (TextView) layout.findViewById(R.id.tv_dache_city_name);
            if(mCity_list_data != null && mCity_list_data.size()>0)
            {
                tv_dache_city_name.setText(mCity_list_data.get(position));
            }
            return layout;
        }
    }

}
