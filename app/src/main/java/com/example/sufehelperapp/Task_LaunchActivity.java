package com.example.sufehelperapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.Locale;

public class Task_LaunchActivity extends AppCompatActivity {

    public String subtaskType;
    public String area;

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
                intent.putExtra("user_now",user);
                startActivity(intent);
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //从MainActivity接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Task_LaunchActivity",myName);

        Button button = (Button) findViewById(R.id.title_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_LaunchActivity.this, Task_HomeActivity.class);
                intent.putExtra("user_now",user);
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
                        intent1.putExtra("user_now", user);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Task_LaunchActivity.this, ExploreActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Task_LaunchActivity.this, My_HomeActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        // intialize views
        final Spinner subtaskView = (Spinner) findViewById(R.id.spinner_subtasks);
        final Spinner areaView = (Spinner) findViewById(R.id.spinner_areas);
        final TextView dateView = (TextView) findViewById(R.id.editDate);
        final TextView timeView = (TextView) findViewById(R.id.editTime);
        final TextView locationView = (TextView) findViewById(R.id.launch_location);
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

        areaView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] areas = getResources().getStringArray(R.array.area);
                area = areas[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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

                if(!subtaskType.isEmpty() && !area.isEmpty() && !date.isEmpty() && !time.isEmpty() && !location.isEmpty() && !payment.isEmpty()
                        && !description.isEmpty()) {


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
                    task.setArea(area);
                    task.setLocation(location);
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
