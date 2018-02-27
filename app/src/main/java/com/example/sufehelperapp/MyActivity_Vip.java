package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity_Vip extends AppCompatActivity {

    user user = new user(); //TODO: user改为当前user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user user = (user) getIntent().getSerializableExtra("user_data");
        String myName = user.getMyName();

        //show level
        TextView levelView = (TextView) findViewById(R.id.vip_text_level);
        levelView.setText("Lv."+ user.getLevel());

        //show credit
        TextView creditView = (TextView) findViewById(R.id.vip_text_credit);
        creditView.setText(String.valueOf(user.getCredit()));

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Vip.this, My_HomeActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_myaward);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Vip.this, MyActivity_Myaward.class);
                startActivity(intent);
            }
        });
    }
}
