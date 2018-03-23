package com.example.sufehelperapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity {
    private MapView mMapView;
        private LocationClient locationClient;
        private BaiduMap baiduMap;
        private boolean firstLocation;
        private BitmapDescriptor mCurrentMarker;
        private MyLocationConfiguration config;
        private TextView positionText;
        private TextView positionText2;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //此方法要再setContentView方法之前实现
            SDKInitializer.initialize(getApplicationContext());
            setContentView(R.layout.activity_map);
            mMapView =(MapView)findViewById(R.id.bmapView);
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
            positionText = (TextView) findViewById(R.id.position_text_view);
            positionText2 = (TextView) findViewById(R.id.position_text_choose);


            List<String> permissionList = new ArrayList<>();
            if(ContextCompat.checkSelfPermission(Map.this, Manifest.
                    permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(ContextCompat.checkSelfPermission(Map.this,Manifest.
                    permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if(ContextCompat.checkSelfPermission(Map.this,Manifest.
                    permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(!permissionList.isEmpty()) {
                String [] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(Map.this,permissions,1);
            } else {
                requestLocation();
            }

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

                    runOnUiThread(new Runnable() {
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
                            positionText.setText(currentPosition);
                        }
                    });
                }
            });
            baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                @Override
                public void onMapClick(final LatLng latLng) {

                }
                @Override
                public boolean onMapPoiClick(MapPoi mapPoi)
                {
                    final String POIName = mapPoi.getName();//POI点名称
                    final LatLng POIPosition = mapPoi.getPosition();//POI点坐标
                    //下面就是自己随便应用了
                    //根据POI点坐标反向地理编码
                    //reverseSearch(POIPosition);
                    //添加图层显示POI点
                    baiduMap.clear();
                    baiduMap.addOverlay(
                        new MarkerOptions()
                                .position(POIPosition)                                     //坐标位置
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
                                .title(POIName)                                         //标题

                    );
                        //将该POI点设置为地图中心
                    baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(POIPosition));
                    if (firstLocation == false) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder currentPosition = new StringBuilder();
                                currentPosition.append(POIName);
                                positionText2.setText(currentPosition);
                                //POIPosition.latitude
                                //POIPosition.longitude
                            }
                        });
                    }
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
