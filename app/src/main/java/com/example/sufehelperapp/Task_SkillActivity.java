package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task_SkillActivity extends AppCompatActivity {

    private task[] tasks =
            {new task("文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","9:00",
                    "5","微信联系"),
                    new task("戴晓东", R.drawable.banana, "13812345678",
                            "拿快递","快递中心","18/2/10","10:00",
                            "7","微信联系"),
                    new task("刘宇涵", R.drawable.orange,"13712345678",
                            "买饭","新食堂","18/2/17","11:00",
                            "6","微信联系")};
    // NOTE: 可删除，用数据库取代

    private List<task> taskList = new ArrayList<>();

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__skill);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent1 = new Intent(Task_SkillActivity.this, ExploreActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Task_SkillActivity.this, MyActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        initTasks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_skill);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        ImageView img1 = findViewById(R.id.skill_ic1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_SkillActivity.this, Task_Selection2Activity.class);
                startActivity(intent1);
            }
        });

    }

    private void initTasks(){
        taskList.clear();
        for(int i=0; i<4; i++){
            Random random = new Random(); //TODO（算法设计）：用推荐算法取代随机算法，从数据库调用相应tasks的index
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
    }
}

