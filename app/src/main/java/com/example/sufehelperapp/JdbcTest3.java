package com.example.sufehelperapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest3 extends AppCompatActivity {

    String url= "jdbc:mysql://localhost:3306/itask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdbc_test3);
        new Thread(runnable).start();
    }
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle data = new Bundle();
            data=msg.getData();
            Log.d("msg","myName" + data.get("myName").toString());
            Log.d("msg","password" + data.get("password").toString());
        }
    };
    Runnable runnable = new Runnable() {
        private Connection connection = null;

        @Override
        public void run() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, "root", "root");
                System.out.print("连接成功");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                test(connection);    //测试数据库连接
            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void test(Connection con1) throws java.sql.SQLException {
            try {
                String sql = "select * from user";        //查询表名为“user”的所有内容
                Statement stmt = con1.createStatement();        //创建Statement
                ResultSet rs = stmt.executeQuery(sql);          //ResultSet类似Cursor

                //<code>ResultSet</code>最初指向第一行
                Bundle bundle = new Bundle();
                while (rs.next()) {
                    bundle.clear();
                    bundle.putString("myName", rs.getString("myName"));
                    bundle.putString("password", rs.getString("password"));
                    Message msg = new Message();
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {

            } finally {
                if (con1 != null)
                    try {
                        con1.close();
                    } catch (SQLException e) {
                    }
            }
        }
    };
};
