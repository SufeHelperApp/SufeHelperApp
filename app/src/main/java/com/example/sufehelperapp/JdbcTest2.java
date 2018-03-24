package com.example.sufehelperapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest2 extends AppCompatActivity {

    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdbc_test2);

        JdbcTest();
    }



    public void JdbcTest(){

        /*

        //failed

        //try3: src: youtube ; features: 1.thread policy 2.show result in activity

        String url= "jdbc:mysql://localhost:3306/itask";

        TextView t1 = findViewById(R.id.t1);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch(java.lang.ClassNotFoundException e) {
                System.err.print("ClassNotFoundException: ");
                System.err.println(e.getMessage());
            }

            Connection con = DriverManager.getConnection(url, "root", "root");

            String result = "Database connection success\n";
            Log.d("db conn status","result");
            Statement st = con.createStatement();
            String sql = "INSERT INTO USER " +
                    "VALUES('13412341234','1234','秦六','男')";
            st.executeUpdate(sql);

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            t1.setText(e.toString());
        }

        */

        /*
        //failed 2

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url= "jdbc:mysql://localhost:3306/itask";
        Connection con;
        String sql;
        Statement stmt;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        try {
            con = DriverManager.getConnection(url, "root", "root"); //
            stmt = con.createStatement();
//向student表中插入一行记录。
            sql = "INSERT INTO USER " +
                    "VALUES('13412341234','1234','秦六','男')";
            stmt.executeUpdate(sql);

//关闭连接。
            stmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        */


       //failed 1

        new Thread()
        {
            public void run() {
                /*
                String dbName = "itask";
                String dbUserName = "root";
                String dbPassword = "root";

                String url =
                        "jdbc:mysql://localhost/" +
                                dbName + "?user=" + dbUserName + "&password=" + dbPassword +
                                "&useUnicode=true&characterEncoding=UTF-8";

                String url =
                        "jdbc:mysql://localhost:3306/itask?user=root&password=root&useUnicode=true&characterEncoding=UTF-8";
                */

                url= "jdbc:mysql://localhost:3306/itask";
                Connection con;
                String sql;
                Statement stmt;

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch(java.lang.ClassNotFoundException e) {
                    System.err.print("ClassNotFoundException: ");
                    System.err.println(e.getMessage());
                }
                try {
                    con = DriverManager.getConnection(url, "testuser", "1234"); //
                    stmt = con.createStatement();
//向student表中插入一行记录。
                    sql = "INSERT INTO USER " +
                            "VALUES('13412341234','1234','秦六','男')";
                    stmt.executeUpdate(sql);

//关闭连接。
                    stmt.close();
                    con.close();
                } catch(SQLException ex) {
                    System.err.println("SQLException: " + ex.getMessage());
                }
            };
        }.start();

    }

}
