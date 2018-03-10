package com.example.sufehelperapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class mysqlTest extends AppCompatActivity {

    private static final String url = "jdbc:mysql://101.94.5.73:3306/sufehelper";
    private static final String user ="test123";
    private static final String pass = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test);

        testDB();

    }


    public void testDB(){
        TextView t1= findViewById(R.id.t1);
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con =DriverManager.getConnection(url, user, pass);

            String result = "Database connection success\n";
            Statement st = con.createStatement();
            ResultSet rs= st.executeQuery("select * from user");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
            }
            t1.setText(result);


        }catch (Exception e){
            e.printStackTrace();
            t1.setText(e.toString());
        }
    }
}
