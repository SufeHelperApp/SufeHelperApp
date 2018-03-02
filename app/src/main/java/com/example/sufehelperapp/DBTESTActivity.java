package com.example.sufehelperapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

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
                user user = new user();
                user.setDormitoryLocation("第一宿舍");
                user.updateAll();
                Toast.makeText(DBTESTActivity.this, "任务修改成功！", Toast.LENGTH_SHORT).show();
            }
        });


        Button select = (Button) findViewById(R.id.button_select);
        final TextView text = (TextView) findViewById(R.id.text_db_test);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<task> taskList = DataSupport.findAll(task.class);
                Log.d("taskNum",String.valueOf(taskList.size()));
                for(task task: taskList){
                    Log.d("task",task.getSubtaskType());
                }
                Toast.makeText(DBTESTActivity.this, "任务筛选成功！", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
