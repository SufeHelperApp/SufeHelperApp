package com.example.sufehelperapp;

import android.content.Context;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.yyydjk.library.DropDownMenu;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Task_ErrandSelectActivity extends AppCompatActivity {

    private static List<task> taskList = new ArrayList<>();

    private user user;

    private TaskAdapter adapter;

    @InjectView(R.id.dropDownMenu) DropDownMenu mDropDownMenu;
    private String headers[] = {"分区", "位置", "报酬", "时间"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter subtaskAdapter;
    private ListDropDownAdapter areaAdapter;
    private ListDropDownAdapter paymentAdapter;
    private ConstellationAdapter ddlAdapter;

    private String subtasks[] = {"不限","占座", "拿快递", "买饭", "买东西", "拼单"};
    private String areas[] = {"不限","国定校区", "武东校区", "武川校区"};
    private String payments[] = {"不限","0-5元", "6-10元","11-15元","15元以上"};
    private String ddls[] = {"不限","三小时内", "今天", "三天内", "本周", "本月"};

    private int ddlPosition = 0;

    private static int position1=0;
    private static int position2=0;
    private static int position3=0;
    private static int position4=0;

    private static String pay1string = "0";
    private static String pay2string = "10000";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__selection1);
        ButterKnife.inject(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //接受user
        //user user = (user) getIntent().getSerializableExtra("user_data");
        //String myName = user.getMyName();

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Task_ErrandSelectActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_ErrandSelectActivity.this, ExploreActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_ErrandSelectActivity.this, MyActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });


        List<task> tasks = DataSupport.findAll(task.class);
        for(task task:tasks) {
            task.checkWithin();
            task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                    task.getLauncherName());

        }

        //init city menu
        ListView cityView = new ListView(this);
        subtaskAdapter = new GirdDropDownAdapter(this, Arrays.asList(subtasks));
        cityView.setDividerHeight(0);
        cityView.setAdapter(subtaskAdapter);

        //init age menu
        ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        areaAdapter = new ListDropDownAdapter(this, Arrays.asList(areas));
        ageView.setAdapter(areaAdapter);

        //init sex menu
        ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        paymentAdapter = new ListDropDownAdapter(this, Arrays.asList(payments));
        sexView.setAdapter(paymentAdapter);

        //init constellation
        View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        ddlAdapter = new ConstellationAdapter(this, Arrays.asList(ddls));
        constellation.setAdapter(ddlAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(ddlPosition == 0 ? headers[3] : ddls[ddlPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);


        //initialization

        if(position1==0 && position2!=0){
            taskList= DataSupport
                    .where("area = ?", areas[position2])
                    .where("payment >= ?" , pay1string)
                    .where("payment <= ?", pay2string)
                    .where("within = ?", "1")
                    .where("isValid = ?","1").find(task.class);
        }else if(position1!=0 && position2==0){
            taskList = DataSupport
                    .where("subtaskType = ?", subtasks[position1])
                    .where("payment >= ?", pay1string)
                    .where("payment <= ?", pay2string)
                    .where("within = ?", "1")
                    .where("isValid = ?", "1").find(task.class);
        }else if(position1==0 && position2==0){
            taskList= DataSupport
                    .where("payment >= ?" , pay1string)
                    .where("payment <= ?", pay2string)
                    .where("within = ?", "1")
                    .where("isValid = ?","1").find(task.class);
        }else{
            taskList= DataSupport
                    .where("subtaskType = ?", subtasks[position1])
                    .where("area = ?", areas[position2])
                    .where("payment >= ?" , pay1string)
                    .where("payment <= ?", pay2string)
                    .where("within = ?", "1")
                    .where("isValid = ?","1").find(task.class);
        }


        final RecyclerView contentView = new RecyclerView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        contentView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList,user);
        contentView.setAdapter(adapter);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);



        //select subtaskType
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subtaskAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : subtasks[position]);
                mDropDownMenu.closeMenu();
                position1 = position; //点击后

                if(position1==0 && position2!=0){
                    taskList= DataSupport
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else if(position1!=0 && position2==0){
                    taskList = DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("payment >= ?", pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?", "1").find(task.class);
                }else if(position1==0 && position2==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

                adapter = new TaskAdapter(taskList,user);
                contentView.setAdapter(adapter);
                mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

            }
        });


        //select area
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : areas[position]);
                mDropDownMenu.closeMenu();
                position2 = position;

                if(position1==0 && position2!=0){
                    taskList= DataSupport
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else if(position1!=0 && position2==0){
                    taskList = DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("payment >= ?", pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?", "1").find(task.class);
                }else if(position1==0 && position2==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

                RecyclerView contentView = new RecyclerView(Task_ErrandSelectActivity.this);
                contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                GridLayoutManager layoutManager = new GridLayoutManager(Task_ErrandSelectActivity.this,1);
                contentView.setLayoutManager(layoutManager);
                adapter = new TaskAdapter(taskList,user);
                contentView.setAdapter(adapter);
                mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

            }

        });


        //select payment
        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paymentAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : payments[position]);
                mDropDownMenu.closeMenu();
                position3 = position;

                //update payments
                switch (payments[position3]) {
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

                if(position1==0 && position2!=0){
                    taskList= DataSupport
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else if(position1!=0 && position2==0){
                    taskList = DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("payment >= ?", pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?", "1").find(task.class);
                }else if(position1==0 && position2==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

                RecyclerView contentView = new RecyclerView(Task_ErrandSelectActivity.this);
                contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                GridLayoutManager layoutManager = new GridLayoutManager(Task_ErrandSelectActivity.this,1);
                contentView.setLayoutManager(layoutManager);
                adapter = new TaskAdapter(taskList,user);
                contentView.setAdapter(adapter);
                mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
            }
        });


        //select ddl
        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ddlAdapter.setCheckItem(position);
                ddlPosition = position;
                position4 = position;

                //update ddls
                List<task> tasks1 = DataSupport.findAll(task.class);
                for(task task:tasks1) {
                    task.ifWithin(position4); //determine within
                    task.updateAll("launchtime = ? and launcherName = ?",task.getLaunchtime(),
                            task.getLauncherName());

                }

                if(position1==0 && position2!=0){
                    taskList= DataSupport
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else if(position1!=0 && position2==0){
                    taskList = DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("payment >= ?", pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?", "1").find(task.class);
                }else if(position1==0 && position2==0){
                    taskList= DataSupport
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }else{
                    taskList= DataSupport
                            .where("subtaskType = ?", subtasks[position1])
                            .where("area = ?", areas[position2])
                            .where("payment >= ?" , pay1string)
                            .where("payment <= ?", pay2string)
                            .where("within = ?", "1")
                            .where("isValid = ?","1").find(task.class);
                }

                RecyclerView contentView = new RecyclerView(Task_ErrandSelectActivity.this);
                contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                GridLayoutManager layoutManager = new GridLayoutManager(Task_ErrandSelectActivity.this,1);
                contentView.setLayoutManager(layoutManager);
                adapter = new TaskAdapter(taskList,user);
                contentView.setAdapter(adapter);
                mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
            }
        });

    }


    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
