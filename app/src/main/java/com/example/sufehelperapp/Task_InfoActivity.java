package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Task_InfoActivity extends AppCompatActivity {

    private task task;

    public static final String TASK_SELECTED = "task_selected";
    public static final String USER_NOW = "user_now";

    private user user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Task_InfoActivity",myName);

        task = (task) getIntent().getSerializableExtra("task_selected");

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_InfoActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_InfoActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        Button button3 = (Button) findViewById(R.id.receive_task_btn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.receive_task_btn:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Task_InfoActivity.this);
                        dialog.setTitle("是否确定接收任务？");
                        dialog.setMessage("如果您中途放弃该任务，将会降低您在SufeHelper上的信用评分。累计三次违约的账户将被冻结。");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String helperName = user.getMyName();

                                //不得接收自己的任务
                                if(task.getLauncherName().equals(helperName)){
                                    Toast.makeText(Task_InfoActivity.this, "请勿接收自己的任务！",
                                            Toast.LENGTH_SHORT).show();
                                }else {

                                    //更新该task信息
                                    Log.d("msg","accepted");
                                    task.setIfAccepted(true);
                                    task.setAccepttime();
                                    task.setProgress(2); //已接受
                                    task.setHelper(user);
                                    task.setHelperName(helperName);
                                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                            task.getLauncherName());

                                    task.updateTaskStatus(); //TODO: 排查
                                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                            task.getLauncherName());


                                    user.increaseCredit(30);
                                    user.addTaskRNum(1);
                                    if (task.getSubtaskType() == "占座") {
                                        user.taskRNum_e1++;
                                        user.addTaskRNum_errand(1);
                                    }else if(task.getSubtaskType() == "拿快递") {
                                        user.taskRNum_e2++;
                                        user.addTaskRNum_errand(1);
                                    }else if(task.getSubtaskType() == "买饭") {
                                        user.taskRNum_e3++;
                                        user.addTaskRNum_errand(1);
                                    }else if(task.getSubtaskType() == "买东西") {
                                        user.taskRNum_e4++;
                                        user.addTaskRNum_errand(1);
                                    }else if(task.getSubtaskType() == "拼单") {
                                        user.taskRNum_e5++;
                                        user.addTaskRNum_errand(1);
                                    }else if(task.getSubtaskType() == "电子产品修理") {
                                        user.taskRNum_s1++;
                                        user.addTaskRNum_skill(1);
                                    }else if(task.getSubtaskType() == "家具器件组装") {
                                        user.taskRNum_s2++;
                                        user.addTaskRNum_skill(1);
                                    }else if(task.getSubtaskType() == "学习作业辅导") {
                                        user.taskRNum_s3++;
                                        user.addTaskRNum_skill(1);
                                    }else if(task.getSubtaskType() == "技能培训") {
                                        user.taskRNum_s4++;
                                        user.addTaskRNum_skill(1);
                                    }else if(task.getSubtaskType() == "找同好") {
                                        user.taskRNum_s5++;
                                        user.addTaskRNum_skill(1);
                                    }else if(task.getSubtaskType() == "选课指南") {
                                        user.taskRNum_c1++;
                                        user.addTaskRNum_counsel(1);
                                    }else if(task.getSubtaskType() == "考研出国经验"){
                                        user.taskRNum_c2++;
                                        user.addTaskRNum_counsel(1);
                                    }else if(task.getSubtaskType() == "求职经验") {
                                        user.taskRNum_c3++;
                                        user.addTaskRNum_counsel(1);
                                    }else if(task.getSubtaskType() == "票务转让") {
                                        user.taskRNum_c4++;
                                        user.addTaskRNum_counsel(1);
                                    }else if(task.getSubtaskType() == "二手闲置") {
                                        user.taskRNum_c5++;
                                        user.addTaskRNum_counsel(1);
                                }

                                    user.addTaskNum(1);

                                    user.updateAll("phonenumber = ?",user.getPhonenumber());
                                    Log.d("msg1",String.valueOf(user.getTaskRNum_errand()));
                                    Log.d("msg2",String.valueOf(user.getTaskRNum_skill()));
                                    Log.d("msg3",String.valueOf(user.getTaskRNum_counsel()));

                                    Intent intent1 = new Intent(Task_InfoActivity.this, Task_HomeActivity.class);
                                    intent1.putExtra("user_now", user);
                                    startActivity(intent1);
                                    Toast.makeText(Task_InfoActivity.this, "任务接收成功！", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });

        ImageView launcher_image = (ImageView) findViewById(R.id.taskinfo_image);
        TextView launcher_name = (TextView) findViewById(R.id.taskinfo_name);
        TextView launcher_phoneNumber = (TextView) findViewById(R.id.taskinfo_phoneNumber);
        TextView taskType = (TextView) findViewById(R.id.taskinfo_taskType);
        TextView subtaskType = (TextView) findViewById(R.id.taskinfo_subtaskType);
        TextView date = (TextView) findViewById(R.id.taskinfo_date);
        TextView time = (TextView)findViewById(R.id.taskinfo_time);
        TextView area = (TextView)findViewById(R.id.taskinfo_area);
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
        area.setText(task.getArea());
        location.setText(task.getLocation());
        String paymentString = Double.toString(task.getPayment());
        payment.setText(paymentString);
        description.setText(task.getDescription());

        Button btn = findViewById(R.id.chakan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_InfoActivity.this, MyActivity_mytask_personalhome.class);
                intent.putExtra("user_now", user);
                List<user> userList = DataSupport.where("myName = ?",task.getLauncherName()).find(user.class);
                user launcher = userList.get(0);
                Log.d("launcher",launcher.getMyName());
                intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, launcher);
                startActivity(intent);
                Toast.makeText(Task_InfoActivity.this, "任务接收成功！", Toast.LENGTH_SHORT).show();
            }
        });



                //接收任务
        /*Button b1 = (Button) findViewById(R.id.receive_task_btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String helperName = user.getMyName();

                //不得接收自己的任务
                if(task.getLauncherName().equals(helperName)){
                    Toast.makeText(Task_InfoActivity.this, "请勿接收自己的任务！",
                            Toast.LENGTH_SHORT).show();
                }else {

                    //更新该task信息
                    task.setIfAccepted(true);
                    task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                            task.getLauncherName());
                    task.updateTaskStatus(); //TODO: 排查
                    task.setProgress(2); //已接受
                    task.setHelper(user);
                    task.setHelperName(helperName);
                    task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                            task.getLauncherName());

                    user.increaseCredit(30);
                    user.addTaskRNum(1);
                    if (task.getTaskType() == "跑腿") {
                        user.addTaskRNum_errand(1);
                    }else if(task.getTaskType() == "技能"){
                        user.addTaskRNum_skill(1);
                    }else if(task.getTaskType() == "咨询"){
                        user.addTaskRNum_counsel(1);
                    }
                    user.addTaskNum(1);

                    user.updateAll("phonenumber = ?",user.getPhonenumber());
                    Log.d("msg1",String.valueOf(user.getTaskRNum_errand()));
                    Log.d("msg2",String.valueOf(user.getTaskRNum_skill()));
                    Log.d("msg3",String.valueOf(user.getTaskRNum_counsel()));

                    Intent intent1 = new Intent(Task_InfoActivity.this, Task_HomeActivity.class);
                    intent1.putExtra("user_now", user);
                    startActivity(intent1);
                    Toast.makeText(Task_InfoActivity.this, "任务接收成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }

    //未添加back返回值


}
