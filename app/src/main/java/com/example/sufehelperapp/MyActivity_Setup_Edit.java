package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MyActivity_Setup_Edit extends AppCompatActivity {

    private user user;
    private String myPhone;

    Connection con;
    ResultSet rs;

    ImageButton imgbtn1;
    ImageButton imgbtn2;
    ImageButton imgbtn3;
    ImageButton imgbtn4;
    ImageButton imgbtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setup_edit);
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
                        Intent intent1 = new Intent(MyActivity_Setup_Edit.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(MyActivity_Setup_Edit.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(MyActivity_Setup_Edit.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });





        ImageView image = (ImageView) findViewById(R.id.picture_upload);
        Glide.with(getContext()).load(user.getMyImageId()).into(image);

        TextView sexView = (TextView) findViewById(R.id.sex_text);
        //TODO:调用当前用户的sex
        sexView.setText(user.getSex());

        TextView nicknameView = (TextView) findViewById(R.id.nickname_text11);
        //TODO:调用当前用户的myName
        nicknameView.setText(user.getMyName());

        /*

        TextView dormitoryView = (TextView) findViewById(R.id.dormname_text22);
        //TODO:调用当前用户的dormitoryLocation
        dormitoryView.setText(user.getDormitoryLocation());*/

        TextView phoneView = (TextView) findViewById(R.id.nickname_text33);
        //TODO:调用当前用户的phonenumber
        phoneView.setText(user.getPhonenumber());

        TextView intentionView = (TextView) findViewById(R.id.nickname_text44);
        //TODO:调用当前用户的demand(数组)
        //intentionView.setText(user.getDemand().get(0));


        imgbtn1=(ImageButton)findViewById(R.id.button_edit_nickname);
        imgbtn2=(ImageButton)findViewById(R.id.button_edit_address);
        imgbtn3=(ImageButton)findViewById(R.id.button_edit_phonenumber);
        imgbtn4=(ImageButton)findViewById(R.id.button_edit_personal_intention);
        imgbtn5=(ImageButton)findViewById(R.id.button_edit_password);

        //返回
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Setup.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });

        imgbtn1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_NickName.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }

        });
        /*
        imgbtn2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_dormname.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }

        });*/
        imgbtn3.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_phonenumber.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }

        });
        imgbtn4.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, My_RegisterForthActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }

        });
        imgbtn5.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_password.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Setup.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }
}
