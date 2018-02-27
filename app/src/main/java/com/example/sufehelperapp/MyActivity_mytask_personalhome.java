package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MyActivity_mytask_personalhome extends AppCompatActivity {

    public static final String USER_SELECTED = "user_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask_personalhome);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        final user user = (user) getIntent().getSerializableExtra("user_selected");
        Log.d("name",user.getMyName());

        ImageView launcher_image = (ImageView) findViewById(R.id.picture_upload);
        TextView launcher_name = (TextView) findViewById(R.id.his_nickname_text11);
        TextView launcher_phoneNumber = (TextView) findViewById(R.id.his_address_text22);
        TextView taskType = (TextView) findViewById(R.id.his_phonenumber_text33);
        TextView subtaskType = (TextView) findViewById(R.id.his_personalintention_text44);
        TextView date = (TextView) findViewById(R.id.his_integral_text11);

        Glide.with(this).load(user.getMyImageId()).into(launcher_image);
        launcher_name.setText(user.getMyName());
        //launcher_phoneNumber.setText(user.getDormArea()); //TODO
        taskType.setText(user.getPhonenumber());
        //subtaskType.setText(user.getp());
        //date.setText(task.getDdlDate());

        Button button2 = (Button) findViewById(R.id.button_talk);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_mytask_personalhome.this, MyActivity_chat.class);
                startActivity(intent);
            }
        });
    }
}

