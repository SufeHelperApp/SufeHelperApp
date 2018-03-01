package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyActivity_Task_Details extends AppCompatActivity {

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

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Mytask.class);
                startActivity(intent);
            }
        });

        findView();
        initData();
    }

    private void findView() {
        rvTrace = (RecyclerView) findViewById(R.id.recycler_time_list);
    }
    private void initData() {
        //模拟一些假的数据
        traceList.add(new Trace("2018-02-28","[任务发布] 任务已发布"));
        traceList.add(new Trace("2018-02-28","[接受任务] 该任务已被接受"));
        traceList.add(new Trace("2018-02-28","[完成情况] 接受者已完成任务"));
        traceList.add(new Trace("2018-02-28","[支付情况] 发布者已支付报酬"));
        adapter = new TraceListAdapter(this,traceList);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);
    }
}
