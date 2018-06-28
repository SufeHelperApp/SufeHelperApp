package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_mytask_personalhome extends AppCompatActivity {

    public static final String USER_SELECTED = "user_selected";
    private user user_selected; //2
    private user user; //1
    private task task; //3
    private int num; //4

    private String myPhone;
    Connection con;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask_personalhome);
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

        //TODO: 调出从taskLaunchActivity中选中的user_selected
        user_selected = (user) getIntent().getSerializableExtra("user_selected");
        Log.d("name",user_selected.getMyName());

        task = (task) getIntent().getSerializableExtra("task_selected");

        num = getIntent().getIntExtra("num",1);


        ImageView launcher_image = (ImageView) findViewById(R.id.picture_upload);
        TextView launcher_name = (TextView) findViewById(R.id.his_nickname_text11);
        TextView launcher_sex = (TextView) findViewById(R.id.sex_text);
        //TextView launcher_dormName = (TextView) findViewById(R.id.his_address_text22);
        TextView launcher_phonenumber = (TextView) findViewById(R.id.his_phonenumber_text33);
        TextView specialty = (TextView) findViewById(R.id.his_personalintention_text44);
        TextView averagescore = (TextView) findViewById(R.id.his_integral_text11);

        //TODO: 调出user_selected的sex,myName,dormitoryLocation,phonenumber,averageScore,speciality(数组)
        Glide.with(this).load(user_selected.getMyImageId()).into(launcher_image);
        launcher_sex.setText(user_selected.getSex());
        launcher_name.setText(user_selected.getMyName());
        //launcher_dormName.setText(user_selected.getDormitoryLocation());
        launcher_phonenumber.setText(user_selected.getPhonenumber());
        averagescore.setText(String.valueOf(user_selected.getAverageScore()));
        /*
        if(user.getSpecialty().size()!=0) {
            specialty.setText(user.getSpecialty().get(0));
        }*/
        String[] specialties = {"占座", "拿快递", "买饭", "买东西", "拼单", "电子产品修理", "家具器件组装",
                "学习作业辅导", "技能培训", "找同好", "周边服务", "考研出国经验", "求职经验", "票务转让", "二手闲置"};

        String str = user.getSpecialtyString();
        for(int i = 0; i < 15; i++){
            if(str.charAt(i)=='1'){
                specialty.setText(specialties[i]);
                break;
            }
        }


    }

    //未添加back返回值

    @Override
    public void onBackPressed(){
        Intent intent1 = new Intent(MyActivity_mytask_personalhome.this, Task_InfoActivity.class);
        Intent intent2 = new Intent(MyActivity_mytask_personalhome.this, Explore_WeeklyTalentActivity.class);
        if(num == 1) {
            intent1.putExtra("user_phone", myPhone);
            intent1.putExtra("task_selected", task);
            intent1.putExtra("num", 2);
            startActivity(intent1);
        }else if(num ==2){
            intent2.putExtra("user_phone", myPhone);
            startActivity(intent2);
        }
        finish();
    }



}

