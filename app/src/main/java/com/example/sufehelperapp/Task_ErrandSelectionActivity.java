package com.example.sufehelperapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Task_ErrandSelectionActivity extends AppCompatActivity {

    private task[] tasks =
            {new task(0,"文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","5"),
            new task(1,"戴晓东", R.drawable.banana, "13812345678",
                    "拿快递","快递中心","18/2/10","7"),
            new task(2,"刘宇涵", R.drawable.orange,"13712345678",
                    "买饭","新食堂","18/2/17","6")};

    private List<task> taskList = new ArrayList<>();

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__errand_selection);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTasks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selected_cards);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

    }

    private void initTasks(){
        taskList.clear();
        for(int i=0; i<10; i++){
            Random random = new Random();
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
    }


}

