package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class My_LoginSecondActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);

        dbHelper = new MyDatabaseHelper(this,"USER.db",null,1);
        LitePal.getDatabase();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button buttonback = (Button) findViewById(R.id.title_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_LoginSecondActivity.this, My_LoginFirstActivity.class);
                startActivity(intent1);
            }
        });
        Button button1 = (Button) findViewById(R.id.button_3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(My_LoginSecondActivity.this, My_LoginThirdActivity.class);
                startActivity(intent2);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView nameView = findViewById(R.id.login_second_name);
                TextView passwordView = findViewById(R.id.login_second_password);
                String name = nameView.getText().toString();
                String password = passwordView.getText().toString();

                List<user> userList = DataSupport.where("myName = ? and password = ?",
                        name,password).find(user.class);

                if(userList.isEmpty()){
                    Toast.makeText(My_LoginSecondActivity.this, "用户名或密码错误！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    user user = userList.get(0);
                    String myName = user.getMyName();
                    String txt = "欢迎回来, "+user.getMyName()+"!";

                    Intent intent = new Intent(My_LoginSecondActivity.this, Task_HomeActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                    Toast.makeText(My_LoginSecondActivity.this,txt,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
