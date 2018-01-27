package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(ExploreActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(ExploreActivity.this, MyActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        Button b1 = (Button) findViewById(R.id.btn_weekly_data);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ExploreActivity.this, Explore_WeeklyDataActivity.class);
                startActivity(intent1);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn_weekly_talent);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ExploreActivity.this, Explore_WeeklyTalentActivity.class);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_my_talent);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ExploreActivity.this, Explore_MyTalentActivity.class);
                startActivity(intent3);
            }
        });
    }
}