package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task_SelectionEActivity extends AppCompatActivity {

    private user user;

    private List<task> taskList = new ArrayList<>();
    private TaskAdapter adapter;

    public String subtaskType;

    private int position1=0;
    private int position2=0;
    private int position3=0;

    private String pay1string = "0";
    private String pay2string = "10000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__selection_e);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("SelectionEActivity",myName);

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_SelectionEActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_SelectionEActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


        final Spinner subtaskView = (Spinner) findViewById(R.id.select_subtask);
        final Spinner paymentView = (Spinner) findViewById(R.id.select_payment);
        final Spinner ddlView = (Spinner) findViewById(R.id.select_ddl);

        List<task> tasks = DataSupport.findAll(task.class);
        for(task task:tasks) {
            task.updateTaskStatus();
            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                    task.getLauncherName());
        }

        //initialize RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_errand_select);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);


        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.d("msg","select");
                String[] subtaskTypes = getResources().getStringArray(R.array.subtasks_errand);
                subtaskType = subtaskTypes[pos];
                position1 = pos;
                if(position1==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("ifDisplayable = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtaskType)
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("ifDisplayable = ?","1").find(task.class);
                }
                if(!taskList.isEmpty()) {
                    //update adapter
                    adapter = new TaskAdapter(taskList,user,1);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

/*
        paymentView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] payments = getResources().getStringArray(R.array.payments);
                payment = payments[pos];
                position2 = pos;

                switch (payment) {
                    case "不限": {
                        pay1string = "0";
                        pay2string = "10000";
                        break;
                    }
                    case "0-5元": {
                        pay1string = "0";
                        pay2string = "5";
                        break;
                    }
                    case "6-10元": {
                        pay1string = "6";
                        pay2string = "10";
                        break;
                    }
                    case "11-15元": {
                        pay1string = "11";
                        pay2string = "15";
                        break;
                    }
                    case "15元以上": {
                        pay1string = "15";
                        pay2string = "10000";
                        break;
                    }
                }

                if(position1==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtaskType)
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ddlView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] ddls = getResources().getStringArray(R.array.ddls);
                position3 = pos;
                ddl = ddls[pos];

                //update ddls
                List<task> tasks1 = DataSupport.findAll(task.class);
                for(task task:tasks1) {
                    task.ifWithin(pos); //determine within
                    task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                            task.getLauncherName());

                }

                if(position1==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtaskType)
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
*/

    }
}
