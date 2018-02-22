package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity_Edit_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_password);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_password.this, MyActivity_Setup_Edit.class);
                startActivity(intent);
            }
        });

        final TextView passwordView = (TextView) findViewById(R.id.edit_text_password);
        Button button2 = (Button) findViewById(R.id.button_conserve_password);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.button_conserve_password:
                        String newPassword = passwordView.getText().toString();
                        //TODO: 修改密码：1.获取当前user对象 2.保存修改
                        //user user1 = new user();
                        //user1.setPassword(newPassword);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_password.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("昵称修改成功！"); //TODO:改错：密码
                        //dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(MyActivity_Edit_password.this, MyActivity_Setup_Edit.class);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
