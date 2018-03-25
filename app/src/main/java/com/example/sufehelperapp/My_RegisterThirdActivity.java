package com.example.sufehelperapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class My_RegisterThirdActivity extends AppCompatActivity {

    private String myPhone;
    private user user;

    Connection con;
    ResultSet rs;

    String dormArea;
    String dormName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_register_third);

        myPhone = getIntent().getStringExtra("user_phone");
        Log.d("RegisterSecondActivity",myPhone);

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

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

                    try {
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();
                        Statement st2 = con.createStatement();
                        st2.executeUpdate("UPDATE `user` SET `dormArea` = '" + dormArea + "' WHERE `phonenumber` = '" + myPhone + "'");

                        con.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(My_RegisterThirdActivity.this, My_RegisterForthActivity.class);
                    intent.putExtra("user_phone", myPhone);
                    startActivity(intent);
                }
            }
        });


    }

}