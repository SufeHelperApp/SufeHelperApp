package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Explore_MyTalent extends AppCompatActivity {

    private user user;

    private int flag1 = 0;
    private int flag2 = 0;
    private int flag3 = 0;
    private int flag4 = 0;
    private int flag5 = 0;
    private int flag6 = 0;
    private int flag7 = 0;
    private int flag8 = 0;
    private int flag9 = 0;
    private int flag10 = 0;
    private int flag11 = 0;
    private int flag12 = 0;
    private int flag13 = 0;
    private int flag14 = 0;
    private int flag15 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_talent);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Explore_MyTalent",myName);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Explore_MyTalent.this, Task_HomeActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Explore_MyTalent.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Explore_MyTalent.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Explore_MyTalent.this, ExploreActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button_to_see_ivt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Explore_MyTalent.this, MyActivity_recievedaward.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });


        user.updateTalentTitles(); //TODO: 数据库调用：这个方法需要调用taskRNum_e1等15个记录任务接受量的变量
        // TODO：具体见user类

        final Button mPerson1 = (Button) findViewById(R.id.person1);
        if(user.getTalentTitles().contains("占座达人")) {
            flag1 = 1;
            mPerson1.setActivated(true);
            Log.d("msg","1");
        }else{
            flag1 = 0;
            mPerson1.setActivated(false);
        }

        final Button mPerson2 = (Button) findViewById(R.id.person2);
        if(user.getTalentTitles().contains("拿快递达人")) {
            flag2 = 1;
            mPerson2.setActivated(true);
        }else{
            flag2 = 0;
            mPerson2.setActivated(false);
        }

        final Button mPerson3 = (Button) findViewById(R.id.person3);
        if(user.getTalentTitles().contains("买饭达人")) {
            flag3 = 1;
            mPerson3.setActivated(true);
        }else{
            flag3 = 0;
            mPerson3.setActivated(false);
        }

        final Button mPerson4 = (Button) findViewById(R.id.person4);
        if(user.getTalentTitles().contains("买东西达人")) {
            flag4 = 1;
            mPerson4.setActivated(true);
        }else{
            flag4 = 0;
            mPerson4.setActivated(false);
        }

        final Button mPerson5 = (Button) findViewById(R.id.person5);
        if(user.getTalentTitles().contains("拼单达人")) {
            flag5 = 1;
            mPerson5.setActivated(true);
        }else{
            flag5 = 0;
            mPerson5.setActivated(false);
        }

        final Button mPerson6 = (Button) findViewById(R.id.person6);
        if(user.getTalentTitles().contains("电子产品修理达人")) {
            flag6 = 1;
            mPerson6.setActivated(true);
        }else{
            flag6 = 0;
            mPerson6.setActivated(false);
        }

        final Button mPerson7 = (Button) findViewById(R.id.person7);
        if(user.getTalentTitles().contains("家具器件组装达人")) {
            flag7 = 1;
            mPerson7.setActivated(true);
        }else{
            flag7 = 0;
            mPerson7.setActivated(false);
        }

        final Button mPerson8 = (Button) findViewById(R.id.person8);
        if(user.getTalentTitles().contains("学习作业辅导达人")) {
            flag8 = 1;
            mPerson8.setActivated(true);
        }else{
            flag8 = 0;
            mPerson8.setActivated(false);
        }

        final Button mPerson9 = (Button) findViewById(R.id.person9);
        if(user.getTalentTitles().contains("技能培训达人")) {
            flag9 = 1;
            mPerson9.setActivated(true);
        }else{
            flag9 = 0;
            mPerson9.setActivated(false);
        }

        final Button mPerson10 = (Button) findViewById(R.id.person10);
        if(user.getTalentTitles().contains("找同好达人")) {
            flag10 = 1;
            mPerson10.setActivated(true);
        }else{
            flag10 = 0;
            mPerson10.setActivated(false);
        }

        final Button mPerson11 = (Button) findViewById(R.id.person11);
        if(user.getTalentTitles().contains("选课指南达人")) {
            flag11 = 1;
            mPerson11.setActivated(true);
        }else{
            flag11 = 0;
            mPerson11.setActivated(false);
        }

        final Button mPerson12 = (Button) findViewById(R.id.person12);
        if(user.getTalentTitles().contains("考研出国经验达人")) {
            flag12 = 1;
            mPerson12.setActivated(true);
        }else{
            flag12 = 0;
            mPerson12.setActivated(false);
        }

        final Button mPerson13 = (Button) findViewById(R.id.person13);
        if(user.getTalentTitles().contains("求职经验达人")) {
            flag13 = 1;
            mPerson13.setActivated(true);
        }else{
            flag13 = 0;
            mPerson13.setActivated(false);
        }

        final Button mPerson14 = (Button) findViewById(R.id.person14);
        if(user.getTalentTitles().contains("票务转让达人")) {
            flag14 = 1;
            mPerson14.setActivated(true);
        }else{
            flag14 = 0;
            mPerson14.setActivated(false);
        }

        final Button mPerson15 = (Button) findViewById(R.id.person15);
        if(user.getTalentTitles().contains("二手闲置达人")) {
            flag15 = 1;
            mPerson15.setActivated(true);
        }else{
            flag15 = 0;
            mPerson15.setActivated(false);
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Explore_MyTalent.this, Task_HomeActivity.class);
        intent.putExtra("user_now",user);
        startActivity(intent);
        finish();
    }


}
