package com.example.sufehelperapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MyActivity_Historical_Task extends AppCompatActivity {

    private TabLayout tab_title2;
    private ViewPager vp_pager2;

    private List<String> list_title2;    //tab名称列表
    private List<View> listViews2;
    private List<Fragment> list_fragment2;

    private View recievedtaskView;  //定义接受任务界面
    private View republishtaskView;  //定义发布任务界面

    private viewAdapter_history vAdapter; //定义以view为切换的Adapter
    private fragmentAdapter_history fAdapter;  //定义以fragment为切换的Adapter

    private MyActivity_History_Recieved cFragment;
    private MyActivity_History_Republish hFragment;
    private ImageButton imgbtn1;

    private int[] tabImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_historical_task);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user user = (user) getIntent().getSerializableExtra("user_data");
        String myName = user.getMyName();

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Historical_Task.this, MyActivity_Mytask.class);
                startActivity(intent);
            }
        });
        /**Button button2 = (Button) findViewById(R.id.launcher_image);
         button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_mytask_personalhome.class);
        startActivity(intent);
        }
        });*/
        /**imgbtn1 = (ImageButton) findViewById(R.id.launcher_image);
         imgbtn1.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_mytask_personalhome.class);
        startActivity(intent);
        }
        });*/

        initControls();

        viewChange();
    }
    /**
     * 初始化控件
     */
    private void initControls() {
        tab_title2 = (TabLayout) findViewById(R.id.tab_title2);
        vp_pager2 = (ViewPager) findViewById(R.id.vp_pager2);

        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.ic_explore,R.drawable.ic_explore};
    }
    /**
     * 采用在viewpager中切换view的方式，并添加了icon的功能
     */


    private void viewChange() {
        listViews2 = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        recievedtaskView = mInflater.inflate(R.layout.activity_my_history_recieved,null);
        republishtaskView = mInflater.inflate(R.layout.activity_my_history_republish,null);
        listViews2.add(recievedtaskView);
        listViews2.add(republishtaskView);

        list_title2 = new ArrayList<>();
        list_title2.add("我接受的任务");
        list_title2.add("我发布的任务");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tab_title2.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        tab_title2.addTab(tab_title2.newTab().setText(list_title2.get(0)));
        tab_title2.addTab(tab_title2.newTab().setText(list_title2.get(1)));

        vAdapter = new viewAdapter_history(this,listViews2,list_title2,tabImg);
        vp_pager2.setAdapter(vAdapter);

        //将tabLayout与viewpager连起来
        tab_title2.setupWithViewPager(vp_pager2);
    }
}
