package com.example.zjb.bamin.Cdachezuche.DaCheZhuCheActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.Cdachezuche.models.StoresMarkerInfo;
import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.List;

public class StoresMapActivity extends AppCompatActivity implements View.OnClickListener
{

    private List<StoresMarkerInfo> mStoresMarkerInfo = new ArrayList<StoresMarkerInfo>();
    private ImageView mIv_zuche_stores_map_back;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarker;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bd_markerA = BitmapDescriptorFactory.fromResource(R.mipmap.b_poi);
    private BitmapDescriptor mCurrentMarker;
    private LocationClient mLocClient;
    boolean isFirstLoc = true;// 是否首次定位
    public MyLocationListenner myListener = new MyLocationListenner();
    private int mIntExtra;
    private ImageView mIv_zuche_stores_loc;
    private double mLocLatitude;
    private double mLocLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_map);
        //TODO 本地StoresMarkerInfo数据,未来直接从后台服务端进行获取
        mStoresMarkerInfo.add(new StoresMarkerInfo(26.264016, 117.640016, "三明客运中心店---"));
        initIntent();
        findViewID();
        initUI();
        setListener();
    }

    private void initIntent()
    {
        Intent intent = getIntent();
        mIntExtra = intent.getIntExtra(ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_KEY, -1);
    }

    private void findViewID()
    {
        mIv_zuche_stores_loc = (ImageView) findViewById(R.id.iv_zuche_stores_loc);
        mIv_zuche_stores_map_back = (ImageView) findViewById(R.id.iv_zuche_stores_map_back);
        mMapView = (MapView) findViewById(R.id.mapview_dache_stores);
    }

    private void initUI()
    {
        initBaiDuMap();
    }

    private void initBaiDuMap()
    {
        initBaseMap();
        initMarker();
        initLoc();

    }

    public void initBaseMap()
    {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        //默认初始化地图时定位到三明客运中心店位置
        LatLng latLng = new LatLng(26.264016, 117.640016);
        mapStatusUpDate(latLng, mBaiduMap);
    }

    public void initMarker()
    {
        //构建Marker图标
        bd_markerA = BitmapDescriptorFactory.fromResource(R.mipmap.b_poi);
        for (int i = 0; i < mStoresMarkerInfo.size(); i++)
        {
            //定义Maker坐标点
            LatLng maker_point = new LatLng(mStoresMarkerInfo.get(i).getLatitude(), mStoresMarkerInfo.get(i).getLongitude());
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .title(mStoresMarkerInfo.get(i).getTitle())
                    .position(maker_point)
                    .icon(bd_markerA);
            //在地图上添加Marker，并显示
            mMarker = (Marker) mBaiduMap.addOverlay(option);
        }
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                final String markerTitle = mMarker.getTitle();
                LatLng position = mMarker.getPosition();
                //地图移动到marker位置为中心
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(position);
                mBaiduMap.animateMapStatus(msu);
                View inflate = getLayoutInflater().inflate(R.layout.dache_baidumap_marker_info, null);
                TextView textView_station = (TextView) inflate.findViewById(R.id.tv_stores_map_name);
                textView_station.setText(markerTitle);
                TextView tv_get_or_return = (TextView) inflate.findViewById(R.id.tv_get_or_return);
                if (mIntExtra != -1)
                {
                    switch (mIntExtra)
                    {
                        case ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_GET:
                            tv_get_or_return.setText("取");
                            break;
                        case ConstantDaCheZuChe.IntentKey.GET_MAP_LOC_RETURN:
                            tv_get_or_return.setText("还");
                            break;
                    }
                }
                InfoWindow mInfoWindow = new InfoWindow(inflate, position, -100);
                //显示InfoWindow
                mBaiduMap.showInfoWindow(mInfoWindow);
                inflate.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent data = new Intent();
                        data.putExtra(ConstantDaCheZuChe.IntentKey.STORES_MAP_KEY, markerTitle);
                        setResult(ConstantDaCheZuChe.ResultCode.JIGOUZUCHE_TAKE_CAR_MAP, data);
                        finish();
                    }
                });
                return true;
            }
        });
    }

    private void initLoc()
    {
        // 自定义定位图标
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.navi_car_locked);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
//        mLocClient.start();
    }

    private void setListener()
    {
        mIv_zuche_stores_map_back.setOnClickListener(this);
        mIv_zuche_stores_loc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_stores_map_back:
                finish();
                break;
            case R.id.iv_zuche_stores_loc:
                mLocClient.start();
                mapStatusUpDate(new LatLng(mLocLatitude,mLocLongitude),mBaiduMap);
                break;
        }
    }

    private void mapStatusUpDate(LatLng latlng,BaiduMap baiduMap)
    {
        //地图移动到marker位置为中心
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
        baiduMap.animateMapStatus(msu);
    }


    @Override
    protected void onPause()
    {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        // 回收 bitmap 资源
        bd_markerA.recycle();
        super.onDestroy();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation location)
        {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
            {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //初始化经纬度
            mLocLatitude = location.getLatitude();
            mLocLongitude = location.getLongitude();
            if (isFirstLoc)
            {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation)
        {
        }
    }
}
