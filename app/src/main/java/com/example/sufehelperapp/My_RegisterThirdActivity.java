package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class My_RegisterThirdActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;

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
        setContentView(R.layout.activity_my);

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

        /*
        initInterals();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new My_RegisterThirdActivity_Adapter(interalList);*/
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_register3);

        initData();
        initRecyclerView();
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