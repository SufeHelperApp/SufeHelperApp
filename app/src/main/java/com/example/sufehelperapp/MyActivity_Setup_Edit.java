package com.example.sufehelperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyActivity_Setup_Edit extends AppCompatActivity {

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
