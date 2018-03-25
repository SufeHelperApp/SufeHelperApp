package com.example.sufehelperapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity {

    private user user;

    private String myPhone;
    Connection con;
    ResultSet rs;

    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    public LocationClient mLocationClient;
    private TextView positionText;
    //创建自己的箭头定位
    private BitmapDescriptor bitmapDescriptor;
    private float mLastX;

    public BDLocationListener mBDListener = new MyLocationListener();
    private List<BDMapData> bdMapClientList;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new Map.MyLocationListener());//BDListener
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

        positionText = (TextView) findViewById(R.id.position_text_view);
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
                Intent intent1 = new Intent(Map.this, Task_LaunchActivity.class);

                intent1.putExtra("user_phone", myPhone);
                startActivity(intent1);
            }
        });

    }
    //开始定位
    private void requestLocation() {

        initLocation();

        /*baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final String info = (String) marker.getExtraInfo().get("info");
                InfoWindow infoWindow;
                //动态生成一个Button对象，用户在地图中显示Infowindow
                final Button textInfo = new Button(getApplicationContext());
                textInfo.setBackgroundResource(R.drawable.locate_popup);
                textInfo.setPadding(10,10,10,10);
                textInfo.setTextColor(Color.BLACK);
                textInfo.setTextSize(12);
                textInfo.setText(info);
                //得到点击的覆盖物的经纬度
                LatLng ll = marker.getPosition();
                textInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Map.this, "你点击了"+info, Toast.LENGTH_SHORT).show();
                    }
                });
                //将marker所在的经纬度的信息转化成屏幕上的坐标
                Point p = baiduMap.getProjection().toScreenLocation(ll);
                p.y -= 90;
                LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p);
                //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
                infoWindow = new InfoWindow(textInfo,llInfo,0);
                baiduMap.showInfoWindow(infoWindow);//显示此infoWindow
                //让地图以备点击的覆盖物为中心
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(llInfo);
                baiduMap.setMapStatus(status);
                return true;
            }
        });*/
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
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("维度：").append(location.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(location.getLongitude()).append("\n");
                    currentPosition.append("定位方式：");
                    if(location.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if(location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络");
                    }
                    positionText.setText(currentPosition);
                }
            });
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
/*
    //添加覆盖物
    private void addOverlay() {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions options = null;
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location);
        for (BDMapData data : bdMapClientList) {
            point = new LatLng(data.getLatitude(), data.getLongitude());
            options = new MarkerOptions().position(point).icon(bitmap);
            marker = (Marker) baiduMap.addOverlay(options);
            //Bundle用于通信
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", data.getName() + "维度:" + data.getLatitude() + "经度:" + data.getLongitude());
            marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到他
        }
        //将地图移动到最后一个标识点
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        baiduMap.setMapStatus(status);


//

        //清空地图
        baiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(BDMapData data :bdMapClientList){
            //获取经纬度
            latLng = new LatLng(data.getLatitude(),data.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
                    .draggable(true); // 设置手势拖拽;
            //添加marker
            marker = (Marker) baiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", data.getName()+"维度:"+data.getLatitude()+"经度:"+data.getLongitude());
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
        //添加marker点击事件的监听
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                BDMapData infoUtil = (BDMapData) bundle.getSerializable("info");
                //将信息显示在界面上

                TextView tv_name = (TextView)findViewById(R.id.position_text_view);
                tv_name.setText(infoUtil.getName());


                //infowindow中的布局
                TextView tv = new TextView(Map.this);
                tv.setBackgroundResource(R.drawable.locate_popup);
                tv.setPadding(20, 10, 20, 20);
                tv.setTextColor(android.graphics.Color.WHITE);
                tv.setText(infoUtil.getName());
                tv.setGravity(Gravity.CENTER);
                bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                //infowindow位置
                LatLng latLng = new LatLng(infoUtil.getLatitude(), infoUtil.getLongitude());
                //infowindow点击事件
                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //隐藏infowindow
                        baiduMap.hideInfoWindow();
                    }
                };
                //显示infowindow
                InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47, listener);
                baiduMap.showInfoWindow(infoWindow);
                return true;
            }
        });
    }


        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mLastX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);
            //更新经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //配置定位图层显示方式，使用自己的定位图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
            baiduMap.setMyLocationConfigeration(configuration);
            if(isFirstLocate){
                //获取经纬度
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                //mBaiduMap.setMapStatus(status);//直接到中间
                baiduMap.animateMapStatus(status);//动画的方式到中间
                isFirstLocate = false;
                showInfo("位置：" + location.getAddrStr());
            }
        }

    private void showInfo(String str){
        Toast.makeText(Map.this, str, Toast.LENGTH_SHORT).show();
    }

    }*/
}
