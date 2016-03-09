package com.example.zjb.bamin.busline.busline_aiton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.busline.busline_aition_utils.MyConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BusLineInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener
{

    private ListView mListView2;
    private MyAdapter mAdapter;
    private int isOpen = -1;
    private TextView mBusLineDirection;
    private TextView mBusLineNum;
    private String mPoi_uid;
    private BusLineSearch mBusLineSearch;
    private List<BusLineResult.BusStation> mStations = new ArrayList<BusLineResult.BusStation>();
    private TextView mTv_start_time;
    private TextView mTv_end_time;
    private ImageView mIv_refreash;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line_info);
        initIntent();
        initUI();
        setListener();
        initBaiDuBusSearch();
        initDate();
    }

    private void initBaiDuBusSearch()
    {
        mBusLineSearch = BusLineSearch.newInstance();
    }

    private void initDate()
    {
        mBusLineSearch.searchBusLine(new BusLineSearchOption().city(MyConstants.Str.CITY).uid(mPoi_uid));
        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener()
        {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult)
            {
                if(busLineResult!=null)
                {
                    mBusLineNum.setText(busLineResult.getBusLineName());
                    mStations = busLineResult.getStations();
                    if(mStations!=null&&mStations.size()>0)
                    {
                        mBusLineDirection.setText(mStations.get(mStations.size() - 1).getTitle());
                        if (busLineResult.getStartTime() != null && busLineResult.getEndTime() != null)
                        {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                            mTv_start_time.setText(simpleDateFormat.format(busLineResult.getStartTime()));
                            mTv_end_time.setText(simpleDateFormat.format(busLineResult.getEndTime()));
                        }
                    }
                }
            }
        });
    }

    private void initIntent()
    {
        Intent intent = getIntent();
        mPoi_uid = intent.getStringExtra("poi_uid");
    }

    private void setListener()
    {
        mListView2.setOnItemClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        mIv_refreash.setOnClickListener(this);
    }

    private void initUI()
    {
        mBusLineNum = (TextView) findViewById(R.id.busLineNum);
        mBusLineDirection = (TextView) findViewById(R.id.busLineDirection);
        mListView2 = (ListView) findViewById(R.id.listView2);
        mAdapter = new MyAdapter();
        mListView2.setAdapter(mAdapter);
        mTv_start_time = (TextView) findViewById(R.id.tv_start_time);
        mTv_end_time = (TextView) findViewById(R.id.tv_end_time);
        mIv_refreash = (ImageView) findViewById(R.id.iv_refreash);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        isOpen = position;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.iv_refreash:
                initDate();
                break;
        }
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mStations.size();
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
            View inflate = null;
            if (position == 0)
            {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem_head, null);
                TextView busStationHead = (TextView) inflate.findViewById(R.id.busStationHead);
                busStationHead.setText(mStations.get(position).getTitle());
            } else if (position == mStations.size() - 1)
            {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem_foot, null);
                TextView busStationHead = (TextView) inflate.findViewById(R.id.busStationFoot);
                busStationHead.setText(mStations.get(position).getTitle());
            } else
            {
                inflate = getLayoutInflater().inflate(R.layout.busline_listitem, null);
                TextView busStation = (TextView) inflate.findViewById(R.id.busStation);
                busStation.setText(mStations.get(position).getTitle());
                LinearLayout hideView = (LinearLayout) inflate.findViewById(R.id.hideView);
                RelativeLayout location = (RelativeLayout) inflate.findViewById(R.id.location);

                if (position == isOpen)
                {
                    hideView.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                } else
                {
                    hideView.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                }
            }
            return inflate;
        }
    }
}
