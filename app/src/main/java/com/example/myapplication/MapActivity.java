package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private String[] tabs = {"找舞点", "找舞团"};
    private List<CourtDanceFragment> tabFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        TextView textView = findViewById(R.id.create_court_dance_group);
        textView.setClickable(true);

        textView.setOnClickListener(arg0 -> {
            Intent intent = new Intent(MapActivity.this,CreateCourtDanceGroupActivity.class);
            startActivity(intent);
        });

        String baseUrl="http://192.168.0.111:9200/";//retrofit要求以/结束,否则会产生错误
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)//设置项目路径 其它路径都改成相对路径 与ApiUrls   HOST    HOME=HOST+"/home"/
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//convert就是将json解析成javaBean
                .build();//执行创建方法

        //添加tab
        for (String tab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
            tabFragmentList.add(CourtDanceFragment.newInstance(tab,retrofit));
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });
        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    private void initMap(){

        mMapView = findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationEnabled(true);

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


}
