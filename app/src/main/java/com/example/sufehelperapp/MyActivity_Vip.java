package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity_Vip extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MyActivity_Vip", user.getMyName());

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        Intent intent1 = new Intent( MyActivity_Vip.this,MainActivity.class);
                        intent1.putExtra("user_now", user);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent( MyActivity_Vip.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        break;
                }
                return true;
            }
        });


        //show level
        TextView levelView = (TextView) findViewById(R.id.vip_text_level);
        levelView.setText("Lv." + user.getLevel());

        //show credit
        TextView creditView = (TextView) findViewById(R.id.vip_text_credit);
        creditView.setText(String.valueOf(user.getCredit()));

        //返回
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Vip.this, My_HomeActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        /*Button button2 = (Button) findViewById(R.id.button_myaward);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Vip.this, MyActivity_Myaward.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });*/

    }

}
