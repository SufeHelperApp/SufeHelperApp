package com.example.sufehelperapp;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyActivity_mytask_personalhome extends AppCompatActivity {

    public static final String USER_SELECTED = "user_selected";
    private user user_selected; //2
    private user user; //1
    private task task; //3
    private int num; //4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask_personalhome);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("personalhome",myName);

        //接收选中的user
        user_selected = (user) getIntent().getSerializableExtra("user_selected");
        Log.d("name",user_selected.getMyName());

        task = (task) getIntent().getSerializableExtra("task_selected");

        num = getIntent().getIntExtra("num",1);


        ImageView launcher_image = (ImageView) findViewById(R.id.picture_upload);
        TextView launcher_name = (TextView) findViewById(R.id.his_nickname_text11);
        TextView launcher_sex = (TextView) findViewById(R.id.sex_text);
        TextView launcher_dormName = (TextView) findViewById(R.id.his_address_text22);
        TextView launcher_phonenumber = (TextView) findViewById(R.id.his_phonenumber_text33);
        TextView specialty = (TextView) findViewById(R.id.his_personalintention_text44);
        TextView averagescore = (TextView) findViewById(R.id.his_integral_text11);


        Glide.with(this).load(user_selected.getMyImageId()).into(launcher_image);
        launcher_sex.setText(user_selected.getSex());
        launcher_name.setText(user_selected.getMyName());
        launcher_dormName.setText(user_selected.getDormitoryLocation());
        launcher_phonenumber.setText(user.getPhonenumber());
        averagescore.setText(String.valueOf(user_selected.getAverageScore()));
        if(user.getSpecialty().size()!=0) {
            specialty.setText(user.getSpecialty().get(0));
        }


    }

    //未添加back返回值

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_mytask_personalhome.this, Task_InfoActivity.class);
        intent.putExtra(Task_InfoActivity.USER_NOW, user);
        intent.putExtra(Task_InfoActivity.TASK_SELECTED, task);
        intent.putExtra("num",1);
        startActivity(intent);
        finish();
    }



}

