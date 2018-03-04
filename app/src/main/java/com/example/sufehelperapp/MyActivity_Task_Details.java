package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyActivity_Task_Details extends AppCompatActivity {

    private task task;

    public static final String TASK_SELECTED = "task_selected";
    public static final String USER_NOW = "user_now";

    private user user;

    private RecyclerView rvTrace;
    private List<Trace> traceList = new ArrayList<>(4);
    private TraceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_details);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("MyActivity_Task_Details",myName);

        task = (task) getIntent().getSerializableExtra("task_selected");

        Button button = (Button) findViewById(R.id.title_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Mytask.class);
                startActivity(intent);
            }
        });

        Button button1 = (Button) findViewById(R.id.to_payoff);
        Button button2 = (Button) findViewById(R.id.to_finish);
        if(user.getMyName().equals(task.getHelperName())){ //当前用户是helper，只显示finish按钮
            button1.setVisibility(View.GONE);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setProgress(3);
                    task.setAchievetime();
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());

                    task.updateTaskStatus();
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());

                    initData();
                }
            });
        }
        if (user.getMyName().equals(task.getLauncherName())) { //当前用户是launcher，只显示payoff按钮
            button2.setVisibility(View.GONE);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setProgress(4);
                    task.setFinishtime();
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());

                    task.updateTaskStatus();
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());

                    initData();
                }
            });
        }


        findView();
        initData();
    }



    private void findView() {
        rvTrace = (RecyclerView) findViewById(R.id.recycler_time_list);
    }

    private void initData() {
        //模拟一些假的数据
        String launchtime = task.getLaunchtime();
        traceList.add(new Trace(launchtime,"[任务发布] 任务已发布"));
        if(task.getProgress()>=2) {
            String accepttime = task.getAccepttime();
            traceList.add(new Trace(accepttime,"[接受任务] 该任务已被接受"));
        }
        if(task.getProgress()>=3) {
            String achievetime = task.getAchievetime();
            traceList.add(new Trace(achievetime,"[完成情况] 接受者已完成任务"));
        }
        if(task.getProgress()>=4) {
            String finishtime = task.getFinishtime();
            traceList.add(new Trace(finishtime,"[支付情况] 发布者已支付报酬"));
        }

        adapter = new TraceListAdapter(this,traceList);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);
    }
}
