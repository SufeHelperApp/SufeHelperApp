package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explore_weekly extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private user user;

    //TODO: 插入图表
    private Explore_weekly_interal[] explore_weekly_interals = {new Explore_weekly_interal("发布量",R.drawable.ic_tiao),
            new Explore_weekly_interal("完成量",R.drawable.ic_tiao),
            new Explore_weekly_interal("发布时间/完成时间",R.drawable.ic_tiao),
            new Explore_weekly_interal("任务报酬",R.drawable.ic_tiao)};

    private List<Explore_weekly_interal> interalList = new ArrayList<>();
    private Explore_weekly_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_weekly);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Explore_weekly.this, MainActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent = new Intent(Explore_weekly.this, ExploreActivity.class);
                        intent.putExtra("user_now", user);
                        startActivity(intent);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Explore_weekly.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Explore_weekly",myName);

        initInterals();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Explore_weekly_adapter(interalList);
        recyclerView.setAdapter(adapter);
    }

    private void initInterals() {
        interalList.clear();
        for(int i=0;i<4;i++) {
            interalList.add(explore_weekly_interals[i]);
        }
    }
}
