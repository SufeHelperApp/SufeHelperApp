package com.example.sufehelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MyActivity_mytask_currenttask extends AppCompatActivity {

    ImageButton imagebutton1;
    @Nullable

    public View onCreatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mytask_currenttask, container, false);
        return view;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask);

        imagebutton1 = (ImageButton) findViewById(R.id.launcher_image);
        imagebutton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity_mytask_currenttask.this, MyActivity_mytask_personalhome.class);
                startActivity(intent);
            }
        });
        Button button1 = (Button) findViewById(R.id.button_evaluation);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_mytask_currenttask.this, MyActivity_evaluation.class);
                startActivity(intent);
            }
        });
    }
}
