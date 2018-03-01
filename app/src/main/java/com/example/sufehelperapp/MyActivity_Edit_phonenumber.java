package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyActivity_Edit_phonenumber extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_phonenumber);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("Edit_phonenumber",user.getMyName());

        //TODO: delete
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
                intent.putExtra("user_now", user);
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

                        user.setPhonenumber(newPhonenumber);
                        user.save();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_phonenumber.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("手机号修改成功！");
                        //dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(MyActivity_Edit_phonenumber.this, MyActivity_Setup_Edit.class);
                                Log.d("MyActivity_Setup_Edit",user.getMyName());
                                intent.putExtra("user_now", user);
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
