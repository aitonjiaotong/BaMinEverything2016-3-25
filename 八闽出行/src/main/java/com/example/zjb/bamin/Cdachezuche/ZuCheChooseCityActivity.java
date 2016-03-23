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

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.Cdachezuche.models.CityInfo;
import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZuCheChooseCityActivity extends AppCompatActivity implements View.OnClickListener
{
    private Map<String, String> mParams = new HashMap<>();
    private int mPage = 0;
    private ListView mLv_dachezuche_city_list;
    private List<String> mCity_list_data = new ArrayList<String>();
    private MyCityListAdapter mMyCityListAdapter;
    private ImageView mIv_zuche_choose_city_back;
    private TextView mTv_choose_city_more;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_choose_city);

        findViewID();
        setListener();
        initUI();
        initData();
    }

    private void setListener()
    {
        mIv_zuche_choose_city_back.setOnClickListener(this);
        mTv_choose_city_more.setOnClickListener(this);
    }

    private void initData()
    {
        mParams.put("page", mPage + "");
        HTTPUtils.post(ZuCheChooseCityActivity.this, ConstantDaCheZuChe.Url.CITY_LIST, mParams, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                CityInfo cityInfo = GsonUtils.parseJSON(s, CityInfo.class);
                if (cityInfo.getNum() == (mPage + 1))
                {
                    mTv_choose_city_more.setVisibility(View.GONE);
                } else
                {
                    mTv_choose_city_more.setVisibility(View.VISIBLE);
                }
                mCity_list_data.addAll(cityInfo.getContains());
                mMyCityListAdapter.notifyDataSetChanged();
            }
        });

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
                data.putExtra(ConstantDaCheZuChe.IntentKey.CHOOSE_CITY, mCity_list_data.get(position));
                setResult(ConstantDaCheZuChe.ResultCode.JIGOUZUCHE_TAKE_CAR_CITY, data);
                setResult(ConstantDaCheZuChe.ResultCode.ZIJIAZUCHE_TAKE_CAR_CITY,data);
                finish();
            }
        });
    }

    private void findViewID()
    {
        mLv_dachezuche_city_list = (ListView) findViewById(R.id.lv_dachezuche_city_list);
        mIv_zuche_choose_city_back = (ImageView) findViewById(R.id.iv_zuche_choose_city_back);
        mTv_choose_city_more = (TextView) findViewById(R.id.tv_choose_city_more);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_city_back:
                finish();
                break;
            case R.id.tv_choose_city_more:
                mPage++;
                initData();
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
            if (mCity_list_data != null && mCity_list_data.size() > 0)
            {
                tv_dache_city_name.setText(mCity_list_data.get(position));
            }
            return layout;
        }
    }

}
