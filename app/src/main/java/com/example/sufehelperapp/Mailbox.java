package com.example.sufehelperapp;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class Mailbox extends AppCompatActivity {

    private user user;

    private List<String> taskStringList = new ArrayList<>();
    private List<task> searchList = new ArrayList<>();
    private List<task> taskList = new ArrayList<>();


    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("My_HomeActivity",user.getMyName());

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Mailbox.this, Task_HomeActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Mailbox.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Mailbox.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        user.clearMsg();
        user.updateAll("phonenumber = ? and myName = ?",user.getPhonenumber(),user.getMyName());
        Log.d("maibox:after clear",user.getMyName() + " " + String.valueOf(user.getMsg()));

        //将时间转化为任务
        //TODO:1.获得Msg 2.获得MsgTaskList(这是一个数组，能不能作为数据库中的一个变量？)
        taskStringList = user.getMsgTaskList();
        Log.d("taskStringList",String.valueOf(user.getMsgTaskList().size()));

        if(!user.getMsgTaskList().isEmpty()){

            for(String time:taskStringList){
                searchList = DataSupport.where("preciseLaunchTime = ?",time).find(task.class);
                task task = searchList.get(0);
                taskList.add(task);
            }
            Log.d("msgtaskList",String.valueOf(taskList.size()));

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mail);
            GridLayoutManager layoutManager = new GridLayoutManager(this,1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TaskAdapter(taskList,user,5); //taskAdapter中获得当前user
            recyclerView.setAdapter(adapter);

        }

        /*

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.clearMsgTaskList(); //清空消息列表
                user.clearMsg();
                user.updateAll("phonenumber = ?",user.getPhonenumber());
                Intent intent = new Intent(Mailbox.this, My_HomeActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onBackPressed(){
        user.clearMsg();
        user.updateAll("phonenumber = ? and myName = ?",user.getPhonenumber(),user.getMyName());
        Log.d("mailbox:back",user.getMyName() + " " + String.valueOf(user.getMsg()));
        Intent intent = new Intent(Mailbox.this, My_HomeActivity.class);
        intent.putExtra("user_now",user);
        startActivity(intent);
        finish();
    }
}
