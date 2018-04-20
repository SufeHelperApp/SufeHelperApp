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
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Task_SkillActivity extends AppCompatActivity {

    private user user;
    private String myPhone;

    private Connection con;
    private ResultSet rs;

    private List<task> taskList = new ArrayList<>();

    private TaskAdapter adapter;

    private LocationClient locationClient = new LocationClient(this);
    private double latNow;
    private double lngNow;

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__skill);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Task_SkillActivity.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Task_SkillActivity.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Task_SkillActivity.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        taskList = initTaskSuggestions();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_skill);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList,user,1); //taskAdapter中获得当前user
        recyclerView.setAdapter(adapter);

        Button btn1 = findViewById(R.id.btn_select_skill);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_SkillActivity.this, Selection2.class);
                intent1.putExtra("user_phone", myPhone);
                startActivity(intent1);
            }
        });

    }

    //推荐算法

    private List<task> initTaskSuggestions(){

        List<task> preferredTasks = new ArrayList<>();
        List<task> taskMatched = new ArrayList<>();

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st1 = con.createStatement();
            rs= st1.executeQuery("SELECT * FROM `task` WHERE `taskType` = '技能' AND `ifDisplayable` = '1'");
            List list = DbUtils.populate(rs,task.class);

            if (list.isEmpty()){

            }else {

                try {

                    for(int i = 0; i < list.size(); i++){
                        taskMatched.add((task)list.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            st1.close();


            for(task task:taskMatched){

                if(task.getLauncherName().equals(user.getMyName())){
                    continue;
                }

                int credit = 0;

                //1.距离小于1km
                if(MapUtils.isTaskWithin1km(latNow,lngNow,task.getLatitude(),task.getLongtitude())){
                    credit++;
                }


                //2.符合特长
                String[] specialty = {"占座", "拿快递", "买饭", "买东西", "拼单", "电子产品修理", "家具器件组装",
                        "学习作业辅导", "技能培训", "找同好", "周边服务", "考研出国经验", "求职经验", "票务转让", "二手闲置"};

                String str = user.getSpecialtyString();
                for(int i = 0; i < 15; i++){
                    if(str.charAt(i)=='1'){
                        if(specialty[i].equals(task.getSubtaskType())){
                            credit++;
                            break;
                        }
                    }
                }


                //3.发布者是曾经帮助过的用户

                String thisLauncher = task.getLauncherName();

                try{

                    Statement st2 = con.createStatement();
                    rs= st2.executeQuery("SELECT * FROM `task` WHERE `launcherName` = '"+thisLauncher+"' AND `helperName` = '"+user.getMyName()+"'");

                    if (!rs.wasNull()){
                        credit++;
                    }

                    st2.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //4.价格很高
                if(task.getPayment()>=20){
                    credit++;
                }

                //5.时间紧急
                if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                    credit++;
                }

                //符合两项即推荐
                if(credit>=1 && preferredTasks.size()<3 && !task.getLauncherName().equals(user.getMyName())){

                    preferredTasks.add(task);

                }

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return preferredTasks;

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Task_SkillActivity.this, Task_HomeActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }
}

