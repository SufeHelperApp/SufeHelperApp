package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class My_RegisterFirstActivity extends AppCompatActivity {

    Connection con;
    ResultSet rs;
    int imageReso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterFirstActivity.this, My_LoginFirstActivity.class);
                startActivity(intent1);
            }
        });

        // 点击“确认注册”按钮，新建用户对象，设置手机号

        Button button2 = (Button) findViewById(R.id.button_7);
        final TextView phoneView = findViewById(R.id.edit_text3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneView.getText().toString();
                //TODO: 检查数据库中是否已经有用户的手机与phone相同
                if(phone.isEmpty())
                {
                    Toast.makeText(My_RegisterFirstActivity.this, "手机号不得为空！",
                            Toast.LENGTH_SHORT).show();
                }else {

                    try{
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();
                        Statement st1 = con.createStatement();
                        rs= st1.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+phone+"'");
                        List list = DbUtils.populate(rs,user.class);

                        if (!list.isEmpty()) {
                            Toast.makeText(My_RegisterFirstActivity.this, "手机号已经存在！",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Random random = new Random();
                            int index = random.nextInt(5);

                            switch(index){
                                case 0:imageReso = R.drawable.df1;break;
                                case 1:imageReso = R.drawable.df2;break;
                                case 2:imageReso = R.drawable.df3;break;
                                case 3:imageReso = R.drawable.df4;break;
                                case 4:imageReso = R.drawable.df5;break;
                            }


                            String sql = "INSERT INTO `user`" +
                                    "(`myName`, `password`, `phonenumber`, `sex`, `credit`, `dormArea`, `taskLNum`, `taskNum`, " +
                                    "`isValid`, `myImageId`, `demandString`, `specialtyString`, `taskRNum`, `taskRNum_errand`, " +
                                    "`taskRNum_e1`, `taskRNum_e2`, `taskRNum_e3`, `taskRNum_e4`, `taskRNum_e5`, `taskRNum_skill`, " +
                                    "`taskRNum_s1`, `taskRNum_s2`, `taskRNum_s3`, `taskRNum_s4`, `taskRNum_s5`, `taskRNum_counsel`," +
                                    " `taskRNum_c1`, `taskRNum_c2`, `taskRNum_c3`, `taskRNum_c4`, `taskRNum_c5`, `default_taskNum`," +
                                    " `averageScore`, `level`, `ifTalent`, `talentTitlesString`, `msg`, `msgTaskListString`) " +
                                    "VALUES ('','','"+phone+"','','0','','0','0','1','"+imageReso+"','000000000000000','000000000000000','0'," +
                                    "'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1','0'," +
                                    "'000000000000000','0','')";

                            Statement st2 = con.createStatement();
                            st2.executeUpdate(sql);

                            /*

                            String sql = "UPDATE `user` " +
                                    "SET `myName`='',`password`='',`phonenumber`='',`sex`='',`credit`='0',`dormArea`='',`taskLNum`='0'," +
                                    "`taskNum`='0',`isValid`='1',`myImageId`='"+imageReso+"',`demandString`='000000000000000'," +
                                    "`specialtyString`='000000000000000',`taskRNum`='0',`taskRNum_errand`='0',`taskRNum_e1`='0'," +
                                    "`taskRNum_e2`='0',`taskRNum_e3`='0',`taskRNum_e4`='0',`taskRNum_e5`='0',`taskRNum_skill`='0'," +
                                    "`taskRNum_s1`='0',`taskRNum_s2`='0',`taskRNum_s3`='0',`taskRNum_s4`='0',`taskRNum_s5`='0'," +
                                    "`taskRNum_counsel`='0',`taskRNum_c1`='0',`taskRNum_c2`='0',`taskRNum_c3`='0',`taskRNum_c4`='0'," +
                                    "`taskRNum_c5`='0',`default_taskNum`='0',`averageScore`='0',`level`='1',`ifTalent`='0'," +
                                    "`talentTitlesString`='000000000000000',`msg`='0',`msgTaskListString`='' WHERE 1";*/


                            Intent intent = new Intent(My_RegisterFirstActivity.this, My_RegisterSecondActivity.class);
                            intent.putExtra("user_phone", phone);
                            startActivity(intent);
                        }

                        con.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}

