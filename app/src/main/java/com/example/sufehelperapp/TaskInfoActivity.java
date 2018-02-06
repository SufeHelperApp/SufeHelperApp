package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TaskInfoActivity extends AppCompatActivity {

    public static final String TASK_ID = "task_id";

    private task[] tasks =
            {new task(0,"文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","5"),
                    new task(1,"戴晓东", R.drawable.banana, "13812345678",
                            "拿快递","快递中心","18/2/10","7"),
                    new task(2,"刘宇涵", R.drawable.orange,"13712345678",
                            "买饭","新食堂","18/2/17","6")};
    // NOTE: 可删除，用数据库取代

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent1 = new Intent(TaskInfoActivity.this, ExploreActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(TaskInfoActivity.this, MyActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
        int taskId = intent.getIntExtra(TASK_ID,0);
        task task = tasks[taskId]; // TODO：用数据库方法取代
        ImageView launcher_image = (ImageView) findViewById(R.id.taskinfo_image);
        TextView launcher_name = (TextView) findViewById(R.id.taskinfo_name);
        TextView launcher_phoneNumber = (TextView) findViewById(R.id.taskinfo_phoneNumber);
        TextView taskType = (TextView) findViewById(R.id.taskinfo_taskType);
        TextView subtaskType = (TextView) findViewById(R.id.taskinfo_subtaskType);
        TextView time = (TextView) findViewById(R.id.taskinfo_time);
        TextView location = (TextView) findViewById(R.id.taskinfo_location);
        TextView payment = (TextView) findViewById(R.id.taskinfo_payment);
        TextView description = (TextView) findViewById(R.id.taskinfo_description);

        Glide.with(this).load(task.getImageId()).into(launcher_image);
        launcher_name.setText(task.getLauncherName());
        launcher_phoneNumber.setText(task.getLauncherPhoneNumber());
        taskType.setText(task.getTaskType());
        //subtaskType.setText(task.getTaskType());
        time.setText(task.getDeadline());
        location.setText(task.getLocation());
        payment.setText(task.getPayment());

    }


}
