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
import java.sql.SQLException;
import java.sql.Statement;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class My_LoginSecondActivity extends AppCompatActivity {

    private Connection con;
    private ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);

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
                //TODO：查找数据库中是否有用户对应上面两行的name和password

                    try{
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();
                        Statement st = con.createStatement();
                        rs= st.executeQuery("SELECT * FROM `user` WHERE `name` = '"+name+"' AND `pass` = '"+password+"'");

                        if (rs.wasNull()){
                            Toast.makeText(My_LoginSecondActivity.this, "用户名或密码错误！",
                                Toast.LENGTH_SHORT).show();
                        }else {

                            String result = "";
                            while (rs.next()) {
                                result += rs.getString(1);
                            }

                            String userName = result;
                            String txt = "欢迎回来, "+userName+"!";

                            Intent intent = new Intent(My_LoginSecondActivity.this, Task_HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(My_LoginSecondActivity.this,txt,
                                    Toast.LENGTH_SHORT).show();

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
        });

    }
}
