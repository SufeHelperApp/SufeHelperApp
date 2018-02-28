package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String sex;
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

        //从My_RegisterFirstActivity获取user
        final user user = (user) getIntent().getSerializableExtra("user_now");
        String myPhone = user.getPhonenumber();
        Log.d("RegisterSecondActivity",myPhone);

        //点击“确认”按钮
        Button button2 = (Button) findViewById(R.id.button_8);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取用户名和密码的组件对象
                TextView nameView = findViewById(R.id.register2_name);
                TextView passwordView = findViewById(R.id.register2_password);

                //检查用户名和密码不得为空
                String name = nameView.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "用户名不得为空！", Toast.LENGTH_SHORT).show();
                }

                String password = passwordView.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "密码不得为空！", Toast.LENGTH_SHORT).show();
                }

                if(sex.isEmpty()){
                    Toast.makeText(My_RegisterSecondActivity.this, "性别不得为空！", Toast.LENGTH_SHORT).show();
                }

                if(!name.isEmpty() && !password.isEmpty() && !sex.isEmpty()){

                    user.setSex(sex);
                    user.setMyName(name);
                    user.setPassword(password);
                    user.save();

                    Intent intent = new Intent(My_RegisterSecondActivity.this, MainActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                }


            }
        });

        //获取sex的组件
        rg = (RadioGroup) findViewById(R.id.rg_sex);
        rb_Male = (RadioButton) findViewById(R.id.rb_Male);
        rb_Female = (RadioButton) findViewById(R.id.rb_FeMale);
        //TODO:sex点击事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_Male: sex = "男"; break;
                    case R.id.rb_FeMale: sex = "女"; break;
                }

            }
        });



    }
}
