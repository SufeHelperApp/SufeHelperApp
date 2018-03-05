package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MyActivity_Historical_Task extends AppCompatActivity {

    private user user;
    private Bundle bundle;

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
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("Historical_Task",user.getMyName());

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(MyActivity_Historical_Task.this, Task_HomeActivity.class);
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(MyActivity_Historical_Task.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(MyActivity_Historical_Task.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });



        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Historical_Task.this, MyActivity_Mytask.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        //bundle用于传当前user
        bundle = new Bundle();
        bundle.putSerializable("user_now",user);//将nameinfo填充入句柄


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

        fragmentChange();
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


    private void fragmentChange(){
        list_fragment2 = new ArrayList<>();

        cFragment = new MyActivity_History_Recieved();
        cFragment.setArguments(bundle); //传user入fragments
        hFragment = new MyActivity_History_Republish();
        hFragment.setArguments(bundle); //传user入fragments


        list_fragment2.add(cFragment);
        list_fragment2.add(hFragment);

        list_title2 = new ArrayList<>();
        list_title2.add("接受的任务");
        list_title2.add("发布的任务");

        fAdapter = new fragmentAdapter_history(getSupportFragmentManager(),list_fragment2,list_title2);
        vp_pager2.setAdapter(fAdapter);
        tab_title2.setupWithViewPager(vp_pager2);
    }
}
