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

public class MyActivity_Edit_phonenumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_phonenumber);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
                startActivity(intent);
            }
        });
        final TextView phonenumberView = (TextView) findViewById(R.id.edit_text_phonenumber);
        Button button2 = (Button) findViewById(R.id.button_conserve_phonenumber);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.button_conserve_phonenumber:
                        String newPhonenumber = phonenumberView.getText().toString();
                        //TODO: 修改手机：1.获取当前user对象 2.保存修改
                        //user user1 = new user();
                        //user1.setPhonenumber(newPhonenumber);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_phonenumber.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("手机号修改成功！");
                        //dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
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
