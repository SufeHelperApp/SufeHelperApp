package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyActivity_History_Recieved extends Fragment implements View.OnClickListener {

    private String myPhone;

    Connection con;
    ResultSet rs;

    private user user;
    private Bundle bundle;
    private List<task> taskList = new ArrayList<>();

    @Nullable
    private TaskAdapter adapter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_history_recieved, container, false);

        bundle = getArguments();
        myPhone = bundle.getString("user_phone");

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

        updateAllTaskStatus();

        //TODO:获得接收者为当前用户，ifShutDown = true 的所有任务
        /*
        taskList = DataSupport.where("helperName = ? and ifShutDown = ?", user.getMyName(), "1")
                .find(task.class);*/

        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st1 = con.createStatement();
            rs = st1.executeQuery("SELECT * FROM `task` WHERE `helperName` = '"+user.getMyName()+"' AND `ifShutDown` = '1'");
            List list = DbUtils.populate(rs, task.class);

            if (list.isEmpty()) {

            } else {

                try {

                    for (int i = 0; i < list.size(); i++) {
                        taskList.add((task) list.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_recieved_recycler);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new TaskAdapter(taskList, user, 3);
            recyclerView.setAdapter(adapter);

            return view;


    }
  
    @Override
    public void onClick(View view) {

    }

    public void updateAllTaskStatus() {

        List<task> taskAllList = new ArrayList<>();

        try {
            con = DbUtils.getConn(); //initialize connection
            Statement st = con.createStatement(); //initialize connection
            rs = st.executeQuery("SELECT * FROM `task` WHERE `ifShutDown` = '0'");

            List list = DbUtils.populate(rs, task.class);
            for (int i = 0; i < list.size(); i++) {
                taskAllList.add((task) list.get(i));
            }

            Log.d("select:updateAllnum", String.valueOf(taskAllList.size()));

            for (task task : taskAllList) {

                String id = task.getPreciseLaunchTime();

                if (task.getProgress() < 3) {
                    if (TimeUtils.isDateOneBigger(TimeUtils.getNowTime(), task.getDdl())) {
                        if (task.getIfAccepted()) {
                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '1' " +
                                    ", `ifShutDown` = '1', `progress` = '7', `statusText` = '接受者违约' " +
                                    "WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);
                            /*
                            task.setIfDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = true;
                            task.ifShutDown = true; //接收者未及时完成，关闭项目
                            task.setProgress(7);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());
                                    */

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);
                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("违约->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }

                            //给接收者发一条消息
                            List<user> helperList = DataSupport.where("myName = ?", task.getHelperName())
                                    .find(user.class);
                            user helper = helperList.get(0);

                            if (!helper.getMsgTaskList().contains(task)) {
                                helper.addMsg();
                                helper.addMsgTaskList(task.getPreciseLaunchTime());
                                helper.updateAll("phonenumber = ?", helper.getPhonenumber());
                                Log.d("违约->接收者", helper.getMyName()
                                        + " " + String.valueOf(helper.getMsg()) + " " + String.valueOf(helper.
                                        getMsgTaskList().size()));
                            }

                            */


                        } else {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = true;
                            task.ifDefault = false;
                            task.ifShutDown = true; //过期未接收，关闭项目
                            task.setProgress(6);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '1' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '1', `progress` = '6', `statusText` = '逾期未被接收' " +
                                    "WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);

                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("过期->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }
                            */


                        }
                    } else {
                        if (task.getIfAccepted()) {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                        } else {
                            /*
                            task.ifDisplayable = true;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql = "UPDATE `task` SET `ifDisplayable` = '1' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '" + id + "'";
                            st.executeUpdate(sql);

                        }
                    }
                } else if (task.getProgress() == 3) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '已完成,待支付' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);


                } else if (task.getProgress() == 4) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '待评价' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);

                } else if (task.getProgress() == 5) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = true;  //评论完成，关闭任务
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql = "UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '1', `statusText` = '任务已结束' WHERE `preciseLaunchTime` = '" + id + "'";
                    st.executeUpdate(sql);

                }
        /*
                task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                        task.getLauncherName());*/

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
