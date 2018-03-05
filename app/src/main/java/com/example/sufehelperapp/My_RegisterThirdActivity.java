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

    public LocationClient mLocationClient;
    private TextView positionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定位监听器
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_my_register_third);

        //positionText = (TextView) findViewById(R.id.position_text_view);
        //申请运行时权限
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(My_RegisterThirdActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(My_RegisterThirdActivity.this,Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(My_RegisterThirdActivity.this,Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(My_RegisterThirdActivity.this,permissions,1);
        } else {
            requestLocation();
        }

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

                    user.setDormArea(dormArea);
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
    //开始定位
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    //实时更新当前位置
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置更新间隔:每5秒更新当前位置
        option.setScanSpan(5000);
        //Device_Sensors将定位模式指定成传感器模式，指定使用GPS定位
        //Battery_Saving节电模式即网络定位
        //Hight_Accuracy默认模式
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);

        //获取位置信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        //活动销毁时停止定位
        mLocationClient.stop();
    }
    //通过循环将申请的权限都进行判断，如果有任何一个权限被拒绝，就会直接调用finish()方法关闭当前程序，
    // 只有当所有权限都被用户同意了，才会调用requestLocation()方法开始地理位置定位

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    for(int result : grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("维度：").append(location.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(location.getLongitude()).append("\n");
                    currentPosition.append("国家：").append(location.getCountry()).append("\n");
                    currentPosition.append("省：").append(location.getProvince()).append("\n");
                    currentPosition.append("市：").append(location.getCity()).append("\n");
                    currentPosition.append("区：").append(location.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(location.getStreet()).append("\n");
                    currentPosition.append("定位方式：");
                    if(location.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if(location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络");
                    }
                    positionText.setText(currentPosition);  //TODO：报错: positionText为空
                }
            });
        }


        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}