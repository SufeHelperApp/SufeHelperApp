package com.example.sufehelperapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class mysqlTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test);

        testDB1();
        //testDB2();

    }


    public void testDB1() {
        TextView t1 = findViewById(R.id.t1);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection con = DbUtils.getConn();

            String result = "Database connection success\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user"); //TODO: check
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                result += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
            }
            t1.setText(result);


        } catch (Exception e) {
            e.printStackTrace();
            t1.setText(e.toString());
        }
    }




    public void testDB2() {

        String[] strings = {"Main","testDB2"};

        try {
            Main.main(strings);
        }catch(InstantiationException | IllegalAccessException | IllegalArgumentException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        /*
        Connection conn = DbUtils.getConn();
        ResultSet rs = null;
        PreparedStatement psmt = null;
        System.out.println(conn);
        String name = "sophia";
        try {
            psmt = conn.prepareStatement(" select * from user where myName = " + name + " ");
            rs = psmt.executeQuery();
            List list = DbUtils.populate(rs,user.class);
            user per = (user) list.get(0);
            System.out.println("person : myName = " + per.getMyName() + " password = " + per.getPassword() + " phonenumber = " + per.getPhonenumber());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rs = null;
            }
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                psmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        */

    }

}
