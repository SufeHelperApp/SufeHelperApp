package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private user user;
    private String myPhone;

    Connection con;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button b1 = (Button) findViewById(R.id.btn_weekly_data);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ExploreActivity.this, Explore_weekly.class);
                intent1.putExtra("user_phone", myPhone);
                startActivity(intent1);
            }
        });
        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(ExploreActivity.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(ExploreActivity.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        Button b2 = (Button) findViewById(R.id.btn_weekly_talent);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ExploreActivity.this, Explore_WeeklyTalentActivity.class);
                intent2.putExtra("user_phone", myPhone);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_my_talent);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ExploreActivity.this, Explore_MyTalent.class);
                intent3.putExtra("user_phone", myPhone);
                startActivity(intent3);
            }
        });
    }

    @Override
    public void onBackPressed(){

    }

}
