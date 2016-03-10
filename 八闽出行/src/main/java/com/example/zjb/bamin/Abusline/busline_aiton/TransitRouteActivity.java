package com.example.zjb.bamin.Abusline.busline_aiton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Abusline.busline_aition_constants.MyConstants;

import java.util.ArrayList;
import java.util.List;

public class TransitRouteActivity extends AppCompatActivity implements View.OnClickListener
{

    private ListView mLv_transit_route;
    private ImageView mIv_back;
    private ImageView mIv_change_direction;
    private TextView mTv_start_station;
    private TextView mTv_end_station;
    private List<TransitRouteLine.TransitStep> allStep = new ArrayList<TransitRouteLine.TransitStep>();
    private MyAdapter mMyAdapter;
    private String mChoosed_start;
    private String mChoosed_end;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_route);
        initIntent();
        findViewById();
        setListenner();
        initData();
    }

    private void initIntent()
    {
        Intent intent = getIntent();
        mChoosed_start = intent.getStringExtra("choosed_start");
        mChoosed_end = intent.getStringExtra("choosed_end");
    }

    private void findViewById()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mIv_change_direction = (ImageView) findViewById(R.id.iv_change_direction);
        mTv_start_station = (TextView) findViewById(R.id.tv_start_station);
        mTv_end_station = (TextView) findViewById(R.id.tv_end_station);
        mTv_start_station.setText(mChoosed_start);
        mTv_end_station.setText(mChoosed_end);
        mLv_transit_route = (ListView) findViewById(R.id.lv_transit_route);
        mMyAdapter = new MyAdapter();
        mLv_transit_route.setAdapter(mMyAdapter);
    }

    private void setListenner()
    {
        mIv_back.setOnClickListener(this);
    }

    private void initData()
    {
        SearchSit();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_change_direction:
                //TODO 切换上下行查询结果
                break;
        }
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return allStep.size();
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
            View layout = getLayoutInflater().inflate(R.layout.layout_busline_allstep_item, null);
            TextView tv_number = (TextView) layout.findViewById(R.id.tv_number);
            tv_number.setText(position + 1);
            TextView tv_rusult_title = (TextView) layout.findViewById(R.id.tv_rusult_title);
            TextView tv_about_station = (TextView) layout.findViewById(R.id.tv_about_station);
            TextView tv_about_time = (TextView) layout.findViewById(R.id.tv_about_time);
            TextView tv_about_journey = (TextView) layout.findViewById(R.id.tv_about_journey);
            TextView tv_about_walk = (TextView) layout.findViewById(R.id.tv_about_walk);

            if(allStep!=null && allStep.size()>0)
            {
                if(allStep.get(position).getStepType() == TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE)
                {
                    VehicleInfo vehicleInfo = allStep.get(position).getVehicleInfo();
                    tv_rusult_title.setText(allStep.get(position).getInstructions());
                    tv_about_station.setText("途经"+vehicleInfo.getPassStationNum()+"个站点");
                    Log.e("vehicleInfo:getTitle ", vehicleInfo.getTitle());
                    Log.e("vehicleInfo:getUid ", vehicleInfo.getUid());
                    Log.e("vehicleInfo:getTotalPrice ", "途经"+vehicleInfo.getPassStationNum()+"个站点");
                    Log.e("vehicleInfo:getZonePrice ", vehicleInfo.getZonePrice()+"");
                }
            }

            return layout;
        }
    }

    /**
     * 利用百度API调用出发地及目的地进行查询搜索
     */
    public void SearchSit()
    {
        // 初始化搜索模块，注册事件监听
        RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener()
        {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult)
            {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result)
            {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(TransitRouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteLine transitRouteLine = result.getRouteLines().get(0);
                    allStep = transitRouteLine.getAllStep();
                    for (int i = 0; i < allStep.size(); i++)
                    {
                        Log.e("allStep.get(i).getInstructions()", allStep.get(i).getInstructions());
                        if(allStep.get(i).getStepType() == TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE)
                        {
                            VehicleInfo vehicleInfo = allStep.get(i).getVehicleInfo();
                            Log.e("vehicleInfo:getTitle ", vehicleInfo.getTitle());
                            Log.e("vehicleInfo:getUid ", vehicleInfo.getUid());
                            Log.e("vehicleInfo:getPassStationNum ", vehicleInfo.getPassStationNum()+"");
                            Log.e("vehicleInfo:getTotalPrice ", vehicleInfo.getTotalPrice()+"");
                            Log.e("vehicleInfo:getZonePrice ", vehicleInfo.getZonePrice()+"");
                        }
                    }

                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult)
            {

            }
        });
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(MyConstants.Str.CITY, mChoosed_start);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(MyConstants.Str.CITY, mChoosed_end);
        routePlanSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode)
                .city(MyConstants.Str.CITY)
                .to(enNode));
    }
}
