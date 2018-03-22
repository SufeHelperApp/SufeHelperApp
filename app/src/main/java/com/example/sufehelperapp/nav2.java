package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class nav2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sqltest1);

        Log.d("msg","mysqltest");

        String url= "jdbc:mysql57://localhost:3306/myuser";
        Connection con;
        String sql;
        Statement stmt;
        String phone,pass,name,sex;

        Log.d("msg","123");

        TextView textView = findViewById(R.id.mysqlTest1_text);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        try {
            Log.d("msg","before conn");
            con = DriverManager.getConnection(url, "root", "root"); //get con
            Log.d("msg","after conn");
            stmt = con.createStatement(); //get stmt

            sql = "INSERT INTO MYUSER " +
                    "VALUES('13912341234','1234','王五'，'女')";
            stmt.executeUpdate(sql); //stmt: update
            Log.d("msg","after insertion");


            sql = " SELECT * FROM MYUSER";
            ResultSet rs = stmt.executeQuery(sql); //stmt: query
            String result = " ";
            System.out.println("手机 密码 姓名"+
                    " 性别");
            while(rs.next()){
                phone = rs.getString(1);
                pass = rs.getString(2);
                name = rs.getString(3);
                sex = rs.getString(4);
                result = result + ";"+(phone+" "+pass+" "+name+" "+sex);
            }
            textView.setText(result);


            sql="SELECT phonenumber,password,myName,sex "+
                    "FROM MYUSER "+
                    "WHERE sex='女'";
            rs = stmt.executeQuery(sql);
            System.out.println();
            System.out.println("The users who are female are:");
            while(rs.next()){
                phone = rs.getString(1);
                pass = rs.getString(2);
                name = rs.getString(3);
                sex = rs.getString(4);
                System.out.println("学号="+phone+" "+"密码="+pass+" "+"姓名="+
                        name+" "+"性别="+sex);
            }
            //关闭连接。
            stmt.close();
            con.close();
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

    }

}
