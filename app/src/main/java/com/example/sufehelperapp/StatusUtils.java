package com.example.sufehelperapp;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sophiawen on 2018/4/19.
 */

public class StatusUtils {

    public static void updateAllTaskStatus() {

        List<task> taskAllList = new ArrayList<>();

        try {
            Connection con = DbUtils.getConn(); //initialize connection
            Statement st = con.createStatement(); //initialize connection
            ResultSet rs = st.executeQuery("SELECT * FROM `task` WHERE `ifShutDown` = '0'");

            List list = DbUtils.populate(rs, task.class);
            for (int i = 0; i < list.size(); i++) {
                taskAllList.add((task) list.get(i));
            }

            Log.d("select:updateAllTask",String.valueOf(taskAllList.size()));

            for (task task : taskAllList) {

                String id = task.getPreciseLaunchTime();

                if (task.getProgress() < 3) {

                    if (TimeUtils.isDateOneBigger(TimeUtils.getNowTime(), task.getDdl())) {
                        //接收者未及时完成，关闭项目
                        if (task.getIfAccepted()) {
                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '1' " +
                                    ", `ifShutDown` = '1', `progress` = '7', `statusText` = '接受者违约' " +
                                    "WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);


                            //给发布者一个提醒
                            String launcherName = task.getLauncherName();
                            try{
                                StrictMode.ThreadPolicy policy =
                                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                con = DbUtils.getConn();

                                st = con.createStatement();


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

                            //给接收者一个提醒
                            String helperName = task.getHelperName();
                            try{
                                StrictMode.ThreadPolicy policy =
                                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                con = DbUtils.getConn();

                                st = con.createStatement();


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


                        } else {

                            //过期未接收，关闭项目

                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '1' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '1', `progress` = '6', `statusText` = '逾期未被接收' " +
                                    "WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);


                            //给发布者一个提醒

                            String launcherName = task.getLauncherName();
                            try{
                                StrictMode.ThreadPolicy policy =
                                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                con = DbUtils.getConn();

                                st = con.createStatement();


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

                        }

                    } else {
                        if (task.getIfAccepted()) {

                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);

                        } else {

                            String sql ="UPDATE `task` SET `ifDisplayable` = '1' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);

                        }
                    }
                } else if (task.getProgress() == 3) {

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '已完成,待支付' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);


                } else if (task.getProgress() == 4) {

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '待评价' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);

                } else if (task.getProgress() == 5) {
                    //评论完成，关闭任务

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '1', `statusText` = '任务已结束' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);

                }

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
