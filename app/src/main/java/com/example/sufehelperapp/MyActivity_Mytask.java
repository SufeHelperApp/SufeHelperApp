package com.example.sufehelperapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.sufehelperapp.fragmentAdapter;
import com.example.sufehelperapp.viewAdapter;

import org.litepal.crud.DataSupport;

public class MyActivity_Mytask extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;

    private List<String> list_title;    //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;

    private View currenttaskView;  //定义当前任务界面
    private View historicaltaskView;  //定义历史任务界面

    private viewAdapter vAdapter; //定义以view为切换的Adapter
    private fragmentAdapter fAdapter;  //定义以fragment为切换的Adapter

    private MyActivity_mytask_currenttask cFragment;
    private MyActivity_mytask_historicaltask hFragment;
    private ImageButton imgbtn1;

    private int[] tabImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Mytask.this, My_HomeActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.title_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_Historical_Task.class);
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
        tab_title = (TabLayout) findViewById(R.id.tab_title);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);

        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.ic_explore,R.drawable.ic_explore};
    }
    /**
     * 采用在viewpager中切换view的方式，并添加了icon的功能
     */


    private void viewChange() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        currenttaskView = mInflater.inflate(R.layout.activity_my_mytask_currenttask,null);
        historicaltaskView = mInflater.inflate(R.layout.activity_my_mytask_historicaltask,null);
        listViews.add(currenttaskView);
        listViews.add(historicaltaskView);

        list_title = new ArrayList<>();
        list_title.add("我接受的任务");
        list_title.add("我发布的任务");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tab_title.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));

        vAdapter = new viewAdapter(this,listViews,list_title,tabImg);
        vp_pager.setAdapter(vAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }
}
