package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class My_RegisterThirdActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;

    private String area;
    private String dormName;

    private My_RegisterThirdActivity_Interal2[] my_registerThirdActivity_interal2s =
            {new My_RegisterThirdActivity_Interal2("常见需求"),
            new My_RegisterThirdActivity_Interal2("个人特长")};

    private List<My_RegisterThirdActivity_Interal2> interalList = new ArrayList<>();
    private My_RegisterThirdActivity_Adapter adapter;

    private Button mButton8;
    int flag = 0;
    int flag2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register_third);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_RegisterThirdActivity.this, My_RegisterFirstActivity.class);
                startActivity(intent1);
            }
        });

        //接收 My_RegisterSecondActivity 传来的user
        final user user = (user)getIntent().getSerializableExtra("user_now");
        Log.d("RegisterThirdActivity",user.getMyName());

        /*
        initInterals();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new My_RegisterThirdActivity_Adapter(interalList);*/
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_register3);

        initData();
        initRecyclerView();


        //获取组件实例
        final Spinner dormAreaSpinner = findViewById(R.id.spinner_address1);
        final Spinner dormNameSpinner = findViewById(R.id.spinner_address2);


        Button person1 = findViewById(R.id.person1);
        Button person2 = findViewById(R.id.person2);
        Button person3 = findViewById(R.id.person3);
        Button person4 = findViewById(R.id.person4);
        Button person5 = findViewById(R.id.person5);
        Button person6 = findViewById(R.id.person6);
        Button person7 = findViewById(R.id.person7);
        Button person8 = findViewById(R.id.person8);
        Button person9 = findViewById(R.id.person9);
        Button person10 = findViewById(R.id.person10);
        Button person11 = findViewById(R.id.person11);
        Button person12 = findViewById(R.id.person12);
        Button person13 = findViewById(R.id.person13);
        Button person14 = findViewById(R.id.person14);
        Button person15 = findViewById(R.id.person15);

        Button confirm = findViewById(R.id.button_confirm);


        //监听spinner内容变化
        dormAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] areas = getResources().getStringArray(R.array.area);
                area = areas[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dormNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String [] dormNames = getResources().getStringArray(R.array.dormitory);
                dormName = dormNames[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //点击确认后
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setDormArea(area);
                user.setDormitoryLocation(dormName);
                //TODO: 设置寝室坐标
                //TODO: 设置需求和特长
                user.save();
            }
        });


    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new My_RegisterThirdActivity_Adapter(this,interalList);
        //adapter.setOnItemClickListener(this);
        adapter.setButtonInterface(new My_RegisterThirdActivity_Adapter.ButtonInterface() {
            @Override
            public void onButtonClick(View view) {
                Intent intent1 = new Intent(My_RegisterThirdActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        adapter.setButtonInterface(new My_RegisterThirdActivity_Adapter.ButtonInterface() {
            @Override
            public void onButtonClick(View view) {
                final Button clickbtn = (Button) findViewById(R.id.person1);
                        switch(flag) {
                            case 0:
                                clickbtn.setActivated(true);
                                flag = 1;
                                break;
                            case 1:
                                clickbtn.setActivated(false);
                                flag = 0;
                                break;
                        }


            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        interalList.clear();
        for(int i=0;i<2;i++) {
            interalList.add(my_registerThirdActivity_interal2s[i]);
        }
    }


    /*@Override
    public void onItemClick(View view, int position) {

        Intent intent=new Intent(My_RegisterThirdActivity.this,MainActivity.class);
        startActivity(intent);

    }*/
    /*final Button clickbtn = (Button) findViewById(R.id.person1);

        clickbtn.setOnItemClickListener(new Button.OnClickListener() {
            public void onItemClick(View v) {

                switch(flag) {
                    case 0:
                        clickbtn.setActivated(true);
                        flag = 1;
                        break;
                    case 1:
                        clickbtn.setActivated(false);
                        flag = 0;
                        break;
                }
            }
        });*/
}