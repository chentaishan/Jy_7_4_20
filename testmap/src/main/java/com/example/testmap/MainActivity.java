package com.example.testmap;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.testmap.overlayutil.PoiOverlay;
import com.example.testmap.overlayutil.WalkingRouteOverlay;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        private int totalPage;

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            Log.d(TAG, "onGetPoiResult: ");

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                mBaiduMap.clear();
                PoiOverlay poiOverlay = new PoiOverlay(mBaiduMap);
                poiOverlay.setData(poiResult);// 设置POI数据
                mBaiduMap.setOnMarkerClickListener(poiOverlay);
                poiOverlay.addToMap();// 将所有的overlay添加到地图上
                poiOverlay.zoomToSpan();
                //
                totalPage = poiResult.getTotalPageNum();// 获取总分页数
                Toast.makeText(
                        MainActivity.this,
                        "总共查到" + poiResult.getTotalPoiNum() + "个兴趣点, 分为"
                                + totalPage + "页", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            Log.d(TAG, "onGetPoiDetailResult: ");
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            Log.d(TAG, "onGetPoiIndoorResult: ");
        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            Log.d(TAG, "onGetPoiDetailResult: ");
        }
    };
    private PoiSearch mPoiSearch;
    private RoutePlanSearch mSearch;
    private Button mClick;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 20/04/25
                initRoutePlan();
                break;

        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            Log.d(TAG, "onReceiveLocation: ");
            if (location == null || mBaiduMap == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

    OnGetRoutePlanResultListener onGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            //创建WalkingRouteOverlay实例
            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                if (walkingRouteResult.getRouteLines() != null && walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();
                }
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }

    };
    String[] permissions = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE

    };
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                initView();
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, permissions);

    }

    private static final String TAG = "MainActivity";

    public void initMark() {

        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_mark);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    public void initPOI() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
//        mPoiSearch.searchInCity(new PoiCitySearchOption()
//                .city("北京") //必填
//                .keyword("餐厅") //必填
//                .pageNum(10));

        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(39.915446, 116.403869))
                .radius(10000)
                .keyword("餐厅")

                .pageNum(10));
    }

    private void initView() {

        Log.d(TAG, "initView: ");
        if (mMapView == null) {
            mMapView = (MapView) findViewById(R.id.bmapView);
        }
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,false,null));


        initMark();
        initPOI();

        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();

        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onResume();


        initPOI();


    }

    private void initRoutePlan() {
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(onGetRoutePlanResultListener);


        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));


    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        if (mBaiduMap != null)
            mBaiduMap.setMyLocationEnabled(false);
        if (mMapView != null)
            mMapView.onDestroy();
        mMapView = null;


        mPoiSearch.destroy();

        mSearch.destroy();
    }
}
