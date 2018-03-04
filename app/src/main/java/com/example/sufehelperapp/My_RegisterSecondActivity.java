package com.example.sufehelperapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;


public class My_RegisterSecondActivity extends AppCompatActivity {

    private user user;
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
        user = (user) getIntent().getSerializableExtra("user_now");
        final String myPhone = user.getPhonenumber();
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
                    Log.d("sex",user.getSex());

                    Random random = new Random();
                    int index = random.nextInt(5);

                    switch(index){
                        case 0:user.setMyImageId(R.drawable.apple);break;
                        case 1:user.setMyImageId(R.drawable.banana);break;
                        case 2:user.setMyImageId(R.drawable.orange);break;
                        case 3:user.setMyImageId(R.drawable.icon_image);break;
                        case 4:user.setMyImageId(R.drawable.photo_lyh);break;
                    }//TODO: 上传图片保存图片


                    //获取到图片
                    //Bitmap headShot= BitmapFactory.decodeFile(imagePath);
                    Bitmap headShot= BitmapFactory.decodeFile(imagePath);
                    //把图片转换字节流
                    byte[] myimages=img(headShot);
                    //找到用户
                    user users=DataSupport.findFirst(user.class);
                    //保存
                    users.setHeadshot(myimages);
                    users.save();

                    //获取图片
                    byte[]images=user.getHeadshot();
                    Bitmap bitmap=BitmapFactory.decodeByteArray(images,0,images.length);

                    user.setMyName(name);
                    user.setPassword(password);
                    user.updateAll("phonenumber = ?",user.getPhonenumber());

                    Intent intent = new Intent(My_RegisterSecondActivity.this, My_RegisterThirdActivity.class);
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

    private byte[] img(Bitmap headShot) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = null;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
