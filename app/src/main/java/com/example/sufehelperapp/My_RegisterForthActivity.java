package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class My_RegisterForthActivity extends AppCompatActivity {

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

    List<String> demand = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register_forth);

        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("RegisterForthActivity",user.getMyName());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterForthActivity.this, My_RegisterThirdActivity.class);
                startActivity(intent1);
            }
        });


        //点击确认按钮后
        Button button2 = (Button) findViewById(R.id.button_confirm4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(demand.size()==0){
                    Toast.makeText(My_RegisterForthActivity.this, "请至少选择一个常见需求！",
                            Toast.LENGTH_SHORT).show();
                }else {

                    user.setDemand(demand);

                    user.updateAll("phonenumber = ?",user.getPhonenumber());
                    for (String d : user.getDemand()) {
                        Log.d("demand", d);
                    }

                    Intent intent = new Intent(My_RegisterForthActivity.this, My_RegisterFifthActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                }
            }
        });

        //按钮响应事件
        final Button mPerson1 = (Button) findViewById(R.id.person1);
        mPerson1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag1) {
                    case 0:
                        mPerson1.setActivated(true);
                        flag1 = 1;
                        demand.add("占座");
                        break;
                    case 1:
                        mPerson1.setActivated(false);
                        flag1 = 0;
                        demand.remove("占座");
                        break;
                }
            }
        });
        final Button mPerson2 = (Button) findViewById(R.id.person2);
        mPerson2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag2) {
                    case 0:
                        mPerson2.setActivated(true);
                        flag2 = 1;
                        demand.add("拿快递");
                        break;
                    case 1:
                        mPerson2.setActivated(false);
                        flag2 = 0;
                        demand.remove("拿快递");
                        break;
                }
            }
        });
        final Button mPerson3 = (Button) findViewById(R.id.person3);
        mPerson3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag3) {
                    case 0:
                        mPerson3.setActivated(true);
                        flag3 = 1;
                        demand.add("买饭");
                        break;
                    case 1:
                        mPerson3.setActivated(false);
                        flag3 = 0;
                        demand.remove("买饭");
                        break;
                }
            }
        });
        final Button mPerson4 = (Button) findViewById(R.id.person4);
        mPerson4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag4) {
                    case 0:
                        mPerson4.setActivated(true);
                        flag4 = 1;
                        demand.add("买东西");
                        break;
                    case 1:
                        mPerson4.setActivated(false);
                        flag4 = 0;
                        demand.remove("买东西");
                        break;
                }
            }
        });
        final Button mPerson5 = (Button) findViewById(R.id.person5);
        mPerson5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag5) {
                    case 0:
                        mPerson5.setActivated(true);
                        flag5 = 1;
                        demand.add("拼单");
                        break;
                    case 1:
                        mPerson5.setActivated(false);
                        flag5 = 0;
                        demand.remove("拼单");
                        break;
                }
            }
        });
        final Button mPerson6 = (Button) findViewById(R.id.person6);
        mPerson6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag6) {
                    case 0:
                        mPerson6.setActivated(true);
                        flag6 = 1;
                        demand.add("电子产品修理");
                        break;
                    case 1:
                        mPerson6.setActivated(false);
                        flag6 = 0;
                        demand.remove("电子产品修理");
                        break;
                }
            }
        });
        final Button mPerson7 = (Button) findViewById(R.id.person7);
        mPerson7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag7) {
                    case 0:
                        mPerson7.setActivated(true);
                        flag7 = 1;
                        demand.add("家具器件组装");
                        break;
                    case 1:
                        mPerson7.setActivated(false);
                        flag7 = 0;
                        demand.remove("家具器件组装");
                        break;
                }
            }
        });
        final Button mPerson8 = (Button) findViewById(R.id.person8);
        mPerson8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag8) {
                    case 0:
                        mPerson8.setActivated(true);
                        flag8 = 1;
                        demand.add("找同好");
                        break;
                    case 1:
                        mPerson8.setActivated(false);
                        flag8 = 0;
                        demand.remove("找同好");
                        break;
                }
            }
        });
        final Button mPerson9 = (Button) findViewById(R.id.person9);
        mPerson9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag9) {
                    case 0:
                        mPerson9.setActivated(true);
                        flag9 = 1;
                        demand.add("学习作业辅导");
                        break;
                    case 1:
                        mPerson9.setActivated(false);
                        flag9 = 0;
                        demand.remove("学习作业辅导");
                        break;
                }
            }
        });
        final Button mPerson10 = (Button) findViewById(R.id.person10);
        mPerson10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag10) {
                    case 0:
                        mPerson10.setActivated(true);
                        flag10 = 1;
                        demand.add("技能培训");
                        break;
                    case 1:
                        mPerson10.setActivated(false);
                        flag10 = 0;
                        demand.remove("技能培训");
                        break;
                }
            }
        });
        final Button mPerson11 = (Button) findViewById(R.id.person11);
        mPerson11.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag11) {
                    case 0:
                        mPerson11.setActivated(true);
                        flag11 = 1;
                        demand.add("选课指南");
                        break;
                    case 1:
                        mPerson11.setActivated(false);
                        flag11 = 0;
                        demand.remove("选课指南");
                        break;
                }
            }
        });
        final Button mPerson12 = (Button) findViewById(R.id.person12);
        mPerson12.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag12) {
                    case 0:
                        mPerson12.setActivated(true);
                        flag12 = 1;
                        demand.add("考研出国经验");
                        break;
                    case 1:
                        mPerson12.setActivated(false);
                        flag12 = 0;
                        demand.remove("考研出国经验");
                        break;
                }
            }
        });
        final Button mPerson13 = (Button) findViewById(R.id.person13);
        mPerson13.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag13) {
                    case 0:
                        mPerson13.setActivated(true);
                        flag13 = 1;
                        demand.add("求职经验");
                        break;
                    case 1:
                        mPerson13.setActivated(false);
                        flag13 = 0;
                        demand.remove("求职经验");
                        break;
                }
            }
        });
        final Button mPerson14 = (Button) findViewById(R.id.person14);
        mPerson14.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag14) {
                    case 0:
                        mPerson14.setActivated(true);
                        flag14 = 1;
                        demand.add("票务转让");
                        break;
                    case 1:
                        mPerson14.setActivated(false);
                        flag14 = 0;
                        demand.remove("票务转让");
                        break;
                }
            }
        });
        final Button mPerson15 = (Button) findViewById(R.id.person15);
        mPerson15.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag15) {
                    case 0:
                        mPerson15.setActivated(true);
                        flag15 = 1;
                        demand.add("二手闲置");
                        break;
                    case 1:
                        mPerson15.setActivated(false);
                        flag15 = 0;
                        demand.remove("二手闲置");
                        break;
                }
            }
        });

    }
}
