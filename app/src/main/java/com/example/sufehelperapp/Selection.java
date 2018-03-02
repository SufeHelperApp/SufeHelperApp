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
import android.widget.AdapterView;
import android.widget.Spinner;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Selection extends AppCompatActivity {

    private user user;

    private String subtaskType;
    private String payment;

    private int position1=0;
    private int position2=0;

    private String pay1string = "0";
    private String pay2string = "10000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Selection",myName);

        final Spinner subtaskView = (Spinner) findViewById(R.id.selection_subtask);
        final String[] subtaskTypes = getResources().getStringArray(R.array.subtasks_errand);

        final Spinner paymentView = (Spinner) findViewById(R.id.selection_payment);
        final String[] payments = getResources().getStringArray(R.array.payments);

        final Spinner ddlView = (Spinner) findViewById(R.id.selection_ddl);


        List<task> tasks = DataSupport.findAll(task.class);
        for(task task:tasks) {
            task.checkWithin();
            task.updateTaskStatus();
            task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                    task.getLauncherName());
        }


        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("choose","selection1");
                subtaskType = subtaskTypes[position];
                Log.d("subtaskType",subtaskType);
                position1 = position;
                Log.d("position1",String.valueOf(position1));
                if(position == 0){
                    List<task> taskList1 = new ArrayList<>();
                    taskList1 = DataSupport
                            .where("payment >= ? and payment <= ?",
                                     pay1string, pay2string)
                            .find(task.class);
                    Log.d("tasknum",String.valueOf(taskList1.size()));
                    TaskAdapter adapter1 = new TaskAdapter(taskList1,user);
                    RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager1 = new GridLayoutManager(Selection.this,1);
                    recyclerView1.setLayoutManager(layoutManager1);
                    recyclerView1.setAdapter(adapter1);

                }else{
                    List<task> taskList1 = new ArrayList<>();
                    taskList1 = DataSupport
                            .where("subtaskType = ? and payment >= ? and payment <= ?",
                                    subtaskType, pay1string, pay2string)
                            .find(task.class);
                    Log.d("tasknum",String.valueOf(taskList1.size()));

                    TaskAdapter adapter1 = new TaskAdapter(taskList1,user);
                    RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager1 = new GridLayoutManager(Selection.this,1);
                    recyclerView1.setLayoutManager(layoutManager1);
                    recyclerView1.setAdapter(adapter1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        paymentView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("choose","selection2");
                position2 = position;
                Log.d("position2",String.valueOf(position2));
                payment = payments[position];
                Log.d("payment",payment);

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

                if(position1 == 0){

                    List<task> taskList1 = new ArrayList<>();
                    taskList1 = DataSupport.
                            where("payment >= ? and payment <= ?", pay1string, pay2string)
                            .find(task.class);
                    Log.d("tasknum",String.valueOf(taskList1.size()));
                    TaskAdapter adapter1 = new TaskAdapter(taskList1,user);
                    RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager1 = new GridLayoutManager(Selection.this,1);
                    recyclerView1.setLayoutManager(layoutManager1);
                    recyclerView1.setAdapter(adapter1);

                }else{
                    List<task> taskList1 = new ArrayList<>();
                    Log.d("subtask in view2",subtaskType);

                    Log.d("pay1",pay1string);
                    Log.d("pay2",pay1string);


                    taskList1 = DataSupport
                            .where("subtaskType = ? and payment >= ? and payment <= ?",
                                    subtaskType, pay1string, pay2string)
                            .find(task.class);

                    Log.d("tasknum",String.valueOf(taskList1.size()));

                    TaskAdapter adapter1 = new TaskAdapter(taskList1,user);
                    RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager1 = new GridLayoutManager(Selection.this,1);
                    recyclerView1.setLayoutManager(layoutManager1);
                    recyclerView1.setAdapter(adapter1);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}




