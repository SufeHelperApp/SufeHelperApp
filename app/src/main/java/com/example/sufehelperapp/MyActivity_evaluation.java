package com.example.sufehelperapp;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_evaluation extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView textView;
    private user user;
    private task task;
    private int num;

    private String myPhone;

    Connection con;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluation);
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
        //TODO:获得MyActivity_Task_Details中传入的task

        num = getIntent().getIntExtra("num",2);


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_evaluation.this, MyActivity_Task_Details.class);
                intent.putExtra("user_phone", myPhone);
                intent.putExtra("task_selected",task);
                intent.putExtra("num",num);

                startActivity(intent);
            }
        });

        ratingBar = (RatingBar) this.findViewById(R.id.ratingBar1);
        textView = (TextView) this.findViewById(R.id.textView1);
//      设置ratingBar的监听方法
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
//              textView append显示数据
//          textView.append("***当前值："+rating+"**步长："+ratingBar.getStepSize()+"\n");
//          switch case 语句对评价的星星个数进行判断 给出相应的操作
                switch ((int)rating) {
                    case 1:
                        textView.setText("当前的用户评价：太糟了");
                        break;
                    case 2:
                        textView.setText("当前的用户评价：不太好");
                        break;
                    case 3:
                        textView.setText("当前的用户评价：不错哦");
                        break;
                    case 4:
                        textView.setText("当前的用户评价：很好哦");
                        break;
                    case 5:
                        textView.setText("当前的用户评价：太棒了");
                        break;
                    default:
                        break;
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.button_submit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_evaluation.this);
                dialog.setTitle("提示");
                dialog.setMessage("您确定提交您的评价吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { //progress5
                        String finishtime = TimeUtils.getNowTime();

                        //更新任务信息

                        try{
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            con = DbUtils.getConn();

                            Statement st = con.createStatement();


                                String sql1 = "UPDATE `task` SET `score`= '"+ratingBar.getRating()+"' WHERE `preciseLaunchTime` = '"+task.getPreciseLaunchTime()+"'";
                                st.executeUpdate(sql1);
                                task.setScore(ratingBar.getRating());
                                String sql2 = "UPDATE `task` SET `progress`= '5' WHERE `preciseLaunchTime` = '"+task.getPreciseLaunchTime()+"'";
                                st.executeUpdate(sql2);
                                task.setProgress(5);
                                String sql3 = "UPDATE `task` SET `StatusText`= '任务已结束' WHERE `preciseLaunchTime` = '"+task.getPreciseLaunchTime()+"'";
                                st.executeUpdate(sql3);
                                task.setStatusText("任务已结束");
                                String sql4 = "UPDATE `task` SET `finishtime`= '"+finishtime+"' WHERE `preciseLaunchTime` = '"+task.getPreciseLaunchTime()+"'";
                                st.executeUpdate(sql4);
                                task.setFinishtime1(finishtime);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            if (con != null)
                                try {
                                    con.close();
                                } catch (SQLException e) {
                                }

                        }

                        //更新帮助者平均分

                        try{
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            con = DbUtils.getConn();

                            Statement st = con.createStatement();

                            //helper
                            rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '"+task.getHelperName()+"'");

                            while (rs.next()) {
                                float newScore = ratingBar.getRating();
                                float averageScore = rs.getFloat("averageScore");
                                int taskRNum = rs.getInt("taskRNum");

                                    if(averageScore == 0){
                                        averageScore = averageScore + newScore;
                                    }else {
                                        averageScore = (averageScore * (taskRNum - 1) + newScore) / taskRNum;
                                    }

                                String sql1 = "UPDATE `user` SET `averageScore`= '"+averageScore+"' WHERE myName='"+task.getHelperName()+"'";
                                st.executeUpdate(sql1);

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


                        /*
                        task.setScore(ratingBar.getRating());
                        task.setProgress(5);
                        task.setFinishtime(); //任务结束
                        task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                task.getLauncherName());

                        task.updateTaskStatus();
                        task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                task.getLauncherName());

                        List<user> userList = DataSupport.where("myName = ?",task.getHelperName()).find(user.class);
                        user helper = userList.get(0);
                        Log.d("helperName",helper.getMyName());

                        helper.addToAverageScore(ratingBar.getRating());
                        //给接收者一个提醒
                        if(!helper.getMsgTaskList().contains(task)) {
                            helper.addMsgTaskList(task.getPreciseLaunchTime());
                            helper.addMsg();
                            helper.updateAll("phonenumber = ?",helper.getPhonenumber());
                            Log.d("评价->接收者",helper.getMyName()
                                    +" "+String.valueOf(helper.getMsg())+" "+String.valueOf(helper.
                                    getMsgTaskList().size()));
                        }
                        helper.updateAll("phonenumber = ?",helper.getPhonenumber()); */

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

                        Intent intent = new Intent(MyActivity_evaluation.this, MyActivity_Task_Details.class);
                        intent.putExtra("user_phone", myPhone);
                        intent.putExtra("task_selected",task);
                        intent.putExtra("num",num);
                        startActivity(intent);


                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });


    }



    //未添加返回值

}
