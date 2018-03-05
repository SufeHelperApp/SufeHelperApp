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

        user.updateTalentTitles();

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

        final Button mPerson1 = (Button) findViewById(R.id.person1);
        if(user.getTalentTitles().contains("占座达人")) {
            flag1 = 1;
            mPerson1.setActivated(true);
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

    }


}
