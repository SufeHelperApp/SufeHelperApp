package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Explore_WeeklyTalentActivity extends AppCompatActivity {

    private user user;

    private static List<user> userList1 = new ArrayList<>();
    private static List<user> userList2 = new ArrayList<>();
    private static List<user> userList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore__weekly_talent);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Explore_WeeklyTalent",myName);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Explore_WeeklyTalentActivity.this, MainActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Explore_WeeklyTalentActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Explore_WeeklyTalentActivity.this, MyActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });



        userList1= DataSupport.order("taskRNum_errand desc").limit(3).find(user.class);
        userList2= DataSupport.order("taskRNum_skill desc").limit(3).find(user.class);
        userList3= DataSupport.order("taskRNum_counsel desc").limit(3).find(user.class);

        android.support.v7.widget.CardView card1 = findViewById(R.id.wt1_card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList1.size()>=1) {
                    user user = userList1.get(0);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card2 = findViewById(R.id.wt1_card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList1.size()>=2) {
                    user user = userList1.get(1);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card3 = findViewById(R.id.wt1_card3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList1.size()>=3) {
                    user user = userList1.get(2);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card4 = findViewById(R.id.wt2_card1);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList2.size()>=1) {
                    user user = userList2.get(0);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card5 = findViewById(R.id.wt2_card2);
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList2.size()>=2) {
                    user user = userList2.get(1);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card6 = findViewById(R.id.wt2_card3);
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList2.size()>=3) {
                    user user = userList2.get(2);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card7 = findViewById(R.id.wt3_card1);
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList3.size()>=1) {
                    user user = userList3.get(0);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card8 = findViewById(R.id.wt3_card2);
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList3.size()>=2) {
                    user user = userList3.get(1);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });

        android.support.v7.widget.CardView card9 = findViewById(R.id.wt3_card3);
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList3.size()>=3) {
                    user user = userList3.get(2);
                    Intent intent = new Intent(Explore_WeeklyTalentActivity.this,
                            MyActivity_mytask_personalhome.class);
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, user);
                    startActivity(intent);
                }
            }
        });



        if(userList1.size()>=1) {
            //TODO：传输？？？
            TextView nameView1 = (TextView) findViewById(R.id.wt1_name1);
            TextView tasknumView1 = (TextView) findViewById(R.id.wt1_num1);

            nameView1.setText(userList1.get(0).getMyName());
            tasknumView1.setText(String.valueOf(userList1.get(0).getTaskRNum()));

        }else{
            TextView nameView1 = (TextView) findViewById(R.id.wt1_name1);
            nameView1.setText("暂无");
        }



        if(userList1.size()>=2) {
            TextView nameView2 = (TextView) findViewById(R.id.wt1_name2);
            TextView tasknumView2 = (TextView) findViewById(R.id.wt1_num2);

            nameView2.setText(userList1.get(1).getMyName());
            tasknumView2.setText(String.valueOf(userList1.get(1).getTaskRNum()));

        }else{
            TextView nameView2 = (TextView) findViewById(R.id.wt1_name2);
            nameView2.setText("暂无");
        }

        if(userList1.size()>=3) {
            TextView nameView3 = (TextView) findViewById(R.id.wt1_name3);
            TextView tasknumView3 = (TextView) findViewById(R.id.wt1_num3);

            nameView3.setText(userList1.get(2).getMyName());
            tasknumView3.setText(String.valueOf(userList1.get(2).getTaskRNum()));
        }else{
            TextView nameView3 = (TextView) findViewById(R.id.wt1_name3);
            nameView3.setText("暂无");
        }

        if(userList2.size() >= 1) {

            TextView nameView4 = (TextView) findViewById(R.id.wt2_name1);
            TextView tasknumView4 = (TextView) findViewById(R.id.wt2_num1);

            nameView4.setText(userList2.get(0).getMyName());
            tasknumView4.setText(String.valueOf(userList2.get(0).getTaskRNum()));

        }else{
            TextView nameView4 = (TextView) findViewById(R.id.wt2_name1);
            nameView4.setText("暂无");
        }

        if(userList2.size() >= 2) {

            TextView nameView5 = (TextView) findViewById(R.id.wt2_name2);
            TextView tasknumView5 = (TextView) findViewById(R.id.wt2_num2);

            nameView5.setText(userList2.get(1).getMyName());
            tasknumView5.setText(String.valueOf(userList2.get(1).getTaskRNum()));

        }else{
            TextView nameView5 = (TextView) findViewById(R.id.wt2_name2);
            nameView5.setText("暂无");
        }

        if(userList2.size() >= 3) {

            TextView nameView6 = (TextView) findViewById(R.id.wt2_name3);
            TextView tasknumView6 = (TextView) findViewById(R.id.wt2_num3);

            nameView6.setText(userList2.get(2).getMyName());
            tasknumView6.setText(String.valueOf(userList2.get(2).getTaskRNum()));

        }else{
            TextView nameView6 = (TextView) findViewById(R.id.wt2_name3);
            nameView6.setText("暂无");
        }

        if(userList3.size() >= 1) {

            TextView nameView7 = (TextView) findViewById(R.id.wt3_name1);
            TextView tasknumView7 = (TextView) findViewById(R.id.wt3_num1);

            nameView7.setText(userList3.get(0).getMyName());
            tasknumView7.setText(String.valueOf(userList3.get(0).getTaskRNum()));

        }else{
            TextView nameView7 = (TextView) findViewById(R.id.wt3_name1);
            nameView7.setText("暂无");
        }

        if(userList3.size() >= 2) {

            TextView nameView8 = (TextView) findViewById(R.id.wt3_name2);
            TextView tasknumView8 = (TextView) findViewById(R.id.wt3_num2);

            nameView8.setText(userList3.get(1).getMyName());
            tasknumView8.setText(String.valueOf(userList3.get(1).getTaskRNum()));

        }else{
            TextView nameView8 = (TextView) findViewById(R.id.wt3_name2);
            nameView8.setText("暂无");
        }

        if(userList3.size() >= 3) {

            TextView nameView9 = (TextView) findViewById(R.id.wt3_name3);
            TextView tasknumView9 = (TextView) findViewById(R.id.wt3_num3);

            nameView9.setText(userList3.get(2).getMyName());
            tasknumView9.setText(String.valueOf(userList3.get(2).getTaskRNum()));

        }else{
            TextView nameView9 = (TextView) findViewById(R.id.wt3_name3);
            nameView9.setText("暂无");
        }

    }

}
