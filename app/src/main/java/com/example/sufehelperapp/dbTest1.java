package com.example.sufehelperapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class dbTest1 extends AppCompatActivity {

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test1);
        testDB1();
        testDB2();
    }

    public void testDB1(){
        TextView t1 = (TextView) this.findViewById(R.id.t1);
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            String result = "Database connection success\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                result += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
            }
            t1.setText(result);

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
            t1.setText(e.toString());
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }
        }
    }

    public void testDB2(){

        TextView t2 = (TextView) this.findViewById(R.id.t2);
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            String result = "Database connection success\n";
            Statement st = con.createStatement();

            String sql = "INSERT INTO `user` (`name`, `pass`, `phone`, `sex`) VALUES ('琳达', '1234', '13912341234', '女')";
            st.executeUpdate(sql); //stmt: update

            ResultSet rs = st.executeQuery("select * from user");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                result += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
            }
            t2.setText(result);

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
            t2.setText(e.toString());
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }
        }

    }


}
