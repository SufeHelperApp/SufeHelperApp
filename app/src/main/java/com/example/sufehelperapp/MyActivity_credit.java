package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_credit extends AppCompatActivity {

    private user user;

    private String myPhone;

    Connection con;
    ResultSet rs;

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

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
                        Intent intent1 = new Intent(MyActivity_credit.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(MyActivity_credit.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(MyActivity_credit.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        //从数据库显示rating

        RatingBar ratingBar = findViewById(R.id.my_credit_rating_bar);
        //TODO: 获取当前user的averageScore
        ratingBar.setRating(user.getAverageScore());

        TextView ratingTextView = findViewById(R.id.my_credit_average_score_text);
        ratingTextView.setText(String.valueOf(user.getAverageScore()));


        //显示违约任务卡片

        //检查所有任务当前是否违约
        updateAllTaskStatus();

        //TODO: 调用所有帮助者为当前用户的任务，ifDefault = true，放入taskList
        /*
        List<task> taskList = DataSupport.where("helperName = ?",user.getMyName())
                .where("ifDefault = ? ","1").find(task.class);*/

        List<task> taskList = new ArrayList<>();

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st1 = con.createStatement();
            rs= st1.executeQuery("SELECT * FROM `task` WHERE `helperName` = '"+user.getMyName()+"' AND `ifDefault` = '1'");
            List list = DbUtils.populate(rs,task.class);

            for(int i = 0; i<list.size(); i++){
                taskList.add((task)list.get(i));
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_credit_recycler);
            GridLayoutManager layoutManager = new GridLayoutManager(this,1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TaskAdapter(taskList,user,4);
            recyclerView.setAdapter(adapter);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_credit.this, My_HomeActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_credit.this, My_HomeActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }

    public void updateAllTaskStatus() {

        List<task> taskAllList = new ArrayList<>();

        try {
            con = DbUtils.getConn(); //initialize connection
            Statement st = con.createStatement(); //initialize connection
            rs = st.executeQuery("SELECT * FROM `task` WHERE `ifShutDown` = '0'");

            List list = DbUtils.populate(rs, task.class);
            for (int i = 0; i < list.size(); i++) {
                taskAllList.add((task) list.get(i));
            }

            Log.d("select:updateAllnum", String.valueOf(taskAllList.size()));

            for (task task : taskAllList) {

                String id = task.getPreciseLaunchTime();

                if (task.getProgress() < 3) {
                    if (TimeUtils.isDateOneBigger(TimeUtils.getNowTime(), task.getDdl())) {
                        if (task.getIfAccepted()) {
                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '1' " +
                                    ", `ifShutDown` = '1', `progress` = '7', `statusText` = '接受者违约' " +
                                    "WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);
                            /*
                            task.setIfDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = true;
                            task.ifShutDown = true; //接收者未及时完成，关闭项目
                            task.setProgress(7);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());
                                    */

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);
                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("违约->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }

                            //给接收者发一条消息
                            List<user> helperList = DataSupport.where("myName = ?", task.getHelperName())
                                    .find(user.class);
                            user helper = helperList.get(0);

                            if (!helper.getMsgTaskList().contains(task)) {
                                helper.addMsg();
                                helper.addMsgTaskList(task.getPreciseLaunchTime());
                                helper.updateAll("phonenumber = ?", helper.getPhonenumber());
                                Log.d("违约->接收者", helper.getMyName()
                                        + " " + String.valueOf(helper.getMsg()) + " " + String.valueOf(helper.
                                        getMsgTaskList().size()));
                            }

                            */


                        } else {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = true;
                            task.ifDefault = false;
                            task.ifShutDown = true; //过期未接收，关闭项目
                            task.setProgress(6);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '1' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '1', `progress` = '6', `statusText` = '逾期未被接收' " +
                                    "WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);

                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("过期->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }
                            */


                        }
                    } else {
                        if (task.getIfAccepted()) {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                        } else {
                            /*
                            task.ifDisplayable = true;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '1' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                        }
                    }
                } else if (task.getProgress() == 3) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '已完成,待支付' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);


                } else if (task.getProgress() == 4) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '待评价' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);

                } else if (task.getProgress() == 5) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = true;  //评论完成，关闭任务
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '1', `statusText` = '任务已结束' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);

                }
        /*
                task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                        task.getLauncherName());*/

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
