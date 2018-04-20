package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_credit extends AppCompatActivity {

    private user user;

    private String myPhone;

    Connection con;
    ResultSet rs;

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

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
                        Intent intent1 = new Intent(MyActivity_credit.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(MyActivity_credit.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(MyActivity_credit.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });



        //检查所有任务当前是否违约
        StatusUtils.updateAllTaskStatus();

        //TODO: 调用所有帮助者为当前用户的任务，ifDefault = true，放入taskList

        //显示违约任务卡片

        List<task> taskList = new ArrayList<>();

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st1 = con.createStatement();
            rs= st1.executeQuery("SELECT * FROM `task` WHERE `helperName` = '"+user.getMyName()+"' AND `ifDefault` = '1'");
            List list = DbUtils.populate(rs,task.class);

            for(int i = 0; i<list.size(); i++){
                taskList.add((task)list.get(i));
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_credit_recycler);
            GridLayoutManager layoutManager = new GridLayoutManager(this,1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TaskAdapter(taskList,user,4);
            recyclerView.setAdapter(adapter);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_credit.this, My_HomeActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });


        //从数据库显示rating

        RatingBar ratingBar = findViewById(R.id.my_credit_rating_bar);
        //TODO: 获取当前user的averageScore
        float score = user.getAverageScore();
        //违约任务扣分
        if((score - taskList.size())>= 0.5){
            score = score - taskList.size();
        }
        ratingBar.setRating(score);

        TextView ratingTextView = findViewById(R.id.my_credit_average_score_text);
        ratingTextView.setText(String.valueOf(score));






    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_credit.this, My_HomeActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }


}
