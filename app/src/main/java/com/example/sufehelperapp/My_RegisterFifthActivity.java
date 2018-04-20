package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class My_RegisterFifthActivity extends AppCompatActivity {

    private String myPhone;
    private user user;
    Connection con;
    ResultSet rs;

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

    List<String> specialty = new ArrayList<String>();
    String specialtyString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register_fifth);

        //user
        myPhone = getIntent().getStringExtra("user_phone");
        Log.d("RegisterSecondActivity",myPhone);

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


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterFifthActivity.this, My_RegisterForthActivity.class);
                startActivity(intent1);
            }
        });

        Button button2 = (Button) findViewById(R.id.button_confirm5);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(specialty.size()==0){
                    Toast.makeText(My_RegisterFifthActivity.this, "请至少选择一个个人特长！",
                            Toast.LENGTH_SHORT).show();
                }else {

                    List<String> i = new ArrayList<>();

                    for(int k=0;k<15;k++){
                        i.add("0");
                    }

                    for(String s:specialty){
                        if(s.equals("占座")){
                            i.set(0,"1");
                            continue;
                        }
                        if(s.equals("拿快递")){
                            i.set(1,"1");
                            continue;
                        }
                        if(s.equals("买东西")){
                            i.set(2,"1");
                            continue;
                        }
                        if(s.equals("买饭")){
                            i.set(3,"1");
                            continue;
                        }
                        if(s.equals("拼单")){
                            i.set(4,"1");
                            continue;
                        }
                        if(s.equals("电子产品修理")){
                            i.set(5,"1");
                            continue;
                        }
                        if(s.equals("家具器件组装")){
                            i.set(6,"1");
                            continue;
                        }
                        if(s.equals("学习作业辅导")){
                            i.set(7,"1");
                            continue;
                        }
                        if(s.equals("技能培训")){
                            i.set(8,"1");
                            continue;
                        }
                        if(s.equals("找同好")){
                            i.set(9,"1");
                            continue;
                        }
                        if(s.equals("周边服务")){
                            i.set(10,"1");
                            continue;
                        }
                        if(s.equals("考研出国经验")){
                            i.set(11,"1");
                            continue;
                        }
                        if(s.equals("求职经验")){
                            i.set(12,"1");
                            continue;
                        }
                        if(s.equals("票务转让")){
                            i.set(13,"1");
                            continue;
                        }
                        if(s.equals("二手闲置")){
                            i.set(14,"1");
                        }
                    }

                    for(int k=0; k<15; k++){
                        specialtyString = specialtyString + i.get(k);
                    }

                    try {
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();
                        Statement st2 = con.createStatement();
                        st2.executeUpdate("UPDATE `user` SET `specialtyString` = '" + specialtyString + "' WHERE `phonenumber` = '" + myPhone + "'");

                        con.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String txt = "注册成功, " + user.getMyName() + "!";

                    Intent intent1 = new Intent(My_RegisterFifthActivity.this, Map_for_task.class);

                    intent1.putExtra("user_phone", myPhone);
                    startActivity(intent1);
                    Toast.makeText(My_RegisterFifthActivity.this, txt,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        final Button mPerson1 = (Button) findViewById(R.id.person19);
        mPerson1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag1) {
                    case 0:
                        mPerson1.setActivated(true);
                        flag1 = 1;
                        specialty.add("占座");
                        break;
                    case 1:
                        mPerson1.setActivated(false);
                        flag1 = 0;
                        specialty.remove("占座");
                        break;
                }
            }
        });
        final Button mPerson2 = (Button) findViewById(R.id.person29);
        mPerson2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag2) {
                    case 0:
                        mPerson2.setActivated(true);
                        flag2 = 1;
                        specialty.add("拿快递");
                        break;
                    case 1:
                        mPerson2.setActivated(false);
                        flag2 = 0;
                        specialty.remove("拿快递");
                        break;
                }
            }
        });
        final Button mPerson3 = (Button) findViewById(R.id.person39);
        mPerson3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag3) {
                    case 0:
                        mPerson3.setActivated(true);
                        flag3 = 1;
                        specialty.add("买饭");
                        break;
                    case 1:
                        mPerson3.setActivated(false);
                        flag3 = 0;
                        specialty.remove("买饭");
                        break;
                }
            }
        });
        final Button mPerson4 = (Button) findViewById(R.id.person49);
        mPerson4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag4) {
                    case 0:
                        mPerson4.setActivated(true);
                        flag4 = 1;
                        specialty.add("买东西");
                        break;
                    case 1:
                        mPerson4.setActivated(false);
                        flag4 = 0;
                        specialty.remove("买东西");
                        break;
                }
            }
        });
        final Button mPerson5 = (Button) findViewById(R.id.person59);
        mPerson5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag5) {
                    case 0:
                        mPerson5.setActivated(true);
                        flag5 = 1;
                        specialty.add("拼单");
                        break;
                    case 1:
                        mPerson5.setActivated(false);
                        flag5 = 0;
                        specialty.remove("拼单");
                        break;
                }
            }
        });
        final Button mPerson6 = (Button) findViewById(R.id.person69);
        mPerson6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag6) {
                    case 0:
                        mPerson6.setActivated(true);
                        flag6 = 1;
                        specialty.add("电子产品修理");
                        break;
                    case 1:
                        mPerson6.setActivated(false);
                        flag6 = 0;
                        specialty.remove("电子产品修理");
                        break;
                }
            }
        });
        final Button mPerson7 = (Button) findViewById(R.id.person79);
        mPerson7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag7) {
                    case 0:
                        mPerson7.setActivated(true);
                        flag7 = 1;
                        specialty.add("家具器件组装");
                        break;
                    case 1:
                        mPerson7.setActivated(false);
                        flag7 = 0;
                        specialty.remove("家具器件组装");
                        break;
                }
            }
        });
        final Button mPerson8 = (Button) findViewById(R.id.person89);
        mPerson8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag8) {
                    case 0:
                        mPerson8.setActivated(true);
                        flag8 = 1;
                        specialty.add("找同好");
                        break;
                    case 1:
                        mPerson8.setActivated(false);
                        flag8 = 0;
                        specialty.remove("找同好");
                        break;
                }
            }
        });
        final Button mPerson9 = (Button) findViewById(R.id.person99);
        mPerson9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag9) {
                    case 0:
                        mPerson9.setActivated(true);
                        flag9 = 1;
                        specialty.add("学习作业辅导");
                        break;
                    case 1:
                        mPerson9.setActivated(false);
                        flag9 = 0;
                        specialty.remove("学习作业辅导");
                        break;
                }
            }
        });
        final Button mPerson10 = (Button) findViewById(R.id.person109);
        mPerson10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag10) {
                    case 0:
                        mPerson10.setActivated(true);
                        flag10 = 1;
                        specialty.add("技能培训");
                        break;
                    case 1:
                        mPerson10.setActivated(false);
                        flag10 = 0;
                        specialty.remove("技能培训");
                        break;
                }
            }
        });
        final Button mPerson11 = (Button) findViewById(R.id.person119);
        mPerson11.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag11) {
                    case 0:
                        mPerson11.setActivated(true);
                        flag11 = 1;
                        specialty.add("周边服务");
                        break;
                    case 1:
                        mPerson11.setActivated(false);
                        flag11 = 0;
                        specialty.remove("周边服务");
                        break;
                }
            }
        });
        final Button mPerson12 = (Button) findViewById(R.id.person129);
        mPerson12.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag12) {
                    case 0:
                        mPerson12.setActivated(true);
                        flag12 = 1;
                        specialty.add("考研出国经验");
                        break;
                    case 1:
                        mPerson12.setActivated(false);
                        flag12 = 0;
                        specialty.remove("考研出国经验");
                        break;
                }
            }
        });
        final Button mPerson13 = (Button) findViewById(R.id.person139);
        mPerson13.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag13) {
                    case 0:
                        mPerson13.setActivated(true);
                        flag13 = 1;
                        specialty.add("求职经验");
                        break;
                    case 1:
                        mPerson13.setActivated(false);
                        flag13 = 0;
                        specialty.remove("求职经验");
                        break;
                }
            }
        });
        final Button mPerson14 = (Button) findViewById(R.id.person149);
        mPerson14.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag14) {
                    case 0:
                        mPerson14.setActivated(true);
                        flag14 = 1;
                        specialty.add("票务转让");
                        break;
                    case 1:
                        mPerson14.setActivated(false);
                        flag14 = 0;
                        specialty.remove("票务转让");
                        break;
                }
            }
        });
        final Button mPerson15 = (Button) findViewById(R.id.person159);
        mPerson15.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag15) {
                    case 0:
                        mPerson15.setActivated(true);
                        flag15 = 1;
                        specialty.add("二手闲置");
                        break;
                    case 1:
                        mPerson15.setActivated(false);
                        flag15 = 0;
                        specialty.remove("二手闲置");
                        break;
                }
            }
        });
    }
}
