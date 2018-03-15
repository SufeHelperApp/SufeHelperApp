package com.example.sufehelperapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;

import java.util.ArrayList;
import java.util.List;

public class My_RegisterThirdActivity extends AppCompatActivity {

    private user user;
    String dormArea;
    String dormName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_register_third);

        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("RegisterThirdActivity",user.getMyName());

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

        //获取spinner实例

        final Spinner dormAreaSpinner = (Spinner) findViewById(R.id.spinner_address1);

        final TextView dormNameView = findViewById(R.id.dormname);

        //监听spinner

        dormAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] dormAreas = getResources().getStringArray(R.array.area);
                dormArea = dormAreas[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //点击确认后，设置项目的dormArea和dormLocation

        Button button2 = (Button) findViewById(R.id.button_confirm);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dormName = dormNameView.getText().toString();

                if(dormName.isEmpty()){
                    Toast.makeText(My_RegisterThirdActivity.this, "寝室名称不得为空！",
                            Toast.LENGTH_SHORT).show();
                }else {

                    //TODO:设置当前用户的dormArea
                    user.setDormArea(dormArea);
                    //TODO:设置当前用户的寝室位置
                    user.setDormitoryLocation(dormName);

                    user.updateAll("phonenumber = ?",user.getPhonenumber());
                    Log.d("dormArea", user.getDormArea());
                    Log.d("dormLocation", user.getDormitoryLocation());

                    Intent intent = new Intent(My_RegisterThirdActivity.this, My_RegisterForthActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                }
            }
        });


    }

}