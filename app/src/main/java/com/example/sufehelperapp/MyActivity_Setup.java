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

import static org.litepal.LitePalApplication.getContext;

public class MyActivity_Setup extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setup);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MyActivity_Setup",user.getMyName());

        user.setMyImageId(R.drawable.apple);
        ImageView image = (ImageView) findViewById(R.id.button_picture);
        Glide.with(getContext()).load(user.getMyImageId()).into(image);

        user.setMyName("戴晓东");
        TextView nicknameView = (TextView) findViewById(R.id.username_text);
        nicknameView.setText(user.getMyName());

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup.this, My_HomeActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_information);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Setup.this, MyActivity_Setup_Edit.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.button_logoff);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MyActivity_Setup.this, My_LoginFirstActivity.class);
                        //传输user的终点
                        startActivity(intent);
            }
        });

    }
}
