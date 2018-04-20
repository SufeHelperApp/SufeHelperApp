package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Selection3 extends AppCompatActivity {

    private String POIName;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration config;

    List<task> taskList = new ArrayList<>();
    private String myPhone;

    private Connection con;
    Statement st;
    private ResultSet rs;

    private user user;
    String[] subtaskTypes;

    private int num = 0;

    private String subtaskType;
    private String payment;

    private int position1=0;
    private int position2=0;
    private int position3=0;
    private int position4=0;

    private String pay1string = "0";
    private String pay2string = "10000";

    private LocationClient locationClient = new LocationClient(this);
    private double latNow; //TODO
    private double lngNow; //TODO

    /*
    public void requestLocation() {
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
                latNow = location.getLatitude();
                lngNow = location.getLongitude();
            }
        });
    }*/

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
                //baiduMap.setMyLocationData(locData);

                // 第一次定位时，将地图位置移动到当前位置
                if (firstLocation)
                {
                    firstLocation = false;
                    LatLng xy = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                    //baiduMap.animateMapStatus(status);
                }

                latNow = location.getLatitude();
                lngNow = location.getLongitude();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
            Log.d("user",user.getMyName());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

        //此方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_selection);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        //mMapView =(MapView)findViewById(R.id.bmapView_task);
        //baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16f);
        //baiduMap.setMapStatus(msu);


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



        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Selection3.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Selection3.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Selection3.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        final Spinner subtaskView = (Spinner) findViewById(R.id.selection_subtask);
        final String[] subtaskTypes = getResources().getStringArray(R.array.subtasks_counsel);

        final Spinner distView = (Spinner) findViewById(R.id.selection_area);

        final Spinner paymentView = (Spinner) findViewById(R.id.selection_payment);
        final String[] payments = getResources().getStringArray(R.array.payments);

        final Spinner ddlView = (Spinner) findViewById(R.id.selection_ddl);
        final String[] ddls = getResources().getStringArray(R.array.ddls);


        StatusUtils.updateAllTaskStatus();

        double dis = MapUtils.getDistance(latNow,lngNow,31.3079395836,121.5089110332);
        Log.d("lat",String.valueOf(latNow));
        Log.d("lng",String.valueOf(lngNow));
        Log.d("dis",String.valueOf(dis));

        if(num == 0 ) {


            try{

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                con = DbUtils.getConn(); //initialize connection
                st = con.createStatement(); //initialize connection
                rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' AND `ifDisplayable` = '1'");

                //List<task> sampleList = new ArrayList<>();

                List list = DbUtils.populate(rs,task.class);
                for(int i = 0; i < list.size(); i++){
                    taskList.add((task)list.get(i));
                }

                //taskList = sampleList;
                Log.d("msg",String.valueOf(taskList.size()));


                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        num++;


        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                subtaskType = subtaskTypes[position];

                if(num!=0) {

                    if (position1 == 0) {

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    } else if (position1 != 0) {

                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }

                    //check dist

                    List<task> demand1;

                    switch (position2) {
                        case 0:
                            break;
                        case 1:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin500m(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 2:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin1km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 3:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin3km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                    }

                    //check ddl

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        distView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position2 = position;


                if(num!=0) {


                    if (position1 == 0) {


                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0) {


                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    //check dist

                    List<task> demand1;

                    switch (position2) {
                        case 0:
                            break;
                        case 1:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin500m(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 2:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin1km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 3:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin3km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        paymentView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position3 = position;
                payment = payments[position];


                switch (payment) {
                    case "报酬": {
                        pay1string = "0";
                        pay2string = "10000";
                        break;
                    }
                    case "0-5元": {
                        pay1string = "0";
                        pay2string = "5";
                        break;
                    }
                    case "6-10元": {
                        pay1string = "6";
                        pay2string = "10";
                        break;
                    }
                    case "11-15元": {
                        pay1string = "11";
                        pay2string = "15";
                        break;
                    }
                    case "15元以上": {
                        pay1string = "15";
                        pay2string = "10000";
                        break;
                    }
                }

                if (num != 0) {

                    if (position1 == 0) {

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0) {


                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    //check dist

                    List<task> demand1;

                    switch (position2) {
                        case 0:
                            break;
                        case 1:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin500m(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 2:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin1km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 3:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin3km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ddlView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position4 = position;

                if(num!=0) {

                    if (position1 == 0) {

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0) {


                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    //check dist

                    List<task> demand1;

                    switch (position2) {
                        case 0:
                            break;
                        case 1:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin500m(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 2:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin1km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                        case 3:
                            demand1 = new ArrayList<>();
                            for (task task : taskList) {
                                if (MapUtils.isTaskWithin3km(latNow,lngNow,task.getLatitude(),task.getLongtitude())) {
                                    demand1.add(task);
                                }
                            }
                            taskList = demand1;
                            break;
                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection3.this, Task_ErrandActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Selection3.this, Task_ErrandActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }



}




