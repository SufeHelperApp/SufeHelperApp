package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Task_InfoActivity extends AppCompatActivity {

    private user user;
    private task task;
    private int num;

    private String myPhone;
    Connection con;
    ResultSet rs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //num
        num = getIntent().getIntExtra("num",1);

        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            Log.d("Info_phone",myPhone);
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            Log.d("userList",String.valueOf(userList.size()));
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

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
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_InfoActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        Button button3 = (Button) findViewById(R.id.receive_task_btn);

        if(num == 2){ //task_detail
            button3.setVisibility(View.GONE);
        }else if(num == 1 || num == 3){

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.receive_task_btn:

                        String helperName = user.getMyName();
                        if (task.getLauncherName().equals(helperName)) {
                            Toast.makeText(Task_InfoActivity.this, "请勿接收自己的任务！",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(Task_InfoActivity.this);
                            dialog.setTitle("是否确定接收任务？");
                            dialog.setMessage("如果您中途放弃任务，将会降低您在iTask上的信用评分。账户累计三次违约将被冻结。");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String helperName = user.getMyName();
                                    int taskID = task.getTaskID();
                                    String accepttime = TimeUtils.getNowTime();
                                    int taskRNum = user.getTaskRNum()+1;
                                    int taskNum = user.getTaskNum()+1;
                                    int taskRNum_errand = user.getTaskRNum_errand();
                                    int taskRNum_skill = user.getTaskRNum_skill();
                                    int taskRNum_counsel = user.getTaskRNum_counsel();
                                    int taskRNum_e1 = user.taskRNum_e1;
                                    int taskRNum_e2 = user.taskRNum_e2;
                                    int taskRNum_e3 = user.taskRNum_e3;
                                    int taskRNum_e4 = user.taskRNum_e4;
                                    int taskRNum_e5 = user.taskRNum_e5;
                                    int taskRNum_s1 = user.taskRNum_s1;
                                    int taskRNum_s2 = user.taskRNum_s2;
                                    int taskRNum_s3 = user.taskRNum_s3;
                                    int taskRNum_s4 = user.taskRNum_s4;
                                    int taskRNum_s5 = user.taskRNum_s5;
                                    int taskRNum_c1 = user.taskRNum_c1;
                                    int taskRNum_c2 = user.taskRNum_c2;
                                    int taskRNum_c3 = user.taskRNum_c3;
                                    int taskRNum_c4 = user.taskRNum_c4;
                                    int taskRNum_c5 = user.taskRNum_c5;

                                    int credit = user.getCredit()+30;

                                    //TODO: ADD

                                    if (task.getSubtaskType().equals("占座")) {
                                        taskRNum_e1++;
                                        taskRNum_errand++;
                                    } else if (task.getSubtaskType().equals("拿快递")) {
                                        taskRNum_e2++;
                                        taskRNum_errand++;
                                    } else if (task.getSubtaskType().equals("买饭")) {
                                        taskRNum_e3++;
                                        taskRNum_errand++;
                                    } else if (task.getSubtaskType().equals("买东西")) {
                                        taskRNum_e4++;
                                        taskRNum_errand++;
                                    } else if (task.getSubtaskType().equals("拼单")) {
                                        taskRNum_e5++;
                                        taskRNum_errand++;
                                    } else if (task.getSubtaskType().equals("电子产品修理")) {
                                        taskRNum_s1++;
                                        taskRNum_skill++;
                                    } else if (task.getSubtaskType().equals("家具器件组装")) {
                                        taskRNum_s2++;
                                        taskRNum_skill++;
                                    } else if (task.getSubtaskType().equals("学习作业辅导")) {
                                        taskRNum_s3++;
                                        taskRNum_skill++;
                                    } else if (task.getSubtaskType().equals("技能培训")) {
                                        taskRNum_s4++;
                                        taskRNum_skill++;
                                    } else if (task.getSubtaskType().equals("找同好")) {
                                        taskRNum_s5++;
                                        taskRNum_skill++;
                                    } else if (task.getSubtaskType().equals("周边服务")) {
                                        taskRNum_c1++;
                                        taskRNum_counsel++;
                                    } else if (task.getSubtaskType().equals("考研出国经验")) {
                                        taskRNum_c2++;
                                        taskRNum_counsel++;
                                    } else if (task.getSubtaskType().equals("求职经验")) {
                                        taskRNum_c3++;
                                        taskRNum_counsel++;
                                    } else if (task.getSubtaskType().equals("票务转让")) {
                                        taskRNum_c4++;
                                        taskRNum_counsel++;
                                    } else if (task.getSubtaskType().equals("二手闲置")) {
                                        taskRNum_c5++;
                                        taskRNum_counsel++;
                                    }

                                    //更新该task信息

                                    try {
                                        StrictMode.ThreadPolicy policy =
                                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);

                                        con = DbUtils.getConn();
                                        Statement st = con.createStatement();
                                        st.executeUpdate("UPDATE `task` SET `ifAccepted` = '1', `ifDisplayable` = '0',`accepttime` = '"+accepttime+"', " +
                                                "`progress` = '2',`helperName` = '"+helperName+"',`StatusText` = '待完成' " +
                                                "WHERE `taskID` = '"+taskID+"'");

                                        st.executeUpdate("UPDATE `user` SET `credit` = '"+credit+"', `credit` = '"+credit+"' ," +
                                                "`taskRNum_errand` = '"+taskRNum_errand+"',`taskRNum_skill` = '"+taskRNum_skill+"',`taskRNum_counsel` = '"+taskRNum_counsel+"'," +
                                                "`taskRNum` = '"+taskRNum+"',`taskNum` = '"+taskNum+"',`taskRNum_e1` = '"+taskRNum_e1+"'" +
                                                ",`taskRNum_e2` = '"+taskRNum_e2+"',`taskRNum_e3` = '"+taskRNum_e3+"',`taskRNum_e4` = '"+taskRNum_e4+"'" +
                                                ",`taskRNum_e5` = '"+taskRNum_e5+"',`taskRNum_s1` = '"+taskRNum_s1+"',`taskRNum_s2` = '"+taskRNum_s2+"'" +
                                                ",`taskRNum_s3` = '"+taskRNum_s3+"',`taskRNum_s4` = '"+taskRNum_s4+"',`taskRNum_s5` = '"+taskRNum_s5+"'" +
                                                " ,`taskRNum_c1` = '"+taskRNum_c1+"' ,`taskRNum_c2` = '"+taskRNum_c2+"' ,`taskRNum_c3` = '"+taskRNum_c3+"'" +
                                                ",`taskRNum_c4` = '"+taskRNum_c4+"' ,`taskRNum_c5` = '"+taskRNum_c5+"' WHERE `phonenumber` = '" + myPhone + "'");

                                        con.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }



                                    //给发布者一个提醒, msg+1, s+taskID
                                    String launcherName = task.getLauncherName();
                                    try{
                                        StrictMode.ThreadPolicy policy =
                                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);

                                        con = DbUtils.getConn();

                                        Statement st = con.createStatement();


                                        rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '"+launcherName+"'");

                                        if (rs.next()) {

                                            //msg+1
                                            int msg = rs.getInt("msg")+1;

                                            //s+taskID
                                            String s = rs.getString("msgTaskListString");
                                            if(s.isEmpty()){
                                                s = s + String.valueOf(task.getTaskID());
                                            }else{
                                                s = s + ";" + String.valueOf(task.getTaskID());
                                            }

                                            String sql1 = "UPDATE `user` SET `msg`= '"+msg+"' WHERE myName='"+launcherName+"'";
                                            st.executeUpdate(sql1);

                                            String sql2 = "UPDATE `user` SET `msgTaskListString`= '"+s+"' WHERE myName='"+launcherName+"'";
                                            st.executeUpdate(sql2);

                                        }

                                        rs.close();
                                        st.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }finally {
                                        if (con != null)
                                            try {
                                                con.close();
                                            } catch (SQLException e) {
                                            }

                                    }


                                    Intent intent1 = new Intent(Task_InfoActivity.this, Map_for_task.class);
                                    intent1.putExtra("user_phone", myPhone);
                                    startActivity(intent1);
                                    Toast.makeText(Task_InfoActivity.this, "任务接收成功！", Toast.LENGTH_SHORT).show();

                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            dialog.show();

                        }
                        break;
                    default:
                        break;
                }
            }
        });

        }

        ImageView launcher_image = (ImageView) findViewById(R.id.taskinfo_image);
        TextView launcher_name = (TextView) findViewById(R.id.taskinfo_name);
        TextView launcher_phoneNumber = (TextView) findViewById(R.id.taskinfo_phoneNumber);
        TextView taskType = (TextView) findViewById(R.id.taskinfo_taskType);
        TextView subtaskType = (TextView) findViewById(R.id.taskinfo_subtaskType);
        TextView date = (TextView) findViewById(R.id.taskinfo_date);
        TextView time = (TextView)findViewById(R.id.taskinfo_time);
        //TextView area = (TextView)findViewById(R.id.taskinfo_area);
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
        //area.setText(task.getArea());
        location.setText(task.getLocation());
        String paymentString = Double.toString(task.getPayment());
        payment.setText(paymentString);
        description.setText(task.getDescription());

        Button btn = findViewById(R.id.chakan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_InfoActivity.this, MyActivity_mytask_personalhome.class);
                intent.putExtra("user_phone", myPhone); //1
                try {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    con = DbUtils.getConn();
                    Statement st = con.createStatement();
                    rs = st.executeQuery("SELECT * FROM user WHERE myName = '"+task.getLauncherName()+"'");
                    List list = DbUtils.populate(rs,user.class);
                    user launcher = (user) list.get(0);

                    Log.d("launcher",launcher.getMyName());
                    intent.putExtra(MyActivity_mytask_personalhome.USER_SELECTED, launcher); //2
                    intent.putExtra("task_selected", task); //3
                    intent.putExtra("num", 1); //4
                    startActivity(intent);

                    con.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

    //未添加back返回值


}
