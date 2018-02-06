package com.example.sufehelperapp;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Task_ErrandChoose1Activity extends AppCompatActivity {

    private task[] tasks =
            {new task(0,"文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","5"),
            new task(1,"戴晓东", R.drawable.banana, "13812345678",
                    "拿快递","快递中心","18/2/10","7"),
            new task(2,"刘宇涵", R.drawable.orange,"13712345678",
                    "买饭","新食堂","18/2/17","6")};

    private List<task> taskList = new ArrayList<>();

    private TaskAdapter adapter1;
    private TaskAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__errand_choose1);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFruits1();
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_choose1);
        GridLayoutManager layoutManager1 = new GridLayoutManager(this,1);
        recyclerView1.setLayoutManager(layoutManager1);
        adapter1 = new TaskAdapter(taskList);
        recyclerView1.setAdapter(adapter1);

        initFruits2();
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_choose2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this,1);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new TaskAdapter(taskList);
        recyclerView2.setAdapter(adapter2);

    }

    private void initFruits1(){
        taskList.clear();
        for(int i=0; i<2; i++){
            Random random = new Random();
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
    }

    private void initFruits2(){
        taskList.clear();
        for(int i=0; i<10; i++){
            Random random = new Random();
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
    }


}

