package com.example.sufehelperapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.baidu.mapapi.map.TextOptions;
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

    private Connection con;
    private Statement st;
    private ResultSet rs;

    private String myPhone;
    user user;

    private double latNow = 31.2463430229; //TODO
    private double lngNow = 121.5088982034; //TODO

    private List<Marker> marker = new ArrayList<>();
    private List<InfoWindow> window = new ArrayList<>();
    private Marker mMarker;
    private MarkerInfoUtil Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //获得当前user
        myPhone = getIntent().getStringExtra("user_phone");
        Log.d("myPhone", myPhone);

        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '" + myPhone + "'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs, user.class);
            for (int i = 0; i < list.size(); i++) {
                userList.add((user) list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }


        //此方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_for_task);
        mMapView = (MapView) findViewById(R.id.bmapView_task);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16f);
        baiduMap.setMapStatus(msu);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 定位初始化
        locationClient = new LocationClient(this);
        firstLocation = true;
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        locationClient.setLocOption(option);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Map_for_task.this, ExploreActivity.class);
                        //intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Map_for_task.this, My_HomeActivity.class);
                        //intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        Button b4 = (Button) findViewById(R.id.launch_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Map_for_task.this, Task_LaunchActivity.class);

                //intent4.putExtra("user_phone", myPhone);
                //intent4.putExtra("num", 1);
                startActivity(intent4);


            }
        });

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
                //获得当前经纬度
                //latNow = location.getLatitude();
                //lngNow = location.getLongitude();

                // 构造定位数据
                /*
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();*/
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(latNow)
                        .longitude(lngNow).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);


                // 第一次定位时，将地图位置移动到当前位置
                if (firstLocation) {/*
                    firstLocation = false;
                    LatLng xy = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                    baiduMap.animateMapStatus(status);*/
                    firstLocation = false;
                    LatLng xy = new LatLng(latNow,
                            lngNow);
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

        List<MarkerInfoUtil> infos = new ArrayList<MarkerInfoUtil>();
        List<task> taskMatched = new ArrayList<task>();
        String sum;
        String name;

        //选出有任务的地点
        try {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn(); //initialize connection
            st = con.createStatement(); //initialize connection

            String sql = "select * from `task` group by `location` ";

            rs = st.executeQuery(sql);
            rs.last();// 移动到最后                                
            Log.d("rs", String.valueOf(rs.getRow()));// 获得结果集长度
            rs.beforeFirst();//获得该location的任务数

            //rs转化为task
            List list = DbUtils.populate(rs, task.class);
            for (int i = 0; i < list.size(); i++) {
                taskMatched.add((task) list.get(i));
            }

            rs.close();

            for (int i = 0; i < taskMatched.size(); i++) {
                double latitude = taskMatched.get(i).getLatitude();
                double longitude = taskMatched.get(i).getLongtitude();
                name = taskMatched.get(i).getLocation();
                Log.d("地点", name);


                //确保任务未被接受
                String sql2 = "select count(*) from `task` where `location` = '" + name + "' AND `ifDisplayable` = '1' ";
                ResultSet rs1 = st.executeQuery(sql2);

                //提出Location
                if (rs1.next()) {
                    sum = String.valueOf(rs1.getInt(1));
                    Log.d("任务数量", sum);
                } else {
                    sum = "0";
                }

                rs1.close();

                //添加Location
                //TODO: info.add(new...(获取各满足条件地点经,纬度,地点名称,地点任务总数));
                infos.add(new MarkerInfoUtil(latitude, longitude, name, sum));
            }

            addOverlay(infos);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_for_task:

                break;
            case R.id.main:
                Intent intent4 = new Intent(Map_for_task.this, Task_HomeActivity.class);
                //intent1.putExtra("user_phone", myPhone);
                startActivity(intent4);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tab,menu);
        return true;
    }


    //显示marker
    private void addOverlay(final List<MarkerInfoUtil> infos) {
        //清空地图
        baiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_fortask);
        LatLng latLng = null;
        OverlayOptions options;

        for (MarkerInfoUtil info : infos) {

            //添加每个marker

            latLng = new LatLng(info.getLatitude(), info.getLongitude());

            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
            ;

            mMarker = (Marker) baiduMap.addOverlay(options);
            marker.add(mMarker);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("location", info.getName());
            marker.get(marker.size() - 1).setExtraInfo(bundle);

            /*

            //添加infowindow

            TextView sum = new TextView(getApplicationContext());
            Log.d("该地任务数量", info.getsum());
            sum.setText(info.getsum());
            TextPaint tp = sum.getPaint();
            tp.setFakeBoldText(true);
            //info文字
            BitmapDescriptor bitmap_text = BitmapDescriptorFactory.fromView(sum);
            //info位置
            LatLng latLng1 = new LatLng(info.getLatitude(), info.getLongitude());
            //info监听器
            InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    //baiduMap.hideInfoWindow();
                }
            };
            InfoWindow w = new InfoWindow(bitmap_text, latLng1, -30, listener);
            window.add(w);
            //baiduMap.showInfoWindow(w);*/


            //定义文字所显示的坐标点

            //构建文字Option对象，用于在地图上添加文字
            OverlayOptions textOption = new TextOptions()
                    .bgColor(0xAAFFFF00)
                    .fontSize(25)
                    .zIndex(10)
                    .fontColor(0xFFFF00FF)
                    .text(info.getsum())
                    .position(latLng);

            //在地图上添加该文字对象并显示
            baiduMap.addOverlay(textOption);

        }


        Log.d("marker数量", String.valueOf(marker.size()));
        Log.d("window数量", String.valueOf(window.size()));


//TODO:各图标点击跳转的界面不一样，ALL_task是新增的用来做任务listview的，点击跳转到该界面
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent1 = new Intent(Map_for_task.this, All_task.class);
                String location = marker.getExtraInfo().getString("location");
                Log.d("转到该任务列表",location);
                intent1.putExtra("location",location);
                intent1.putExtra("user_phone", myPhone);
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
