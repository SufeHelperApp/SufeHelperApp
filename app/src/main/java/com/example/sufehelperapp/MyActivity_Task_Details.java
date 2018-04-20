package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_Task_Details extends AppCompatActivity {

    private String myPhone;
    Connection con;
    ResultSet rs;

    private task task;
    private user user;
    private int num;

    private int tabNum;

    private RecyclerView rvTrace;
    private List<Trace> traceList = new ArrayList<>(4);
    private TraceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_details);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
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
        //TODO:调出选中的task

        num = getIntent().getIntExtra("num",1);

        Button button = (Button) findViewById(R.id.title_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num ==2) { //mytask
                    Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Mytask.class);
                    intent.putExtra("user_phone", myPhone);
                    //TODO:调出选中的task的launcherName，helperName,progress,当前user的myName
                    if (task.getLauncherName().equals(user.getMyName())) {
                        tabNum = 1;
                    }else if(task.getProgress() >= 2){
                        if(task.getHelperName().equals(user.getMyName())){
                            tabNum = 0;
                        }
                    }else{
                        tabNum = 1;
                    }

                    Log.d("detail-curr-tabNum",String.valueOf(tabNum));
                    intent.putExtra("tabNum", tabNum);
                    startActivity(intent);
                    finish();
                }else if(num == 3){ //history
                    Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Historical_Task.class);
                    intent.putExtra("user_phone", myPhone);
                    if (task.getLauncherName().equals(user.getMyName())) {
                        tabNum = 1;
                    }else if(task.getProgress() >= 2){
                        if(task.getHelperName().equals(user.getMyName())){
                            tabNum = 0;
                        }
                    }else{
                        tabNum = 1;
                    }

                    Log.d("detail-hist-tabNum",String.valueOf(tabNum));
                    intent.putExtra("tabNum", tabNum);
                    startActivity(intent);
                    finish();
                }else if(num == 4){ //credit
                    Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_credit.class);
                    intent.putExtra("user_phone", myPhone);
                    startActivity(intent);
                    finish();
                }else if (num == 5) { //mailbox
                    Intent intent = new Intent(MyActivity_Task_Details.this, Mailbox.class);
                    intent.putExtra("user_phone", myPhone);
                    intent.putExtra("num",2);
                    intent.putExtra("task_selected",task);
                    startActivity(intent);
                    finish();
                }
            }
        });

        findView();

        TextView locationView = findViewById(R.id.get_address);
        locationView.setText(task.getLocation());

        TextView launcherView = findViewById(R.id.details_launcher_text);
        launcherView.setText(task.getLauncherName());

        TextView helperView = findViewById(R.id.details_helper_text);
        helperView.setText(task.getHelperName());

        TextView scoreView = findViewById(R.id.details_score_text);
        scoreView.setText(String.valueOf(task.getScore()));

        TextView ddlView = findViewById(R.id.details_ddl_text);
        ddlView.setText(task.getDdl());

        TextView paymentView = findViewById(R.id.details_payment_text);
        paymentView.setText(String.valueOf(task.getPayment()));

        Button btn_more = findViewById(R.id.more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyActivity_Task_Details.this, Task_InfoActivity.class);
                intent.putExtra("num",2);
                intent.putExtra("user_phone", myPhone);
                intent.putExtra("task_selected", task);
                startActivity(intent);

            }
        });


        final Button btn_wait_finish = (Button) findViewById(R.id.to_wait_finish);
        final Button btn_finish = (Button) findViewById(R.id.to_finish);
        final Button btn_wait_pay = (Button) findViewById(R.id.to_wait_pay);
        final Button btn_payoff = (Button) findViewById(R.id.to_payoff);
        final Button btn_score = (Button) findViewById(R.id.to_score);
        final Button btn_wait_score = (Button) findViewById(R.id.to_wait_score);
        final Button btn_close = (Button) findViewById(R.id.to_close);
        btn_wait_finish.setVisibility(View.GONE);
        btn_finish.setVisibility(View.GONE);
        btn_wait_pay.setVisibility(View.GONE);
        btn_payoff.setVisibility(View.GONE);
        btn_score.setVisibility(View.GONE);
        btn_wait_score.setVisibility(View.GONE);
        btn_close.setVisibility(View.GONE);

        if(user.getMyName().equals(task.getHelperName())){ //当前用户是helper
            initData();
            if(task.getProgress() == 2) { //待完成
                Log.d("msg","接收者完成");
                btn_finish.setVisibility(View.VISIBLE);
                btn_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //点击按钮，接收者完成任务（progress3）
                        btn_finish.setVisibility(View.GONE);
                        btn_wait_pay.setVisibility(View.VISIBLE); //显示待支付
                        String achievetime = TimeUtils.getNowTime();

                        try {
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            con = DbUtils.getConn();
                            Statement st = con.createStatement();
                            st.executeUpdate("UPDATE `task` SET `progress` = '3',`achievetime` = '"+achievetime+"',`StatusText` = '已完成，待支付' " +
                                    "WHERE `preciseLaunchTime` = '" + task.getPreciseLaunchTime() + "'");

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

                        addData(1); //增加完成信息

                        Log.d("progress",String.valueOf(task.getProgress()));
                    }
                });
            }else if(task.getProgress() == 3){ //待支付
                Log.d("msg","接收者待支付");
                btn_wait_pay.setVisibility(View.VISIBLE);
            }else if(task.getProgress() == 4){ //待评价
                Log.d("msg","接收者待评价");
                btn_wait_score.setVisibility(View.VISIBLE);
            } else if (task.getProgress() == 5) { //已结束
                Log.d("msg","接收者已结束");
                btn_close.setVisibility(View.VISIBLE);
            }
        }

        if (user.getMyName().equals(task.getLauncherName())) { //当前用户是launcher
            initData();
            if(task.getProgress() == 2) { //待完成
                Log.d("msg","发布者待完成");
                btn_wait_finish.setVisibility(View.VISIBLE);
            }else if(task.getProgress() == 3){
                Log.d("msg","发布者支付");
                btn_payoff.setVisibility(View.VISIBLE);
                btn_payoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击按钮，发布者支付(progress4)
                        btn_payoff.setVisibility(View.GONE);
                        String paytime = TimeUtils.getNowTime();


                        try {
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            con = DbUtils.getConn();
                            Statement st = con.createStatement();
                            st.executeUpdate("UPDATE `task` SET `progress` = '4',`paytime` = '"+paytime+"',`StatusText` = '待评价' " +
                                    "WHERE `preciseLaunchTime` = '" + task.getPreciseLaunchTime() + "'");

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //给接收者一个提醒, msg+1, s+taskID
                        String helperName = task.getHelperName();
                        try{
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            con = DbUtils.getConn();

                            Statement st = con.createStatement();


                            rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '"+helperName+"'");

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

                                String sql1 = "UPDATE `user` SET `msg`= '"+msg+"' WHERE myName='"+helperName+"'";
                                st.executeUpdate(sql1);

                                String sql2 = "UPDATE `user` SET `msgTaskListString`= '"+s+"' WHERE myName='"+helperName+"'";
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

                        addData(2);//增加支付信息

                        btn_score.setVisibility(View.VISIBLE);
                        btn_score.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("msg","发布者评价");
                                Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_evaluation.class);
                                intent.putExtra("user_phone", myPhone);
                                intent.putExtra("num",num);
                                intent.putExtra("task_selected", task);
                                startActivity(intent);

                            }
                        });
                    }
                });
            }else if(task.getProgress() == 4){ //评价
                Log.d("msg","发布者评价");
                btn_score.setVisibility(View.VISIBLE);
                btn_score.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_evaluation.class);
                        intent.putExtra("user_phone", myPhone);
                        intent.putExtra("task_selected", task);
                        intent.putExtra("num", num);
                        startActivity(intent);

                    }
                });
            } else if (task.getProgress() == 5) { //已结束
                Log.d("msg","发布者结束");
                btn_close.setVisibility(View.VISIBLE);

            }
        }

    }



    private void findView() {
        rvTrace = (RecyclerView) findViewById(R.id.recycler_time_list);
    }

    private void initData() {
            if (task.getProgress() == 2) {
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String accepttime = task.getAccepttime();
                traceList.add(new Trace(accepttime, "[接受任务] 任务已接收"));
            } else if (task.getProgress() == 3) {
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String accepttime = task.getAccepttime();
                traceList.add(new Trace(accepttime, "[接受任务] 任务已接收"));
                String achievetime = task.getAchievetime();
                traceList.add(new Trace(achievetime, "[完成情况] 接收者已完成任务"));
            } else if (task.getProgress() == 4) {
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String accepttime = task.getAccepttime();
                traceList.add(new Trace(accepttime, "[接受任务] 任务已接收"));
                String achievetime = task.getAchievetime();
                traceList.add(new Trace(achievetime, "[完成情况] 接收者已完成任务"));
                String paytime = task.getPaytime();
                traceList.add(new Trace(paytime, "[支付情况] 发布者已支付报酬"));
            } else if (task.getProgress() == 5) {
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String accepttime = task.getAccepttime();
                traceList.add(new Trace(accepttime, "[接受任务] 任务已接收"));
                String achievetime = task.getAchievetime();
                traceList.add(new Trace(achievetime, "[完成情况] 接收者已完成任务"));
                String paytime = task.getPaytime();
                traceList.add(new Trace(paytime, "[支付情况] 发布者已支付报酬"));
                String finishtime = task.getFinishtime();
                traceList.add(new Trace(finishtime, "[支付情况] 发布者已完成评价"));
            } else if(task.getProgress() ==6){
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String finishtime = task.getDdl();
                traceList.add(new Trace(finishtime, "[支付情况] 逾期未被接收"));
            } else if(task.getProgress() ==7){
                String launchtime = task.getLaunchtime();
                traceList.add(new Trace(launchtime, "[任务发布] 任务已发布"));
                String accepttime = task.getAccepttime();
                traceList.add(new Trace(accepttime, "[接受任务] 任务已接收"));
                String finishtime = task.getDdl();
                traceList.add(new Trace(finishtime, "[支付情况] 接受者未及时完成"));
            }
        adapter = new TraceListAdapter(this,traceList,task);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);
    }

    private void addData(int type){
        if(type == 1){
            String achievetime = task.getAchievetime();
            traceList.add(new Trace(achievetime, "[完成情况] 接收者已完成任务"));
        }else if(type == 2){
            String paytime = task.getPaytime();
            traceList.add(new Trace(paytime, "[支付情况] 发布者已支付报酬"));
        }

        adapter = new TraceListAdapter(this,traceList,task);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);

    }

    @Override
    public void onBackPressed(){
        if(num ==2) { //mytask
            Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Mytask.class);
            intent.putExtra("user_phone", myPhone);
            if (task.getLauncherName().equals(user.getMyName())) {
                tabNum = 1;
            }else if(task.getProgress() >= 2){
                if(task.getHelperName().equals(user.getMyName())){
                    tabNum = 0;
                }
            }else{
                tabNum = 1;
            }

            Log.d("detail-curr-tabNum",String.valueOf(tabNum));
            intent.putExtra("tabNum", tabNum);
            startActivity(intent);
            finish();
        }else if(num == 3){ //history
            Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_Historical_Task.class);
            intent.putExtra("user_phone", myPhone);
            if (task.getLauncherName().equals(user.getMyName())) {
                tabNum = 1;
            }else if(task.getProgress() >= 2){
                if(task.getHelperName().equals(user.getMyName())){
                    tabNum = 0;
                }
            }else{
                tabNum = 1;
            }

            Log.d("detail-hist-tabNum",String.valueOf(tabNum));
            intent.putExtra("tabNum", tabNum);
            startActivity(intent);
            finish();
        }else if(num == 4){ //credit
            Intent intent = new Intent(MyActivity_Task_Details.this, MyActivity_credit.class);
            intent.putExtra("user_phone", myPhone);
            startActivity(intent);
            finish();
        }else if (num == 5) { //mailbox
            Intent intent = new Intent(MyActivity_Task_Details.this, Mailbox.class);
            intent.putExtra("user_phone", myPhone);
            intent.putExtra("num",2);
            intent.putExtra("task_selected",task);
            startActivity(intent);
            finish();
        }
    }
}
