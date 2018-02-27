package com.example.sufehelperapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class My_RegisterFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterFirstActivity.this, My_LoginFirstActivity.class);
                startActivity(intent1);
            }
        });

        // 点击“确认注册”按钮，新建用户对象，设置手机号

        Button button2 = (Button) findViewById(R.id.button_7);
        final TextView phoneView = findViewById(R.id.edit_text3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneView.getText().toString();
                if(phone.isEmpty())
                {
                    Toast.makeText(My_RegisterFirstActivity.this, "手机号不得为空！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    List<user> users = DataSupport.where("phonenumber = ?", phone).find(user.class);
                    if (!users.isEmpty()) {
                        Toast.makeText(My_RegisterFirstActivity.this, "手机号已经存在！",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        user user = new user(); //TODO: 用当前用户代替
                        user.setPhonenumber(phone);
                        user.save();
                        Intent intent2 = new Intent(My_RegisterFirstActivity.this, My_RegisterSecondActivity.class);
                        startActivity(intent2);
                    }
                }
            }
        });

    }
}

