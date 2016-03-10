package com.example.zjb.bamin.Abusline.busline_aiton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Abusline.busline_aition_constants.MyConstants;

import java.util.ArrayList;
import java.util.List;

public class InputLocActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView mInput_cancle;
    private ImageView mIv_search;
    private EditText mIntput_edit;
    /*----百度公交搜索相关----*/
    private PoiSearch mSearch;
    private LinearLayout mLl_for_loading;
    private TextView mTv_results_suggest;
    private ListView mLv_search_result;
    private List<PoiInfo> mAllPoi = new ArrayList<PoiInfo>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_loc);
        initUI();
        setListener();
        initBaiDu();


    }

    private void initBaiDu()
    {
        mSearch = PoiSearch.newInstance();

    }


    private void setListener()
    {
        mInput_cancle.setOnClickListener(this);
        mIv_search.setOnClickListener(this);

    }

    private void initUI()
    {
        mInput_cancle = (TextView) findViewById(R.id.input_cancle);
        mIntput_edit = (EditText) findViewById(R.id.input_edit);
        Intent intent = getIntent();
        String intputType = intent.getStringExtra(MyConstants.IntentKey.INPUT_TYPE_KEY);
        if ("myLoc".equals(intputType))
        {
            mIntput_edit.setHint("输入起点");
        } else if ("end".equals(intputType))
        {
            mIntput_edit.setHint("输入终点");
        }

        mIv_search = (ImageView) findViewById(R.id.imageView_search);
        mLl_for_loading = (LinearLayout) findViewById(R.id.ll_for_loading);
        mTv_results_suggest = (TextView) findViewById(R.id.tv_results_suggest);
        mLv_search_result = (ListView) findViewById(R.id.lv_search_result);
        mAdapter = new MyAdapter();
        mLv_search_result.setAdapter(mAdapter);
        mLv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (mAllPoi != null && mAllPoi.size() > 0)
                {
                    Intent data = new Intent();
                    if (mAllPoi.get(position).type == PoiInfo.POITYPE.BUS_LINE)
                    {

                    }
                    if (mAllPoi.get(position).type == PoiInfo.POITYPE.BUS_STATION)
                    {
                        //TODO 跳转到本站公交车所经过的所有公交线路
                        data.putExtra("choosed",mAllPoi.get(position).name);
                        setResult(MyConstants.RequestAndResultCode.CHOOSED, data);
                        finish();
                    }
                    if (mAllPoi.get(position).type == PoiInfo.POITYPE.POINT)
                    {
                        //TODO 跳转到地图界面，并定位当前位置后显示附近的公交站
                        data.putExtra("choosed",mAllPoi.get(position).name);
                        setResult(MyConstants.RequestAndResultCode.CHOOSED, data);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.input_cancle:
                finish();
                break;
            case R.id.imageView_search:
                mLl_for_loading.setVisibility(View.VISIBLE);//显示加载数据提示
                mTv_results_suggest.setVisibility(View.GONE);//隐藏无查询结果提示文字
                mLv_search_result.setVisibility(View.GONE);//显示查询结果的列表
                mSearch.searchInCity(new PoiCitySearchOption().city(MyConstants.Str.CITY).keyword(getUseInput()));
                mSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener()
                {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult)
                    {
                        if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR)
                        {
                            //显示无查询结果提示文字
                            mTv_results_suggest.setVisibility(View.VISIBLE);
                            mLl_for_loading.setVisibility(View.GONE);
                            mLv_search_result.setVisibility(View.GONE);
                            return;
                        }
                        mTv_results_suggest.setVisibility(View.GONE);//隐藏无查询结果提示文字
                        mLl_for_loading.setVisibility(View.GONE);//隐藏数据加载提示
                        mLv_search_result.setVisibility(View.VISIBLE);//显示查询结果的列表
                        mAllPoi.clear();
                        //遍历所有POI，找到类型为公交站的POI，将其添加到容器中
                        for (int i = 0; i < poiResult.getAllPoi().size(); i++)
                        {
                            if (poiResult.getAllPoi().get(i).type == PoiInfo.POITYPE.BUS_STATION)
                            {
                                mAllPoi.add(poiResult.getAllPoi().get(i));
                            }
                        }
                        //遍历所有POI，找到类型为位置的POI，将其添加到容器中
                        for (int i = 0; i < poiResult.getAllPoi().size(); i++)
                        {
                            if (poiResult.getAllPoi().get(i).type == PoiInfo.POITYPE.POINT)
                            {
                                mAllPoi.add(poiResult.getAllPoi().get(i));
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
                    {

                    }
                });
                break;
        }
    }

    /*----获取用户输入的信息内容----*/
    public String getUseInput()
    {
        String s = mIntput_edit.getText().toString();
        return s;
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mAllPoi.size();
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
            View layout = getLayoutInflater().inflate(R.layout.busline_search_station, null);
            ImageView iv_type = (ImageView) layout.findViewById(R.id.iv_input_type);
            TextView tv_title = (TextView) layout.findViewById(R.id.tv_input_title);
            TextView tv_subtitle = (TextView) layout.findViewById(R.id.tv_input_subtitle);
            if (mAllPoi != null && mAllPoi.size() > 0)
            {
                if( mAllPoi.get(position).type == PoiInfo.POITYPE.BUS_STATION)
                {
                    iv_type.setImageResource(R.mipmap.icon_station);
                    tv_title.setText(mAllPoi.get(position).name);
                    tv_subtitle.setVisibility(View.GONE);
                }
                if(mAllPoi.get(position).type == PoiInfo.POITYPE.POINT)
                {
                    iv_type.setImageResource(R.mipmap.icon_poi);
                    tv_title.setText(mAllPoi.get(position).name);
                    tv_subtitle.setVisibility(View.VISIBLE);
                    tv_subtitle.setText(mAllPoi.get(position).address);
                }
            }
            return layout;
        }
    }

}
