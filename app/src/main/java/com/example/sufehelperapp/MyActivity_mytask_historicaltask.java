package com.example.sufehelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyActivity_mytask_historicaltask extends Fragment implements View.OnClickListener {

    @Nullable
    private TaskAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mytask_historicaltask, container, false);

        List<task> taskList = DataSupport.where("launcherName = ?","tom").find(task.class);
        //TODO: 用当前用户代替
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.historicaltask_recycler);
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