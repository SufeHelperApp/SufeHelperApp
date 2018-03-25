package com.example.sufehelperapp;

import android.content.Intent;
import android.os.StrictMode;
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
import android.widget.Button;
import android.widget.Spinner;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Selection3 extends AppCompatActivity {

    List<task> taskList = new ArrayList<>();

    private String myPhone;

    private Connection con;
    Statement st;
    private ResultSet rs;

    private user user;
    private int num;

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
        setContentView(R.layout.activity_selection3);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);
            Log.d("user",user.getMyName());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Selection3.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Selection3.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Selection3.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        final Spinner subtaskView = (Spinner) findViewById(R.id.selection_subtask);
        final String[] subtaskTypes = getResources().getStringArray(R.array.subtasks_counsel);

        final Spinner areaView = (Spinner) findViewById(R.id.selection_area);
        final String[] areas = getResources().getStringArray(R.array.areas);

        final Spinner paymentView = (Spinner) findViewById(R.id.selection_payment);
        final String[] payments = getResources().getStringArray(R.array.payments);

        final Spinner ddlView = (Spinner) findViewById(R.id.selection_ddl);
        final String[] ddls = getResources().getStringArray(R.array.ddls);


        updateAllTaskStatus();

        if(num == 0 ) {

            /*

            taskList = DataSupport
                    .where("taskType = ? and ifDisplayable = ?",
                            "跑腿", "1").find(task.class);*/

            try{

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                con = DbUtils.getConn(); //initialize connection
                st = con.createStatement(); //initialize connection
                rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' AND `ifDisplayable` = '1'");

                //List<task> sampleList = new ArrayList<>();

                List list = DbUtils.populate(rs,task.class);
                for(int i = 0; i < list.size(); i++){
                    taskList.add((task)list.get(i));
                }

                //taskList = sampleList;
                Log.d("msg",String.valueOf(taskList.size()));


                TaskAdapter adapter = new TaskAdapter(taskList,user,1);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this,1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        num++;


        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                subtaskType = subtaskTypes[position];

                if(num!=0) {

                    if (position1 == 0 && position2 == 0) {

                        /*
                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ?" +
                                                " and ifDisplayable = ?",
                                        "咨询", pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    } else if (position1 != 0 && position2 == 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    } else if (position1 == 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", pay1string, pay2string, area, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                                "and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, area, "1")
                                .find(task.class);*/
                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
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


                if(num!=0) {


                    if (position1 == 0 && position2 == 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ? and ifDisplayable = ?",
                                        "跑腿", pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0 && position2 == 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 == 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", pay1string, pay2string, area, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                                "and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, area, "1")
                                .find(task.class);*/
                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
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
                    case "报酬": {
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

                if (num != 0) {

                    if (position1 == 0 && position2 == 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ? and ifDisplayable = ?",
                                        "跑腿", pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();
                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0 && position2 == 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? and payment <= ? " +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, "1")
                                .find(task.class);*/

                        try{
                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    " AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 == 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and payment >= ? and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", pay1string, pay2string, area, "1")
                                .find(task.class);*/

                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (position1 != 0 && position2 != 0) {

                        /*

                        taskList = DataSupport
                                .where("taskType = ? and subtaskType = ? and payment >= ? " +
                                                "and payment <= ? and area = ?" +
                                                "and ifDisplayable = ?",
                                        "跑腿", subtaskType, pay1string, pay2string, area, "1")
                                .find(task.class);*/
                        try{

                            con = DbUtils.getConn();
                            st = con.createStatement();

                            rs= st.executeQuery("SELECT * FROM `task` WHERE `taskType` = '咨询' " +
                                    " AND `subtaskType` = '"+subtaskType+"' AND `payment` >= '"+pay1string+"' AND `payment` <= '"+pay2string+"'" +
                                    "  AND `area` = '"+area+"' AND `ifDisplayable` = '1'");

                            List<task> sampleList = new ArrayList<>(); //清空taskList

                            List list = DbUtils.populate(rs,task.class);
                            for(int i = 0; i < list.size(); i++){
                                sampleList.add((task)list.get(i));
                            }

                            taskList = sampleList;

                            con.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    List<task> demand;

                    switch (position4) {
                        case 0:
                            break;
                        case 1:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeHour(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 2:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 3:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinThreeDay(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 4:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneWeek(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                        case 5:
                            demand = new ArrayList<>();
                            for (task task : taskList) {
                                if (TimeUtils.isDateWithinOneMonth(task.getDdl())) {
                                    demand.add(task);
                                }
                            }
                            taskList = demand;
                            break;
                    }

                    TaskAdapter adapter = new TaskAdapter(taskList, user, 1);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selection_recycler);
                    GridLayoutManager layoutManager = new GridLayoutManager(Selection3.this, 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection3.this, Task_CounselActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Selection3.this, Task_ErrandActivity.class);
        intent.putExtra("user_phone", myPhone);
        startActivity(intent);
        finish();
    }


    public void updateAllTaskStatus() {

        List<task> taskAllList = new ArrayList<>();

        try {
            con = DbUtils.getConn(); //initialize connection
            st = con.createStatement(); //initialize connection
            rs = st.executeQuery("SELECT * FROM `task` WHERE `ifShutDown` = '0'");

            List list = DbUtils.populate(rs, task.class);
            for (int i = 0; i < list.size(); i++) {
                taskAllList.add((task) list.get(i));
            }

            Log.d("select:updateAllnum",String.valueOf(taskAllList.size()));

            for (task task : taskAllList) {

                String id = task.getPreciseLaunchTime();

                if (task.getProgress() < 3) {
                    if (TimeUtils.isDateOneBigger(TimeUtils.getNowTime(), task.getDdl())) {
                        if (task.getIfAccepted()) {
                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '1' " +
                                    ", `ifShutDown` = '1', `progress` = '7', `statusText` = '接受者违约' " +
                                    "WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);
                            /*
                            task.setIfDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = true;
                            task.ifShutDown = true; //接收者未及时完成，关闭项目
                            task.setProgress(7);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());
                                    */

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);
                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("违约->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }

                            //给接收者发一条消息
                            List<user> helperList = DataSupport.where("myName = ?", task.getHelperName())
                                    .find(user.class);
                            user helper = helperList.get(0);

                            if (!helper.getMsgTaskList().contains(task)) {
                                helper.addMsg();
                                helper.addMsgTaskList(task.getPreciseLaunchTime());
                                helper.updateAll("phonenumber = ?", helper.getPhonenumber());
                                Log.d("违约->接收者", helper.getMyName()
                                        + " " + String.valueOf(helper.getMsg()) + " " + String.valueOf(helper.
                                        getMsgTaskList().size()));
                            }

                            */


                        } else {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = true;
                            task.ifDefault = false;
                            task.ifShutDown = true; //过期未接收，关闭项目
                            task.setProgress(6);
                            task.updateStatusText();
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '1' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '1', `progress` = '6', `statusText` = '逾期未被接收' " +
                                    "WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);

                            /*

                            //给发布者发一条消息
                            List<user> launcherList = DataSupport.where("myName = ?", task.getLauncherName())
                                    .find(user.class);
                            user launcher = launcherList.get(0);

                            if (!launcher.getMsgTaskList().contains(task)) {
                                launcher.addMsg();
                                launcher.addMsgTaskList(task.getPreciseLaunchTime());
                                launcher.updateAll("phonenumber = ?", launcher.getPhonenumber());
                                Log.d("过期->发布者", launcher.getMyName()
                                        + " " + String.valueOf(launcher.getMsg()) + " " + String.valueOf(launcher.
                                        getMsgTaskList().size()));
                            }
                            */


                        }
                    } else {
                        if (task.getIfAccepted()) {
                            /*
                            task.ifDisplayable = false;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);

                        } else {
                            /*
                            task.ifDisplayable = true;
                            task.ifOutdated = false;
                            task.ifDefault = false;
                            task.ifShutDown = false;
                            task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                                    task.getLauncherName());*/

                            String sql ="UPDATE `task` SET `ifDisplayable` = '1' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                                    ", `ifShutDown` = '0' WHERE `preciseLaunchTime` = '"+id+"'";
                            st.executeUpdate(sql);

                        }
                    }
                } else if (task.getProgress() == 3) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '已完成,待支付' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);


                } else if (task.getProgress() == 4) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = false;
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '0', `statusText` = '待评价' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);

                } else if (task.getProgress() == 5) {
                    /*
                    task.ifDisplayable = false;
                    task.ifOutdated = false;
                    task.ifDefault = false;
                    task.ifShutDown = true;  //评论完成，关闭任务
                    task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                            task.getLauncherName());*/

                    String sql ="UPDATE `task` SET `ifDisplayable` = '0' , `ifOutdated` = '0' , `ifDefault` = '0' " +
                            ", `ifShutDown` = '1', `statusText` = '任务已结束' WHERE `preciseLaunchTime` = '"+id+"'";
                    st.executeUpdate(sql);

                }
        /*
                task.updateAll("preciseLaunchTime = ? and launcherName = ?", task.getPreciseLaunchTime(),
                        task.getLauncherName());*/

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}




