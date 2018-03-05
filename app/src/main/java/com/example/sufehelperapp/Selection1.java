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

public class Selection1 extends AppCompatActivity {

    List<task> taskList = new ArrayList<>();

    private user user;
    String[] subtaskTypes;

    private String subtaskType;
    private String area;
    private String payment;

    private int position1=0;
    private int position2=0;
    private int position3=0;
    private int position4=0;

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
        Log.d("Selection1",myName);


        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Selection1.this, Task_HomeActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Selection1.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Selection1.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        final Spinner subtaskView = (Spinner) findViewById(R.id.selection_subtask);
        final String[] subtaskTypes = getResources().getStringArray(R.array.subtasks_errand);

        final Spinner areaView = (Spinner) findViewById(R.id.selection_area);
        final String[] areas = getResources().getStringArray(R.array.areas);

        final Spinner paymentView = (Spinner) findViewById(R.id.selection_payment);
        final String[] payments = getResources().getStringArray(R.array.payments);

        final Spinner ddlView = (Spinner) findViewById(R.id.selection_ddl);
        final String[] ddls = getResources().getStringArray(R.array.ddls);



        task.updateAllTaskStatus();


        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                subtaskType = subtaskTypes[position];

                if(position1 == 0 && position2 == 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ?" +
                                            " and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string,"1")
                            .find(task.class);

                }else if(position1 != 0 && position2 == 0 ){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType, pay1string, pay2string,"1")
                            .find(task.class);

                }else if (position1 == 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string, area,"1")
                            .find(task.class);

                }else if (position1 != 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                            "and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType,pay1string, pay2string, area,"1")
                            .find(task.class);
                }

                List<task> demand;

                switch (position4){
                    case 0:
                        break;
                    case 1:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 2:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 3:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 4:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneWeek(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 5:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneMonth(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                }

                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection1.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        areaView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position2 = position;
                area = areas[position];


                if(position1 == 0 && position2 == 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string,"1")
                            .find(task.class);

                }else if(position1 != 0 && position2 == 0 ){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType, pay1string, pay2string,"1")
                            .find(task.class);

                }else if (position1 == 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string, area,"1")
                            .find(task.class);

                }else if (position1 != 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                            "and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType,pay1string, pay2string, area,"1")
                            .find(task.class);
                }

                List<task> demand;

                switch (position4){
                    case 0:
                        break;
                    case 1:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 2:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 3:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 4:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneWeek(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 5:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneMonth(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                }

                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection1.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        paymentView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position3 = position;
                payment = payments[position];


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

                if(position1 == 0 && position2 == 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string,"1")
                            .find(task.class);

                }else if(position1 != 0 && position2 == 0 ){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType, pay1string, pay2string,"1")
                            .find(task.class);

                }else if (position1 == 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string, area,"1")
                            .find(task.class);

                }else if (position1 != 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                            "and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType,pay1string, pay2string, area,"1")
                            .find(task.class);
                }

                List<task> demand;

                switch (position4){
                    case 0:
                        break;
                    case 1:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 2:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 3:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 4:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneWeek(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 5:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneMonth(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                }

                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection1.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ddlView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position4 = position;

                if(position1 == 0 && position2 == 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string,"1")
                            .find(task.class);

                }else if(position1 != 0 && position2 == 0 ){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType, pay1string, pay2string,"1")
                            .find(task.class);

                }else if (position1 == 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",pay1string, pay2string, area,"1")
                            .find(task.class);

                }else if (position1 != 0 && position2 != 0){

                    taskList = DataSupport
                            .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                            "and payment <= ? and area = ?" +
                                            "and ifDisplayable = ?",
                                    "跑腿",subtaskType,pay1string, pay2string, area,"1")
                            .find(task.class);
                }

                List<task> demand;

                switch (position4){
                    case 0:
                        break;
                    case 1:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeHour(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 2:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 3:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinThreeDay(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 4:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneWeek(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                    case 5:
                        demand = new ArrayList<>();
                        for(task task:taskList){
                            if(TimeUtils.isDateWithinOneMonth(task.getDdl())){
                                demand.add(task);
                            }
                        }
                        taskList = demand;
                        break;
                }

                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection1.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



}




