package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlTest3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test3);
        onClickQuery();
    }

    public void onClickQuery()
    {
    //在android中操作数据库最好在子线程中执行，否则可能会报异常
        new Thread()
        {
            public void run() {
                try {
                    //注册驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://10.64.22.26:3306/";
                    Connection conn = DriverManager.getConnection(url, "root", "root");
                    Statement stmt = conn.createStatement();
                    String sql = "select * from user";
                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        Log.v("yzy", "field1-->"+rs.getString(1)+"  field2-->"+rs.getString(2));
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                    Log.v("yzy", "success to connect!");
                }catch(ClassNotFoundException e)
                {
                    Log.v("yzy", "fail to connect1!"+"  "+e.getMessage());
                } catch (SQLException e)
                {
                    Log.v("yzy", "fail to connect2!"+"  "+e.getMessage());
                }
            };
        }.start();

    }
}
