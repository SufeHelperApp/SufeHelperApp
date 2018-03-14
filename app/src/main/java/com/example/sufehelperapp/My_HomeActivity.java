package com.example.sufehelperapp;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static org.litepal.LitePalApplication.getContext;

public class My_HomeActivity extends AppCompatActivity {

    private user user1;
    private TextView bar_num;
    private int MsgCount;
    private ImageView reddot;

    //接受user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        user1 = (user) getIntent().getSerializableExtra("user_now");
        Log.d("My_HomeActivity",user1.getMyName());

        Log.d("My_HomeActivity:msg",String.valueOf(user1.getMsg()));

        bar_num = (TextView) findViewById(R.id.bar_num);
        reddot = findViewById(R.id.reddot);
        MsgCount = user1.getMsg();
        setMessageCount(MsgCount);

        ImageButton mailbox = findViewById(R.id.bar_iv);
        mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1.setIfClicked(true);
                user1.clearMsg();
                user1.updateAll("phonenumber = ? and myName = ?",user1.getPhonenumber(),
                        user1.getMyName());
                Intent intent = new Intent(My_HomeActivity.this, Mailbox.class);
                intent.putExtra("user_now", user1);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(My_HomeActivity.this, Task_HomeActivity.class);
                        intent1.putExtra("user_now", user1);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(My_HomeActivity.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user1);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        break;
                }
                return true;
            }
        });

        ImageView image = (ImageView) findViewById(R.id.button_picture);
        Glide.with(getContext()).load(user1.getMyImageId()).into(image);
        /*
        byte[] images=user.getHeadshot();
        Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
        image.setImageBitmap(bitmap);*/

        TextView nicknameView = (TextView) findViewById(R.id.username_text);
        nicknameView.setText(user1.getMyName());

        Button button1 = (Button) findViewById(R.id.button_setup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_HomeActivity.this, MyActivity_Setup.class);
                intent.putExtra("user_now", user1);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_mytask);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_HomeActivity.this, MyActivity_Mytask.class);
                intent.putExtra("user_now", user1);
                intent.putExtra("tabNum", 0);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.button_vip);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_HomeActivity.this, MyActivity_Vip.class);
                intent.putExtra("user_now", user1);
                startActivity(intent);
            }
        });
        /**Button button4 = (Button) findViewById(R.id.button_talent);
         button4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(My_HomeActivity.this, Explore_MyTalent.class);
        startActivity(intent);
        }
        });*/
        Button button5 = (Button) findViewById(R.id.button_grade);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_HomeActivity.this,  MyActivity_credit.class);
                intent.putExtra("user_now", user1);
                startActivity(intent);
            }
        });



    }

    public void setMessageCount(int count) {

        if (count == 0) {
            bar_num.setVisibility(View.GONE);
            reddot.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 100) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("99+");
            }
        }
    }


    @Override
    public void onBackPressed(){

    }
}
