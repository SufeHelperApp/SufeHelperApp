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

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mailbox extends AppCompatActivity {

    private user user;

    private String myPhone;

    Connection con;
    ResultSet rs;

    private String taskString = "";
    private List<task> searchList = new ArrayList<>();
    private List<task> taskList = new ArrayList<>();

    private int msgCount;
    String [] IDstring;
    int num;


    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //user
        myPhone = getIntent().getStringExtra("user_phone");
        num = getIntent().getIntExtra("num",1);

        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '" + myPhone + "'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs, user.class);
            for (int i = 0; i < list.size(); i++) {
                userList.add((user) list.get(i));
            }
            user = userList.get(0);
            msgCount = user.getMsg();
            String msgList = user.getMsgTaskListString();
            //目前用户未查看的消息
            Log.d("msgCountOri",String.valueOf(msgCount));
            Log.d("msgListOri",String.valueOf(msgList));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
                switch (item.getItemId()) {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Mailbox.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Mailbox.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Mailbox.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

/*
        //清除消息红点

        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '" + user.getMyName() + "'");

            if (rs.next()) {
                String sql1 = "UPDATE `user` SET `msg`= '0' WHERE myName='" + user.getMyName() + "'";
                st.executeUpdate(sql1);
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }*/

        //显示消息
        //1.确定string不空 2.将string转化为ID数组 3.去除重复ID 4.提取每个int，对应到task,加入taskList

        taskString = user.getMsgTaskListString();

        //1.确定string不空
        if (!taskString.isEmpty()) {

            //2.将string转化为IDstring数组
            IDstring = taskString.split(";");

            //3.去除重复ID
            List<Integer> taskID = new ArrayList<Integer>();
            for (int i = 0; i< IDstring.length; i++) {
                int id = Integer.parseInt(IDstring[i]);
                if(!taskID.contains(id)) {
                    taskID.add(id);
                }
            }

            //4.提取每个id，对应到task,加入taskList

            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                con = DbUtils.getConn();
                Statement st = con.createStatement();

                for (int i = 0; i < taskID.size(); i++) {

                    rs = st.executeQuery("SELECT * FROM `task` WHERE `taskID` = '" + taskID.get(i) + "'");
                    List list = DbUtils.populate(rs, task.class);
                    task task = (task) list.get(0);
                    taskList.add(task);

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }

            }

            if(num == 2){

                Log.d("num","2");

                //若点击任务更新，消除一个任务，msg-1

                Log.d("taskListBeforeReduced",String.valueOf(taskList.size()));

                task task = (task) getIntent().getSerializableExtra("task_selected");
                Log.d("taskIDdeleted",String.valueOf(task.getTaskID()));
                List<task>temp = new ArrayList<>();
                temp = taskList;
                for(int i = 0; i<taskList.size(); i++){
                    if(taskList.get(i).getTaskID()==task.getTaskID()){
                        temp.remove(i);
                        break;
                    }
                }
                taskList = temp;

                Log.d("taskListAfterReduced",String.valueOf(taskList.size()));

                //新建减少一个任务的msgTaskListString

                String s ="";

                for(int i = 0; i<taskList.size(); i++){
                    if(s.isEmpty()){
                        s = s + String.valueOf(taskList.get(i).getTaskID());
                    }else{
                        s =  s + ";" + String.valueOf(taskList.get(i).getTaskID());
                    }
                }

                Log.d("msgListNow",s);

                try {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    con = DbUtils.getConn();
                    Statement st = con.createStatement();

                    if(msgCount>0) {

                        Log.d("msgCount还能减",String.valueOf(msgCount));

                        msgCount = msgCount - 1;

                        Log.d("msgCount减了",String.valueOf(msgCount));

                        String sql1 = "UPDATE `user` SET `msg`= '" + msgCount + "' WHERE myName='" + user.getMyName() + "'";
                        st.executeUpdate(sql1);

                    }

                    String sql2 = "UPDATE `user` SET `msgTaskListString`= '"+s+"' WHERE myName='" + user.getMyName() + "'";
                    st.executeUpdate(sql2);


                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null)
                        try {
                            con.close();
                        } catch (SQLException e) {
                        }

                }

            }


        Log.d("taskListshown", String.valueOf(taskList.size()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList, user, 5); //taskAdapter中获得当前user
        recyclerView.setAdapter(adapter);

        }

    }




    @Override
    public void onBackPressed(){

        //按返回键时，清除消息列表

        /*

        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '" + user.getMyName() + "'");

            if (rs.next()) {
                String sql1 = "UPDATE `user` SET `msgTaskListString`= '' WHERE myName='" + user.getMyName() + "'";
                st.executeUpdate(sql1);
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }*/

        Intent intent = new Intent(Mailbox.this, My_HomeActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();


    }
}
