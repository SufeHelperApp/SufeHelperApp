package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyActivity_History_Recieved extends Fragment implements View.OnClickListener {

    @Nullable
    private TaskAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_history_recieved, container, false);
      
        //TODO:接受user
        //user user = (user) getIntent().getSerializableExtra("user_data");
        //String myName = user.getMyName();

        List<task> taskList = DataSupport.where("launcherName = ?","tom").find(task.class);
        //TODO: 用当前用户代替
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_recieved_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        return view;
    }
  
    @Override
    public void onClick(View view) {

    }

}
