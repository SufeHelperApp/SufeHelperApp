package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class DBTESTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        //接受user
        user user = (user) getIntent().getSerializableExtra("user_data");
        String myName = user.getMyName();

        Button delete = (Button) findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(task.class);
                Toast.makeText(DBTESTActivity.this, "任务删除成功！", Toast.LENGTH_SHORT).show();
            }
        });

        Button change = (Button) findViewById(R.id.button_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task task = new task();
                task.setLauncherImageId(R.drawable.apple);
                task.setSubtaskType("占座");
                task.setLauncherPhoneNumber("13912341234");
                task.setIfDefault(true);
                task.updateAll();
                Toast.makeText(DBTESTActivity.this, "任务修改成功！", Toast.LENGTH_SHORT).show();
            }
        });


        Button select = (Button) findViewById(R.id.button_select);
        final TextView text = (TextView) findViewById(R.id.text_db_test);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = " ";
                List<task> tasks = DataSupport.where("helperName = ?", "sophia")
                        .where("ifDefault = ? ","1").find(task.class);
                for(task task:tasks){
                    a = a + task.getLauncherName() + " ";
                }
                text.setText(a);
                Toast.makeText(DBTESTActivity.this, "任务筛选成功！", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
