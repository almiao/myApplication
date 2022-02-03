package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.*;
import com.example.myapplication.common.DistanceUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends Activity implements LocationListAdapter.OnItemClickListener, OnRefreshLoadMoreListener, BaiduMap.OnMapClickListener {

    private EditText contentSearch;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private String mCity = "北京市";
    private double mLatitude = 0;
    private double mLongitude = 0;
    private PoiSearch mPoiSearch;
    private LocationListAdapter mLocationListAdapter;
    private int page = 0;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.e("hhh","bbbbbbbbbbbbbbbbbbbbbb人进入ffxxxxxxxxxxxxxxxxxx");
        initData();
    }

    private void initData() {
        checkPermission();
        SmartRefreshLayout refreshlayout = findViewById(R.id.refreshlayout);
        refreshlayout.setEnableRefresh(false);
        refreshlayout.setEnableLoadMore(true);
        refreshlayout.setOnRefreshLoadMoreListener(this);
        initMap();
        mLocationListAdapter = new LocationListAdapter(PlaceActivity.this);
        RecyclerView locationRecy = findViewById(R.id.location_recy);

        contentSearch = findViewById(R.id.content_search);
        locationRecy.setLayoutManager(new LinearLayoutManager(PlaceActivity.this));
        locationRecy.setAdapter(mLocationListAdapter);
        mLocationListAdapter.setOnItemClickListener(this);
        initedit();
        initpoiSearch();
        initLocation();
    }
    private void initMap(){

        mMapView = findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(15.0f);
        builder.target(point);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationEnabled(true);


    }

    private void initMap1(){
        mMapView = findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18.0f));
        mBaiduMap.setOnMapClickListener(this);
        mMapView.setLongClickable(true);
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);

        double weidu = 39.9184470000;
        double jindu = 116.3252280000;// 这个是百度地图公司的经纬度坐标点
        LatLng point = new LatLng(weidu, jindu);

        MapStatusUpdate center = MapStatusUpdateFactory.newLatLng(point);
        // 设置默认中心店
        mBaiduMap.setMapStatus(center);

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationEnabled(true);

        //定义Maker坐标点
        point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);



        //定义Maker坐标点
        LatLng point1 = new LatLng(39.944251, 116.494996);
//构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.control);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(point1) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true)
//设置平贴地图，在地图中双指下拉查看效果
                .flat(true)
                .alpha(0.5f);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option1);


        //文字覆盖物位置坐标
        LatLng llText = new LatLng(39.86923, 116.397428);

//构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("百度地图SDK") //文字内容
                .bgColor(0xAAFFFF00) //背景色
                .fontSize(24) //字号
                .fontColor(0xFFFF00FF) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
            }
        }

    }

    private void initedit() {

        contentSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String input = contentSearch.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    mLocationListAdapter.crear();
                    initSeach(input, 0);
                    DistanceUtils.closeKeybord(PlaceActivity.this);
                }
                return true;
            }
            return false;
        });
    }

    private void initLocation() {
        mLocationClient = DistanceUtils.getLocationClient(PlaceActivity.this);
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        DistanceUtils.reverseGeoParse(latLng.longitude, latLng.latitude, new OnGetGeoCoderResultListener() {
            //获取正向解析结果时执行函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult arg0) {
            }

            //获取反向解析结果时执行函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                } else {
                    if (!TextUtils.isEmpty(result.getAddress())) {
                        mLocationListAdapter.crear();
                        page = 0;
                        initSeach(result.getAddress(), 0);
                    }
                    showMap(latLng.latitude, latLng.longitude);
                }
            }
        });
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {
        System.out.println("poi click xxxxxxxxxxxxxxxxxxxxxxxx");
        Log.e("hhh","人进入ffxxxxxxxxxxxxxxxxxx");
        if (!TextUtils.isEmpty(mapPoi.getName())) {
            mLocationListAdapter.crear();
            page = 0;
            initSeach(mapPoi.getName(), 0);
        }
        showMap(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
    }

    public class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (!TextUtils.isEmpty(location.getCity())) {
                mCity = location.getCity();
            }
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            if (!TextUtils.isEmpty(location.getStreet())) {
                System.out.println("接收到检索结果：xxxxxxxxxxxxxxxx" + location.getStreet());
                mLocationListAdapter.crear();
                initSeach(location.getStreet(), 0);
            }
            showMap(mLatitude, mLongitude);
            mLocationClient.stop();
        }
    }

    /**
     * 设置地图定位标识
     *
     * @param latitude
     * @param longtitude
     */
    protected void showMap(double latitude, double longtitude) {
        LatLng lng = new LatLng(latitude, longtitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(lng)
                .icon(bitmap)// 设置 Marker 覆盖物的图标
                .zIndex(9)// 设置 marker 覆盖物的 zIndex
                .draggable(true);
        mBaiduMap.clear();//清除覆盖物
        mBaiduMap.addOverlay(markerOptions);//添加
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(lng, 18f);
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    public void onItem(PoiInfo PoiInfo, int position) {
        mLocationListAdapter.setmPosition(position);
        showMap(PoiInfo.getLocation().latitude, PoiInfo.getLocation().longitude);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initSeach(contentSearch.getText().toString().trim(), page);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    private void initSeach(String keyword, int page) {
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(mCity) //必填
                .keyword(keyword) //必填
                .cityLimit(false)
                .pageCapacity(20)
                .scope(2)
                .pageNum(page));

    }

    private void initpoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
//                PoiInfo: name = 五里店; uid = f1abb0295fe781a284154102; address = 轨道交通6号线;轨道交通9号线一期;轨道交通环线; province = 重庆市; city = 重庆市; area = 江北区; street_id = ; phoneNum = ; postCode = null; detail = 1; location = latitude: 29.591655, longitude: 106.572932; hasCaterDetails = false; isPano = false; tag = null; poiDetailInfo = PoiDetailInfo: name = null; location = null; address = null; province = null; city = null; area = null; telephone = null; uid = null; detail = 0; distance = 0; type = ; tag = 地铁站; naviLocation = null; detailUrl = ; price = 0.0; shopHours = ; overallRating = 0.0; tasteRating = 0.0; serviceRating = 0.0; environmentRating = 0.0; facilityRating = 0.0; hygieneRating = 0.0; technologyRating = 0.0; imageNum = 0; grouponNum = 0; discountNum = 0; commentNum = 0; favoriteNum = 0; checkinNum = 0; The 0 poiChildrenInfo is: PoiChildrenInfo: uid = f2c4c1ce23c1330b2fb90884; name = 五里店地铁站-3口; showName = 3口; tag = 出入口; location = latitude: 29.59119, longitude: 106.570995; address = ; The 1 poiChildrenInfo is: PoiChildrenInfo: uid = c88f5172d5155541a31de954; name = 五里店地铁站-4口; showName = 4口; tag = 出入口; location = latitude: 29.591266, longitude: 106.572599; address = ; direction = null; distance = 0
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                if (allPoi != null && allPoi.size() != 0) {
                    mLocationListAdapter.addAll(mLatitude, mLongitude, allPoi);
                } else {
                    mLocationListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
