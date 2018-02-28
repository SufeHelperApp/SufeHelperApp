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
import android.widget.RatingBar;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MyActivity_credit extends AppCompatActivity {

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

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MyActivity_credit",user.getMyName());
/*
        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        Intent intent1 = new Intent(MyActivity_credit.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(MyActivity_credit.this, ExploreActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(MyActivity_credit.this, MyActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        }); */


        //从数据库显示rating

        RatingBar ratingBar = findViewById(R.id.my_credit_rating_bar);
        ratingBar.setRating(user.getAverageScore());

        TextView ratingTextView = findViewById(R.id.my_credit_average_score_text);
        ratingTextView.setText(String.valueOf(user.getAverageScore()));


        //显示违约任务卡片


        List<task> taskList = DataSupport.where("helperName = ?",user.getMyName())
                .where("ifDefault = ? ","1").find(task.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_credit_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        //TODO:删除
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_credit.this, My_HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
