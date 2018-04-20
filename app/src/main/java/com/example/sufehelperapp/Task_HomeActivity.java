package com.example.sufehelperapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.LocationClient;



public class Task_HomeActivity extends AppCompatActivity {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;

    private NotificationManager manager;
    int notification_id;

    private user user;
    private String myPhone;

    private double latSpot;
    private double lngSpot;

    Connection con;
    Statement st;
    ResultSet rs;

    float payment;
    int num;

    private LocationClient locationClient = new LocationClient(this);
    private double latNow = 31.2459531645; //TODO
    private double lngNow = 121.5059477735; //TODO

    public void requestLocation() {
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
                latNow = location.getLatitude();
                lngNow = location.getLongitude();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //user
        myPhone = getIntent().getStringExtra("user_phone");
        Log.d("myPhone",myPhone);

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            st = con.createStatement();
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

        ImageView view = (ImageView) findViewById(R.id.btn_note);
        sendNotification(view);

        //map

        /*
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数*/

        //在任务量>=5的地点200米内，发通知
        try{

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn(); //initialize connection
            st = con.createStatement(); //initialize connection
            ResultSet rs1;
            ResultSet rs2;

            //选出任务数>=5的location的经纬度
            String sql = "select * from `task` group by `location` having count(*)>=5";
            String sql1 = "select sum(payment) from `task` group by `location` having count(*)>=5";
            String sql2 = "select count(*) from `task` group by `location` having count(*)>=5";

            rs = st.executeQuery(sql);



            
            //发通知
            if(rs.next()){
                double latTask = rs.getDouble("latitude");
                double lngTask = rs.getDouble("longtitude");
                String location = rs.getString("location");
                rs1 = st.executeQuery(sql1);
                if(rs1.next()) {
                    payment = rs1.getFloat(1);
                }
                rs2 = st.executeQuery(sql2);
                if(rs2.next()) {
                    num = rs2.getInt(1);
                }

                if(MapUtils.getDistance(latNow,lngNow,latTask,lngTask)<=1000){
                    Log.d("msg","broadcast");
                    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(Task_HomeActivity.this);
                    builder.setSmallIcon(R.drawable.ic_task);
                    builder.setTicker("iTask");
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentTitle("通知");
                    builder.setContentText("您附近的 " + location + "有" + String.valueOf(num) + "个任务等待接收，总报酬为 "
                    + String.valueOf(payment) + "元，顺路赚外快吧！");

                    Intent intent = new Intent(Task_HomeActivity.this, Selection1.class);
                    PendingIntent ma = PendingIntent.getActivity(Task_HomeActivity.this,0,intent,0);
                    builder.setContentIntent(ma);//设置点击过后跳转的activity

                    //builder.setDefaults(Notification.DEFAULT_SOUND);//设置声音
                    //builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
                    builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
                    //builder.setDefaults(Notification.DEFAULT_ALL);//设置全部

                    Log.d("msg","build");

                    Notification notification = builder.build();//4.1以上用.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                    manager.notify(0,notification);
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*

        NotifyListener mNotifyer = new NotifyListener();
        mNotifyer.SetNotifyLocation(latSpot,lngSpot,200,"gps");
        //4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        mLocationClient.registerNotify(mNotifyer);*/



        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_HomeActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_HomeActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


        Button b1 = (Button) findViewById(R.id.btn_errand);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_HomeActivity.this,Task_ErrandActivity.class);
                intent1.putExtra("user_phone", myPhone);
                startActivity(intent1);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn_skill);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Task_HomeActivity.this, Task_SkillActivity.class);
                intent2.putExtra("user_phone", myPhone);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_counsel);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Task_HomeActivity.this, Task_CounselActivity.class);
                intent3.putExtra("user_phone", myPhone);
                startActivity(intent3);
            }
        });
        Button b4 = (Button) findViewById(R.id.launch_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Task_HomeActivity.this, Task_LaunchActivity.class);

                intent4.putExtra("user_phone", myPhone);
                intent4.putExtra("num", 1);
                startActivity(intent4);


            }
        });


    }

    public void sendNotification(View view){
        //实例化通知管理器
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化通知
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("大事件");//设置通知标题
        builder.setContentText("不要放孔明灯，容易起火");//设置通知内容
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置通知的方式，震动、LED灯、音乐等
        builder.setAutoCancel(true);//点击通知后，状态栏自动删除通知
        builder.setSmallIcon(android.R.drawable.ic_media_play);//设置小图标
        builder.setContentIntent(PendingIntent.getActivity(this,0x102,new Intent(this,ExploreActivity.class),0));//设置点击通知后将要启动的程序组件对应的PendingIntent
        Notification notification=builder.build();

        //发送通知
        notificationManager.notify(0x101,notification);

    }


    @Override
    public void onBackPressed(){

    }



}
