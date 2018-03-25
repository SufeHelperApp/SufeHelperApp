package com.example.sufehelperapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Task_LaunchActivity extends AppCompatActivity {

    private String myPhone;

    private Connection con;
    private ResultSet rs;

    public String subtaskType;
    private String POIName;

    private int num;

    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    private int mHour;
    private int mMinute;
    static final int TIME_DIALOG_ID = 1;
    private Button mPickTime;

    private String showdate;
    private String showtime;

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_task);
        Button button1 = (Button) findViewById(R.id.launch_location_coordinator);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_LaunchActivity.this, Map.class);
                intent.putExtra("user_phone",myPhone);
                startActivity(intent);
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //user
        myPhone = getIntent().getStringExtra("user_phone");

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

        num = getIntent().getIntExtra("num",1);

        if(num ==2){
            POIName = getIntent().getStringExtra("POIName");
        }


        Button button = (Button) findViewById(R.id.title_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_LaunchActivity.this, Task_HomeActivity.class);
                intent.putExtra("user_phone",myPhone);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Task_LaunchActivity.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone",myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Task_LaunchActivity.this, ExploreActivity.class);
                        intent3.putExtra("user_phone",myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Task_LaunchActivity.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone",myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        // intialize views
        final Spinner subtaskView = (Spinner) findViewById(R.id.spinner_subtasks);
        //final Spinner areaView = (Spinner) findViewById(R.id.spinner_areas);
        final TextView dateView = (TextView) findViewById(R.id.editDate);
        final TextView timeView = (TextView) findViewById(R.id.editTime);
        final TextView locationView = (TextView) findViewById(R.id.launch_coodinator);
        if(num ==2) {
            locationView.setText(POIName);
        }
        final TextView paymentView = (TextView) findViewById(R.id.launch_payment);
        final TextView descriptionView = (TextView) findViewById(R.id.launch_description);

        mPickDate = (Button) findViewById(R.id.editDate);
        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task_LaunchActivity.this.showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance(Locale.CHINA);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        mPickTime = (Button) findViewById(R.id.editTime);
        mPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task_LaunchActivity.this.showDialog(TIME_DIALOG_ID);
            }
        });

        updateDisplay();
        uptimeDisplay();

        //detect spinner change
        subtaskView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] subtaskTypes = getResources().getStringArray(R.array.subtasks);
                subtaskType = subtaskTypes[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /*areaView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] areas = getResources().getStringArray(R.array.area);
                area = areas[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        //once clicked, build new task
        Button b1 = (Button) findViewById(R.id.launch_task_btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = dateView.getText().toString();
                String time = timeView.getText().toString();
                String location = locationView.getText().toString();
                String payment = paymentView.getText().toString();
                String description = descriptionView.getText().toString();


                if(!subtaskType.isEmpty() && !date.isEmpty() && !time.isEmpty() && !payment.isEmpty()
                        && !description.isEmpty() && !location.isEmpty()) {

                    String phone = "";
                    String taskType = task.setTaskType1(subtaskType);
                    String ddl = task.setDdl(date,time);
                    String launchtime = TimeUtils.getNowTime();
                    String precisetime = TimeUtils.getNowTimePrecise();

                    try{
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();

                        Statement st = con.createStatement();

                        rs = st.executeQuery("SELECT * FROM `user` WHERE `myName` = '"+user.getMyName()+"'");

                        while (rs.next()) {
                            phone += rs.getString(3);
                            int credit = rs.getInt("credit") + 15;
                            int taskLNum = rs.getInt("taskLNum") + 1;
                            int taskNum = rs.getInt("taskNum") + 1;

                            String sql1 = "UPDATE `user` SET `credit`= '"+credit+"' WHERE myName='"+user.getMyName()+"'";
                            st.executeUpdate(sql1);
                            String sql2 = "UPDATE `user` SET `taskLNum`= '"+taskLNum+"' WHERE myName='"+user.getMyName()+"'";
                            st.executeUpdate(sql2);
                            String sql3 = "UPDATE `user` SET `taskNum`= '"+taskNum+"' WHERE myName='"+user.getMyName()+"'";
                            st.executeUpdate(sql3);
                        }
                        Log.d("phone",phone);

                        rs.close();
                        st.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (con != null)
                            try {
                                con.close();
                            } catch (SQLException e) {
                            }

                    }

                    try{
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        con = DbUtils.getConn();

                        String sql = "INSERT INTO `task`(`launcherName`, `launcherPhoneNumber`, `subtaskType`, `taskType`, `ddlDate`, `ddlTime`, `ddl`, `area`, `location`, `payment`, `description`, `ifDisplayable`, `helperName`, `ifAccepted`, `ifOutdated`, `ifDefault`, `ifShutDown`, `progress`, `StatusText`, `launchtime`, `preciseLaunchTime`, `accepttime`, `achievetime`, `paytime`, `finishtime`, `latitude`, `longtitude`, `score`) VALUES ('"+user.getMyName()+"','"+user.getPhonenumber()+"','"+subtaskType+"','"+taskType+"','"+date+"','"+time+"','"+ddl+"','"+area+"','"+location+"','"+payment+"','"+description+"','1','','0','0','0','0','1','待接收','"+launchtime+"','"+precisetime+"','','','','','0','0','0')";
                        Statement st = con.createStatement();
                        st.executeUpdate(sql);

                        st.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (con != null)
                            try {
                                con.close();
                            } catch (SQLException e) {
                            }
                    }


                    Intent intent1 = new Intent(Task_LaunchActivity.this, Task_HomeActivity.class);
                    intent1.putExtra("user_phone",myPhone);
                    startActivity(intent1);
                    Toast.makeText(Task_LaunchActivity.this, "任务发布成功！", Toast.LENGTH_SHORT).show();

                    /*

                    double paymentDouble = Double.parseDouble(payment);

                    task task = new task();
                    task.setLauncher(user);
                    task.setLauncherName(user.getMyName());
                    task.setLauncherPhoneNumber(user.getPhonenumber());
                    task.setLauncherImageId(user.getMyImageId());
                    task.setSubtaskType(subtaskType);
                    task.setTaskType(subtaskType);
                    task.setDdlDate(showdate);
                    task.setDdlTime(showtime);
                    task.setDdl();
                    if(num ==2)
                    {task.setLocation(POIName);}
                    task.setPayment(paymentDouble);
                    task.setDescription(description);

                    task.save();

                    user.increaseCredit(15);
                    user.addTaskLNum(1);
                    user.addTaskNum(1);

                    user.updateAll("phonenumber = ?",user.getPhonenumber());

                    Intent intent1 = new Intent(Task_LaunchActivity.this, Task_HomeActivity.class);
                    intent1.putExtra("user_now", user);
                    startActivity(intent1);
                    Toast.makeText(Task_LaunchActivity.this, "任务发布成功！", Toast.LENGTH_SHORT).show();

                    */

                }else{
                    Toast.makeText(Task_LaunchActivity.this, "请检查空缺字段！", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void updateDisplay() {

        if(mMonth+1<10){//month前添零
            if(mDay<10){//day前添零
                showdate = String.valueOf(mYear) + "/" + "0" + String.valueOf(mMonth+1) + "/" +
                        "0" + String.valueOf(mDay);
            }else{//day前无需添零
                showdate = String.valueOf(mYear) + "/" + "0" + String.valueOf(mMonth+1) + "/" +
                        String.valueOf(mDay);
            }
        }else{//month前无需添零
            if(mDay<10){//day前添零
                showdate = String.valueOf(mYear) + "/" + String.valueOf(mMonth+1) + "/" +
                        "0" +String.valueOf(mDay);
            }else{//day前无需添零
                showdate = String.valueOf(mYear) + "/" + String.valueOf(mMonth+1) + "/" +
                        String.valueOf(mDay);
            }
        }

        mPickDate.setText(showdate);
    }
    private void uptimeDisplay() {

        if(mHour<10){//hour前添零
            if(mMinute<10){//minute前添零
                showtime = "0" + String.valueOf(mHour) + ":" + "0" + String.valueOf(mMinute);
            }else{//minute前无需添零
                showtime = "0" + String.valueOf(mHour) + ":" + String.valueOf(mMinute);
            }
        }else{//hour前无需添零
            if(mMinute<10){//minute前添零
                showtime = String.valueOf(mHour) + ":" + "0" + String.valueOf(mMinute);
            }else{//minute前无需添零
                showtime = String.valueOf(mHour) + ":" + String.valueOf(mMinute);
            }
        }

        mPickTime.setText(showtime);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,mOnDateSetListener , mYear, mMonth, mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,TimePickerDialog.THEME_HOLO_LIGHT,onTimeSetListener,mHour,mMinute,true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mOnDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    mHour = i;
                    mMinute = i1;
                    uptimeDisplay();
                }
            };


    //未添加返回值


}
