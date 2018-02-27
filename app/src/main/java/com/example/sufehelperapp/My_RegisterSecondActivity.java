package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;



public class My_RegisterSecondActivity extends AppCompatActivity {

    private RadioGroup rg;
    private RadioButton rb_Male;
    private RadioButton rb_Female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterSecondActivity.this, My_RegisterFirstActivity.class);
                startActivity(intent1);
            }

        });

        Button button2 = (Button) findViewById(R.id.button_8);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将填写的用户名和密码存入数据库

                TextView nameView = findViewById(R.id.register2_name);
                TextView passwordView = findViewById(R.id.register2_password);
                String name = nameView.getText().toString();
                String password = passwordView.getText().toString();

                List<user> userList1 = DataSupport.where("myName = ?",name).find(user.class);
                if(!userList1.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "用户名已经存在！", Toast.LENGTH_SHORT).show();
                }else {

                    List<user> userList = DataSupport.where("phonenumber = ?", "13712341234").find(user.class);
                    user user = userList.get(0); //TODO: 用当前用户代替

                    user.setMyName(name);
                    user.setPassword(password);
                    user.setMyImageId(R.drawable.apple);
                    user.save();

                    Intent intent1 = new Intent(My_RegisterSecondActivity.this, My_RegisterThirdActivity.class);
                    startActivity(intent1);

                }
            }
        });

        rg = (RadioGroup) findViewById(R.id.rg_sex);
        rb_Male = (RadioButton) findViewById(R.id.rb_Male);
        rb_Female = (RadioButton) findViewById(R.id.rb_FeMale);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });



    }
}
