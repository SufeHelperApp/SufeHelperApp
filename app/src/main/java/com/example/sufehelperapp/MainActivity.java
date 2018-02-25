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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        Intent intent2 = new Intent(MainActivity.this, ExploreActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(MainActivity.this, My_HomeActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        //接受user
        user user = (user) getIntent().getSerializableExtra("user_data");
        String myName = user.getMyName();


        Button db_test = (Button) findViewById(R.id.button_db_test);
        db_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, DBTESTActivity.class);
                startActivity(intent1);
            }
        });

        Button b1 = (Button) findViewById(R.id.btn_errand);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, Task_ErrandActivity.class);
                startActivity(intent1);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn_skill);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Task_SkillActivity.class);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_counsel);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, Task_CounselActivity.class);
                startActivity(intent3);
            }
        });
        Button b4 = (Button) findViewById(R.id.launch_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, Task_LaunchActivity.class);
                startActivity(intent4);
            }
        });



    }


}
