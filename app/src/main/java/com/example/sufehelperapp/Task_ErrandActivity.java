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
import java.util.Random;

public class Task_ErrandActivity extends AppCompatActivity {

    private user user;

    private task[] tasks =
            {new task("文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","9:00",
                    5,"微信联系"),
                    new task("戴晓东", R.drawable.banana, "13812345678",
                            "拿快递","快递中心","18/2/10","10:00",
                            7,"微信联系"),
                    new task("刘宇涵", R.drawable.orange,"13712345678",
                            "买饭","新食堂","18/2/17","11:00",
                            6,"微信联系")};

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
                        Intent intent1 = new Intent(Task_ErrandActivity.this, MainActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_ErrandActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_ErrandActivity.this, MyActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        initTasks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_errand);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        ImageView img1 = findViewById(R.id.errand_ic1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_ErrandActivity.this, Task_SelectionEActivity.class);
                intent1.putExtra("user_now", user);
                startActivity(intent1);
            }
        });

    }

    private void initTasks(){
        taskList.clear();
        for(int i=0; i<4; i++){
            Random random = new Random();
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
    }

    //TODO: 推荐算法

    private void initTaskSuggestions(){
        taskList.clear();
        String Dormarea = user.getDormArea();

        taskList= DataSupport.where("area = ?", "Dormarea")
                .where("payment >= ?" , "pay1").where(
                "payment <= ?", "pay2").where("ddl <= ?", "time").limit(4).find(task.class);

    }

}
