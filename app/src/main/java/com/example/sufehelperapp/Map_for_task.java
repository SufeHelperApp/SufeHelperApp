package com.example.sufehelperapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.inner.Point;
import com.example.sufehelperapp.MarkerInfoUtil;

public class Map_for_task extends AppCompatActivity {

    private String POIName;
    private MapView mMapView;
    private LocationClient locationClient;
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration config;

    private Marker mMarkerA;
    private MarkerInfoUtil Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //此方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_for_task);
        mMapView =(MapView)findViewById(R.id.bmapView_task);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16f);
        baiduMap.setMapStatus(msu);

        // 定位初始化
        locationClient = new LocationClient(this);
        firstLocation =true;
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        locationClient.setLocOption(option);

        requestLocation();
        setMarkerInfo();
    }
    public void requestLocation() {
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null)
                    return;
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);

                // 第一次定位时，将地图位置移动到当前位置
                if (firstLocation)
                {
                    firstLocation = false;
                    LatLng xy = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                    baiduMap.animateMapStatus(status);
                }

               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder currentPosition = new StringBuilder();
                        currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
                        currentPosition.append("经线：").append(location.getLongitude()).append("\n");
                        currentPosition.append("定位方式：");
                        if (location.getLocType() == BDLocation.TypeGpsLocation) {
                            currentPosition.append("GPS");
                        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                            currentPosition.append("网络");
                        }
                    }
                });*/
            }
        });

    }

    private void setMarkerInfo() {
        //TODO:info.add(new...(获取各满足条件地点经,纬度,null,null));
        List<MarkerInfoUtil> infos = new ArrayList<MarkerInfoUtil>();
        infos.add(new MarkerInfoUtil(31.307327052096305, 121.50846834627889,null,null));
        //infos.add.........
        addOverlay(infos);
    }
    //显示marker
    private void addOverlay(List<MarkerInfoUtil> infos) {
        //清空地图
        baiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_fortask);
        LatLng latLng = null;
        OverlayOptions options;
        for(MarkerInfoUtil info:infos){
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
                    ;
            //添加marker
            mMarkerA = (Marker) baiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);

            InfoWindow mInfoWindow;
            mMarkerA.setExtraInfo(bundle);
            TextView sum = new TextView(getApplicationContext());
            //TODO:sum.setText("任务数量");  (8是我随便写的数字)
            sum.setText("8");
            TextPaint tp = sum.getPaint();
            tp.setFakeBoldText(true);
            bitmap = BitmapDescriptorFactory.fromView(sum);
            LatLng latLng1 = new LatLng(info.getLatitude(),info.getLongitude());
            InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    baiduMap.hideInfoWindow();
                }
            };
            InfoWindow infoWindow = new InfoWindow(bitmap,latLng,-30,listener);
            baiduMap.showInfoWindow(infoWindow);
        }
//TODO:各图标点击跳转的界面不一样，ALL_task是新增的用来做任务listview的，点击跳转到该界面
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent1 = new Intent(Map_for_task.this, All_task.class);
                startActivity(intent1);
                return true;
            }
        });

    }

    @Override
    protected void onStart()
    {
        // 如果要显示位置图标,必须先开启图层定位
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted())
        {
            locationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        // 关闭图层定位
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()
        locationClient.stop();
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
    }



}
