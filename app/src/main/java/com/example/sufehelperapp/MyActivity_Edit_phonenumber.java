package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity_Edit_phonenumber extends AppCompatActivity {

    private user user;

    private String myPhone;

    Connection con;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_phonenumber);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });
        final TextView phonenumberView = (TextView) findViewById(R.id.edit_text_phonenumber);
        Button button2 = (Button) findViewById(R.id.button_conserve_phonenumber);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPhonenumber = phonenumberView.getText().toString();

                try{
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    con = DbUtils.getConn();
                    Statement st1 = con.createStatement();
                    rs= st1.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+newPhonenumber+"'");

                    List list = DbUtils.populate(rs,user.class);

                    if (!list.isEmpty()) {
                        Toast.makeText(MyActivity_Edit_phonenumber.this, "手机已被注册！",
                                Toast.LENGTH_SHORT).show();
                    }


                    if(list.isEmpty() && !newPhonenumber.isEmpty()){

                        //TODO: 将newPhonenumber存入当前用户
                        Statement st2 = con.createStatement();
                        st2.executeUpdate("UPDATE `user` SET `phonenumber` = '"+newPhonenumber+"' WHERE `phonenumber` = '"+myPhone+"'");


                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_phonenumber.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("手机修改成功！");
                        //dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
                                intent.putExtra("user_phone", myPhone);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                    }

                    con.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }
}
