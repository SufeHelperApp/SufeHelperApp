package com.example.sufehelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static org.litepal.LitePalApplication.getContext;

public class MyActivity_Setup_Edit extends AppCompatActivity {

    private user user;

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

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MyActivity_Setup_Edit",user.getMyName());

        //TODO: DB：1.数据库中调用头像

        ImageView image = (ImageView) findViewById(R.id.picture_upload);
        Glide.with(getContext()).load(user.getMyImageId()).into(image);

        TextView sexView = (TextView) findViewById(R.id.sex_text);
        sexView.setText(user.getSex());

        TextView nicknameView = (TextView) findViewById(R.id.nickname_text11);
        nicknameView.setText(user.getMyName());

        TextView dormitoryView = (TextView) findViewById(R.id.dormname_text22);
        dormitoryView.setText(user.getDormitoryLocation());

        TextView phoneView = (TextView) findViewById(R.id.nickname_text33);
        phoneView.setText(user.getPhonenumber());

        /*
        TextView intentionView = (TextView) findViewById(R.id.nickname_text44);
        intentionView.setText(user.getDemand().get(0));*/

        imgbtn1=(ImageButton)findViewById(R.id.button_edit_nickname);
        imgbtn2=(ImageButton)findViewById(R.id.button_edit_password);
        imgbtn3=(ImageButton)findViewById(R.id.button_edit_phonenumber);
        imgbtn4=(ImageButton)findViewById(R.id.button_edit_address);
        imgbtn5=(ImageButton)findViewById(R.id.button_edit_personal_intention);

        //TODO:delete
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Setup.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        imgbtn1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_NickName.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }

        });
        imgbtn2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_dormname.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }

        });
        imgbtn3.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_phonenumber.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }

        });
        imgbtn4.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, My_RegisterForthActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }

        });
        imgbtn5.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_password.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }

        });
    }
}
