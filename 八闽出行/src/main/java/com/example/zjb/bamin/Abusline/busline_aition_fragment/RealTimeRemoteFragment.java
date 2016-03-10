package com.example.zjb.bamin.Abusline.busline_aition_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Abusline.busline_aiton.BusLineInfoActivity;
import com.example.zjb.bamin.Abusline.busline_aiton_model.BusLineInfo;
import com.example.zjb.bamin.Abusline.busline_aiton_model.BusStation;
import com.example.zjb.bamin.Zutils.GpsUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RealTimeRemoteFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private List<BusLineInfo> mBusLineInfoList = new ArrayList<>();
    private Boolean isSwitchMap = false;
    private MapView mBmapView;
    private View mInflate;
    private ListView mRealTimeRomote_listView;
    private BaiduMap mBaiduMap;
    //定位相关
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongitude;
    private String addressStr;
    //自定义定位图标
    private BitmapDescriptor mIconLocation;
    private List<BusStation.ContentEntity.RecordsEntity> mRecords;
    private RelativeLayout mRela_bmapView;
    private ImageView mImageView_reLocation;
    private LocationClientOption mOption;
    private Dialog mDialog;
    private LatLng mLlText;
    private UiSettings mUiSettings;
    private LatLng mCenterLatLng;
    private TextView mTextView_selectLocation;
    private List<Integer> addedMakerList = new ArrayList<>();
    private PopupWindow mPopupWindow;
    //    private ListView mNearby_listView;

    public RealTimeRemoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_realtime_remote, container, false);
            initData();
            initStationInfo();
            initUI();
            initLocation();
            setListener();
        }
        return mInflate;
    }

    private void initStationInfo() {
        InputStream inputStream = getResources().openRawResource(R.raw.stationinfo);
        String string = getString(inputStream);
        BusStation busStation = GsonUtils.parseJSON(string, BusStation.class);
        mRecords = busStation.getContent().getRecords();
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void initData() {
        mBusLineInfoList.add(new BusLineInfo("1", "徐碧方向"));
        mBusLineInfoList.add(new BusLineInfo("58", "上河城方向"));
        mBusLineInfoList.add(new BusLineInfo("66", "碧湖方向"));
    }

    private void setListener() {
        mInflate.findViewById(R.id.map).setOnClickListener(this);
        mRealTimeRomote_listView.setOnItemClickListener(this);
        mImageView_reLocation.setOnClickListener(this);
    }

    private void initUI() {
        mTextView_selectLocation = (TextView) mInflate.findViewById(R.id.textView_selectLocation);
        mTextView_selectLocation.setVisibility(View.GONE);
        mImageView_reLocation = (ImageView) mInflate.findViewById(R.id.imageView_reLocation);
        mRela_bmapView = (RelativeLayout) mInflate.findViewById(R.id.rela_bmapView);
        mRealTimeRomote_listView = (ListView) mInflate.findViewById(R.id.RealTimeRomote_listView);
        mRealTimeRomote_listView.setAdapter(new MyAdapter());
        mBmapView = (MapView) mInflate.findViewById(R.id.bmapView);
        mBaiduMap = mBmapView.getMap();
        //构造一个更新地图的msu对象，然后设置该对象为缩放等级(比例尺)，最后设置地图状态。
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(msu);
        mBmapView.showScaleControl(false);
        mBmapView.showZoomControls(false);
        mBaiduMap.setOnMarkerClickListener(markerListener);
        //设置地图滑动状态界面的监听
        setMapStatusChangeListener();
        /**------百度地图UI手势操作相关------*/
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setOverlookingGesturesEnabled(false);//取消俯视手势操作
        mUiSettings.setRotateGesturesEnabled(false);//取消旋转手势操作
    }

    /**
     * 地图状态改变监听回调
     */
    private void setMapStatusChangeListener()
    {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener()
        {
            public void onMapStatusChangeStart(MapStatus mapStatus)
            {
                text_selectLocationIsVisible(mapStatus);
            }

            public void onMapStatusChange(MapStatus mapStatus)
            {
                text_selectLocationIsVisible(mapStatus);
            }

            public void onMapStatusChangeFinish(MapStatus mapStatus)
            {
                updateMapState(mapStatus);
            }
        });
    }

    private void text_selectLocationIsVisible(MapStatus mapStatus) {
        LatLng target = mapStatus.target;
        double distance = DistanceUtil.getDistance(mLlText, target);
        if (distance >= 1000) {
            mTextView_selectLocation.setVisibility(View.VISIBLE);
        }else {
            mTextView_selectLocation.setVisibility(View.GONE);
        }
    }

    /**
     * 更新地图移动后的状态
     */
    private void updateMapState(MapStatus status)
    {
        mCenterLatLng = status.target;
        double distance = DistanceUtil.getDistance(mLlText, mCenterLatLng);
        if (distance >= 1000) {
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.b_poi_small);
            initMaker(bitmap, mCenterLatLng);
        }
    }


    private void addMaker(LatLng point,int i,BitmapDescriptor bitmap) {
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .title(mRecords.get(i).getStation_name())
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }


    //
    private void initLocation() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        mOption.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setOpenGps(true);//可选，默认false,设置是否使用gps
        mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(mOption);
        //初始化定位的图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.ico_location_big_highlight_map);
    }
//

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("busLineNum", mBusLineInfoList.get(position).getBusLineNum());
        intent.setClass(getActivity(), BusLineInfoActivity.class);
        startActivity(intent);
    }

    BaiduMap.OnMarkerClickListener markerListener = new BaiduMap.OnMarkerClickListener() {
        /**
         * 地图 Marker 覆盖物点击事件监听函数
         * @param marker 被点击的 marker
         */
        public boolean onMarkerClick(Marker marker){
            LatLng position = marker.getPosition();
            String title = marker.getTitle();
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(position);
            mBaiduMap.animateMapStatus(msu);
            View inflate = getActivity().getLayoutInflater().inflate(R.layout.baidumap_popupwindow, null);
            TextView textView_station = (TextView) inflate.findViewById(R.id.textView_station);
            textView_station.setText(title);
            InfoWindow mInfoWindow = new InfoWindow(inflate, position, -100);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
            return true;
        }
    };

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent();
        switch (v.getId()) {
//            case R.id.switchMap:
//                break;
            case R.id.imageView_reLocation:
                /**
                 * 重新定位
                 */
                if (!GpsUtils.gPSIsOPen(getActivity())) {
                    mDialog = new Dialog(getActivity());
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialog.setContentView(R.layout.layout_gps_dialog);
                    TextView mPositive = (TextView) mDialog.findViewById(R.id.tv_btn_setting);
                    TextView mNegative = (TextView) mDialog.findViewById(R.id.tv_btn_cancel);
                    mPositive.setOnClickListener(this);
                    mNegative.setOnClickListener(this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                }
                mTextView_selectLocation.setVisibility(View.GONE);
                LatLng latLng = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                break;
            case R.id.map:
                isSwitchMap = !isSwitchMap;
                if (isSwitchMap) {
                    mRela_bmapView.setVisibility(View.VISIBLE);
                } else {
                    mRela_bmapView.setVisibility(View.GONE);
                }
//                intent.setClass(getActivity(), MapActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_btn_setting:
                mDialog.dismiss();
                // 跳转到GPS相关设置界面
                GpsUtils.EnterGPSInterface(getActivity());
                break;
            case R.id.tv_btn_cancel:
                mDialog.dismiss();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBusLineInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.nearby_listview_item, null);
            TextView busLineNum = (TextView) inflate.findViewById(R.id.busLineNum);
            TextView busLineDirection = (TextView) inflate.findViewById(R.id.busLineDirection);
            busLineNum.setText(mBusLineInfoList.get(position).getBusLineNum());
            busLineDirection.setText(mBusLineInfoList.get(position).getBusLineDirection());
            return inflate;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //开启定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //停止定位
        mLocationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBmapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBmapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBmapView.onPause();
    }

    //    定位的监听回调
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 构造定位数据
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())//获得半径
                    .latitude(bdLocation.getLatitude())//获得经度
                    .longitude(bdLocation.getLongitude())//获得纬度
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(data);

            //设置自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
            mBaiduMap.setMyLocationConfigeration(config);

            //初始化经纬度
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();
            //第一次进入，定位到所在位置
            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
                addressStr = bdLocation.getAddrStr();
                mLlText = new LatLng(mLatitude, mLongitude);
                //构建文字Option对象，用于在地图上添加文字
                OverlayOptions textOption = new TextOptions()
                        .fontSize(24)
                        .fontColor(0xFF000000)
                        .text(addressStr)
                        .position(mLlText);
                //在地图上添加该文字对象并显示
                mBaiduMap.addOverlay(textOption);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.b_poi);
                initMaker(bitmap,mLlText);
            }
        }
    }


    private void initMaker(BitmapDescriptor bitmap,LatLng center) {
        //添加覆盖物
        for (int i = 0; i < mRecords.size(); i++) {
            if (mRecords.get(i).getStation_name().equals(mRecords.get(i+1).getStation_name())){
                i++;
            }

            LatLng point = tansforLatLng(mRecords.get(i).getLat() * 0.000001, mRecords.get(i).getLng() * 0.000001, i);
            double distance = DistanceUtil.getDistance(center, point);

            if (distance < 1000) {
                if (!addedMakerList.contains(i)){
                    addMaker(point, i,bitmap);
                    addedMakerList.add(i);
                }
            }
        }
    }

    private LatLng tansforLatLng(double lat,double lng,int i) {
        //转换百度坐标
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        LatLng sourceLatLng = new LatLng(mRecords.get(i).getLat() * 0.000001, mRecords.get(i).getLng() * 0.000001);
        converter.coord(sourceLatLng);
        LatLng point = converter.convert();
        return point;
    }

}

