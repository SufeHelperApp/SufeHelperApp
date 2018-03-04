package com.example.sufehelperapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    public LocationClient mLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new Map.MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        //申请运行时权限
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
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Map.this, My_RegisterThirdActivity.class);
                startActivity(intent1);
            }
        });

    }
    //开始定位
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    //实时更新当前位置
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置更新间隔:每5秒更新当前位置
        option.setScanSpan(5000);
        //Device_Sensors将定位模式指定成传感器模式，指定使用GPS定位
        //Battery_Saving节电模式即网络定位
        //Hight_Accuracy默认模式
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);

        //获取位置信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        //活动销毁时停止定位
        mLocationClient.stop();
        mapView.onDestroy();
        //将“我”显示在定位上
        baiduMap.setMyLocationEnabled(false);
    }
    //通过循环将申请的权限都进行判断，如果有任何一个权限被拒绝，就会直接调用finish()方法关闭当前程序，
    // 只有当所有权限都被用户同意了，才会调用requestLocation()方法开始地理位置定位

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    for(int result : grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("维度：").append(location.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(location.getLongitude()).append("\n");
                    currentPosition.append("国家：").append(location.getCountry()).append("\n");
                    currentPosition.append("省：").append(location.getProvince()).append("\n");
                    currentPosition.append("市：").append(location.getCity()).append("\n");
                    currentPosition.append("区：").append(location.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(location.getStreet()).append("\n");
                    currentPosition.append("定位方式：");
                    if(location.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if(location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络");
                    }
                    positionText.setText(currentPosition);
                }
            });*/
            if(location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }


        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    private void navigateTo(BDLocation location) {
        if(isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            //缩放级别3-19之间
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.latitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
}
