package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DBTESTActivity extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        user = (user) getIntent().getSerializableExtra("user_now");
        final String myName = user.getMyName();
        Log.d("DBTESTActivity",myName);

        final TextView text = (TextView) findViewById(R.id.text_db_test);

        Button delete = (Button) findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(task.class);
                DataSupport.deleteAll(user.class);
                Toast.makeText(DBTESTActivity.this, "任务删除成功！", Toast.LENGTH_SHORT).show();
            }
        });

        Button change = (Button) findViewById(R.id.button_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    user.setMyName("amy3");
                    user.updateAll("phonenumber = ?",user.getPhonenumber());

                    text.setText(String.valueOf(user.getMyName()));

                Toast.makeText(DBTESTActivity.this, "任务修改成功！", Toast.LENGTH_SHORT).show();
            }
        });


        Button select = (Button) findViewById(R.id.button_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<user> userList = DataSupport.findAll(user.class);
                for(user user:userList){
                    Log.d("name",user.getMyName());
                    Log.d("msg num",String.valueOf(user.getMsg()));
                }

                Toast.makeText(DBTESTActivity.this, "任务筛选成功！", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DBTESTActivity.this, Task_HomeActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DBTESTActivity.this, Task_HomeActivity.class);
        intent.putExtra("user_now",user);
        startActivity(intent);
        finish();
    }


}
