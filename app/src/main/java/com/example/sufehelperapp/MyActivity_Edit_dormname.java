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


public class MyActivity_Edit_dormname extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__edit_dormname);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("Edit_dormname",user.getMyName());

        //TODO: delete
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_dormname.this, MyActivity_Setup_Edit.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });
        final TextView dormnameView = (TextView) findViewById(R.id.edit_text_changedormname);
        Button button2 = (Button) findViewById(R.id.button_conserve_dormname);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.button_conserve_dormname:
                        String newDormname = dormnameView.getText().toString();

                        user.setDormitoryLocation(newDormname);
                        user.save();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_dormname.this);
                        dialog.setTitle("提示");
                        dialog.setMessage("寝室名称修改成功！");
                        //dialog.setCancelable(false);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(MyActivity_Edit_dormname.this, MyActivity_Setup_Edit.class);
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
