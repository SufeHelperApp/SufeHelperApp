package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Task_InfoActivity extends AppCompatActivity {

    public static final String TASK_SELECTED = "task_selected";

    private task[] tasks =
            {new task("文静", R.drawable.apple, "13912345678",
                    "占座","二教206","18/2/12","9:00",
                    "5","微信联系"),
                    new task("戴晓东", R.drawable.banana, "13812345678",
                            "拿快递","快递中心","18/2/10","10:00",
                            "7","微信联系"),
                    new task("刘宇涵", R.drawable.orange,"13712345678",
                            "买饭","新食堂","18/2/17","11:00",
                            "6","微信联系")};
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
                        Intent intent1 = new Intent(Task_InfoActivity.this, MainActivity.class);
                        startActivity(intent1);
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_InfoActivity.this, ExploreActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_InfoActivity.this, MyActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        final task task = (task) getIntent().getSerializableExtra("task_selected");
        ImageView launcher_image = (ImageView) findViewById(R.id.taskinfo_image);
        TextView launcher_name = (TextView) findViewById(R.id.taskinfo_name);
        TextView launcher_phoneNumber = (TextView) findViewById(R.id.taskinfo_phoneNumber);
        TextView taskType = (TextView) findViewById(R.id.taskinfo_taskType);
        TextView subtaskType = (TextView) findViewById(R.id.taskinfo_subtaskType);
        TextView date = (TextView) findViewById(R.id.taskinfo_date);
        TextView time = (TextView) findViewById(R.id.taskinfo_time);
        TextView location = (TextView) findViewById(R.id.taskinfo_location);
        TextView payment = (TextView) findViewById(R.id.taskinfo_payment);
        TextView description = (TextView) findViewById(R.id.taskinfo_description);

        Glide.with(this).load(task.getLauncherImageId()).into(launcher_image);
        launcher_name.setText(task.getLauncherName());
        launcher_phoneNumber.setText(task.getLauncherPhoneNumber());
        taskType.setText(task.getTaskType());
        subtaskType.setText(task.getSubtaskType());
        date.setText(task.getDdlDate());
        time.setText(task.getDdlTime());
        location.setText(task.getLocation());
        payment.setText(task.getPayment());
        description.setText(task.getDescription());

        Button b1 = (Button) findViewById(R.id.receive_task_btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setIfAccepted(true);
                //TODO: task.setHelper(user); 参数为当前正在操作的User对象
                //TODO: user.getCredit().increase(30);
                Intent intent1 = new Intent(Task_InfoActivity.this, MainActivity.class);
                startActivity(intent1);
                Toast.makeText(Task_InfoActivity.this, "任务接收成功！",Toast.LENGTH_SHORT).show();
            }
        });


    }


}
