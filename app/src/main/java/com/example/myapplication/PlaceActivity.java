package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.*;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends AppCompatActivity implements LocationListAdapter.OnItemClickListener, OnRefreshLoadMoreListener, BaiduMap.OnMapClickListener {

    private EditText contentSeach;

    private MapView mapview;

    private BaiduMap baiduMap;
    private String mCity = "北京市";

    private double mLatitude = 0;
    private double mLongitude = 0;
    private PoiInfo mLocation;
    private PoiSearch mPoiSearch;
    private LocationListAdapter mLocationListAdapter;
    private int page = 0;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        checkPermission();
        SmartRefreshLayout refreshlayout = findViewById(R.id.refreshlayout);
        refreshlayout.setEnableRefresh(false);
        refreshlayout.setEnableLoadMore(true);
        refreshlayout.setOnRefreshLoadMoreListener(this);
        mLocationListAdapter = new LocationListAdapter(PlaceActivity.this);
        RecyclerView locationRecy = findViewById(R.id.location_recy);

        mapview = findViewById(R.id.mapview);
        contentSeach = findViewById(R.id.content_seach);

        locationRecy.setLayoutManager(new LinearLayoutManager(PlaceActivity.this));
        locationRecy.setAdapter(mLocationListAdapter);
        mLocationListAdapter.setOnItemClickListener(this);
        baiduMap = mapview.getMap();
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
        baiduMap.setOnMapClickListener(this);
        mapview.setLongClickable(true);
        initedit();
        initpoiSearch();
        initLocation();
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

        contentSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String input = contentSeach.getText().toString();
                    if (!TextUtils.isEmpty(input)) {
                        mLocationListAdapter.crear();
                        initSeach(input, 0);
                        DistanceUtils.closeKeybord(PlaceActivity.this);
                    }
                    return true;
                }
                return false;
            }
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
        baiduMap.clear();//清除覆盖物
        baiduMap.addOverlay(markerOptions);//添加
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(lng, 18f);
        baiduMap.animateMapStatus(u);
    }

    @Override
    public void onItem(PoiInfo PoiInfo, int position) {
        mLocation = PoiInfo;
        mLocationListAdapter.setmPosition(position);
        showMap(PoiInfo.getLocation().latitude, PoiInfo.getLocation().longitude);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initSeach(contentSeach.getText().toString().trim(), page);
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
                    mLocation = allPoi.get(0);
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

    @OnClick({R.id.click_cancel, R.id.click_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_cancel:
                finish();
                break;
            case R.id.click_ok:
                if (mLocation == null) {
                    Toast.makeText(this, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }
}
