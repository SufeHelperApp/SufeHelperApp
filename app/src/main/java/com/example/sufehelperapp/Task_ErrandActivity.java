package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Task_ErrandActivity extends AppCompatActivity {

    private user user;

    private List<task> taskList = new ArrayList<>();

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__errand);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Task_ErrandActivity",myName);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_ErrandActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_ErrandActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


        taskList = initTaskSuggestions();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_errand);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList,user); //taskAdapter中获得当前user
        recyclerView.setAdapter(adapter);

        ImageView img1 = findViewById(R.id.errand_ic1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_ErrandActivity.this, Selection1.class);
                intent1.putExtra("user_now", user);
                startActivity(intent1);
            }
        });

    }

    //推荐算法

    private List<task> initTaskSuggestions(){

        List<task> preferredTasks = new ArrayList<>();
        List<task> taskMatched = DataSupport.where("taskType = ?","跑腿").find(task.class);

        for(task task:taskMatched){

            int credit = 0;

            //位置近
            if(task.getArea() == user.getDormArea()){
                credit++;
            }
            //符合特长
            for(String specialty:user.getSpecialty()){
                if(specialty.equals(task.getSubtaskType())){
                    credit++;
                }
            }

            //发布者是曾经帮助过的用户
            List<task> userTaskList = DataSupport.where("helperName = ?",user.getMyName())
                    .find(task.class);
            for(task task1:userTaskList) {
                if (task1.getLauncherName() == task.getLauncherName()){
                    credit++;
                }
            }

            //价格很高
            if(task.getPayment()>=20){
                credit++;
            }

            //时间紧急
            if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                credit++;
            }

            //符合两项即推荐
            if(credit>=2 && preferredTasks.size()<4){

                preferredTasks.add(task);

            }

        }

        return preferredTasks;

    }

}
