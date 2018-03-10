package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class My_LoginSecondActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private user user;

    /*
    private static final String url = "jdbc:mysql://101.94.5.73:3306/sufehelper";
    private static final String user ="test123";
    private static final String pass = "1234";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);

        dbHelper = new MyDatabaseHelper(this,"USER.db",null,1);
        LitePal.getDatabase();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button buttonback = (Button) findViewById(R.id.title_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_LoginSecondActivity.this, My_LoginFirstActivity.class);
                startActivity(intent1);
            }
        });
        Button button1 = (Button) findViewById(R.id.button_3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(My_LoginSecondActivity.this, My_LoginThirdActivity.class);
                startActivity(intent2);
            }
        });

        Button button2 = (Button) findViewById(R.id.button_4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView nameView = findViewById(R.id.login_second_name);
                TextView passwordView = findViewById(R.id.login_second_password);
                String name = nameView.getText().toString();
                String password = passwordView.getText().toString();

                List<user> userList = DataSupport.where("myName = ? and password = ?",
                        name,password).find(user.class);

                /*

                try{
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con =DriverManager.getConnection(url, user, pass);

                    String result = "Database connection success\n";
                    Statement st = con.createStatement();
                    ResultSet rs= st.executeQuery(" select * from user where myName = "+name+" ");
                    //user_now = rs;
                    ResultSetMetaData rsmd = rs.getMetaData();

                    while(rs.next()){
                        result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                        result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                        result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                        result += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
                    }
                    //t1.setText(result);


                }catch (Exception e){
                    e.printStackTrace();
                    //t1.setText(e.toString());
                }

                */


                if(userList.isEmpty()){
                    Toast.makeText(My_LoginSecondActivity.this, "用户名或密码错误！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    user user = userList.get(0);
                    if(user.getIfClicked()){
                        user.clearMsg();
                        user.updateAll("phonenumber = ? and myName = ?",user.getPhonenumber(),
                                user.getMyName());
                    }
                    String myName = user.getMyName();
                    String txt = "欢迎回来, "+user.getMyName()+"!";
                    Log.d("log:msg",String.valueOf(user.getMsg()));

                    Intent intent = new Intent(My_LoginSecondActivity.this, Task_HomeActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                    Toast.makeText(My_LoginSecondActivity.this,txt,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
