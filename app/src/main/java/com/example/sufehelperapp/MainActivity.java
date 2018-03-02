package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MainActivity:",user.getMyName());

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(MainActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(MainActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


        Button db_test = (Button) findViewById(R.id.button_db_test);
        db_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DBTESTActivity.class);
                startActivity(intent);
            }
        });

        Button b1 = (Button) findViewById(R.id.btn_errand);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,Selection.class);
                intent1.putExtra("user_now", user);
                startActivity(intent1);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn_skill);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Task_SkillActivity.class);
                intent2.putExtra("user_now", user);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_counsel);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, Task_CounselActivity.class);
                intent3.putExtra("user_now", user);
                startActivity(intent3);
            }
        });
        Button b4 = (Button) findViewById(R.id.launch_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, Task_LaunchActivity.class);
                intent4.putExtra("user_now", user);
                startActivity(intent4);


            }
        });

    }



}
