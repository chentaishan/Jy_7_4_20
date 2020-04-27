package com.example.testmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mRouteplan;
    private MapView mBmapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private PoiSearch mPoiSearch;
    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mBaiduMap.clear();

                //创建PoiOverlay对象
                PoiOverlay poiOverlay = new PoiOverlay(mBaiduMap);

                //设置Poi检索数据
                poiOverlay.setData(poiResult);

                //将poiOverlay添加至地图并缩放至合适级别
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };
    private RoutePlanSearch mSearch;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mBmapView == null){
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initMarker();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

    }

    private void clickPOI() {

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("北京") //必填
                .keyword("美食") //必填
                .pageNum(10));
    }

    private void initMarker() {

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

    private void initView() {
        mRouteplan = (Button) findViewById(R.id.routeplan);
        mRouteplan.setOnClickListener(this);
        mBmapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mBmapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //开启热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
        // 地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);

//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,false,null));



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
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBmapView.onDestroy();

        mSearch.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.routeplan:
                // TODO 20/04/27

//              clickPOI();
                initRoutePlan();
                break;

            default:
                break;
        }
    }


    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            //创建WalkingRouteOverlay实例
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
            if (walkingRouteResult.getRouteLines().size() > 0) {
                //获取路径规划数据,(以返回的第一条数据为例)
                //为WalkingRouteOverlay实例设置路径数据
                overlay.setData(walkingRouteResult.getRouteLines().get(0));
                //在地图上绘制WalkingRouteOverlay
                overlay.addToMap();
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
    /**
     * 路径规划
     */
    private void initRoutePlan() {

        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "天安门");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");

        // 开始 规划
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }
}
