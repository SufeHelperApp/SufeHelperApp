package com.example.sufehelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static org.litepal.LitePalApplication.getContext;

public class MyActivity_Setup_Edit extends AppCompatActivity {

    //user user = new user(); //TODO: 用当前用户代替
    //接受user
    user user = (user) getIntent().getSerializableExtra("user_data");
    String myName = user.getMyName();
    ImageButton imgbtn1;
    ImageButton imgbtn2;
    ImageButton imgbtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setup_edit);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //TODO: DB：1.数据库中调用头像
        //TODO: UI: 2.姓名删除，昵称改成用户名 3.button位置

        user.setMyImageId(R.drawable.apple);
        ImageView image = (ImageView) findViewById(R.id.picture_upload);
        Glide.with(getContext()).load(user.getMyImageId()).into(image);

        user.setSex("女");
        TextView sexView = (TextView) findViewById(R.id.sex_text);
        sexView.setText(user.getSex());

        user.setMyName("戴晓东");
        TextView nicknameView = (TextView) findViewById(R.id.nickname_text11);
        nicknameView.setText(user.getMyName());

        user.setDormitoryLocation("第八宿舍");
        TextView dormitoryView = (TextView) findViewById(R.id.nickname_text22);
        dormitoryView.setText(user.getDormitoryLocation());

        user.setPhonenumber("13612341234");
        TextView phoneView = (TextView) findViewById(R.id.nickname_text33);
        phoneView.setText(user.getPhonenumber());

        imgbtn1=(ImageButton)findViewById(R.id.button_edit_nickname);
        imgbtn2=(ImageButton)findViewById(R.id.button_edit_password);
        imgbtn3=(ImageButton)findViewById(R.id.button_edit_phonenumber);

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Setup.class);
                startActivity(intent);
            }
        });

        imgbtn1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_NickName.class);
                startActivity(intent);
            }

        });
        imgbtn2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_password.class);
                startActivity(intent);
            }

        });
        imgbtn3.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup_Edit.this, MyActivity_Edit_phonenumber.class);
                startActivity(intent);
            }

        });
    }
}
