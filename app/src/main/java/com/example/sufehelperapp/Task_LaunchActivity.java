package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Task_LaunchActivity extends AppCompatActivity {

    public String subtaskType;
    public String area;

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
                startActivity(intent);
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //从MainActivity接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        String myName = user.getMyName();
        Log.d("Task_LaunchActivity",myName);


        // intialize views
        final Spinner subtaskView = (Spinner) findViewById(R.id.spinner_subtasks);
        final Spinner areaView = (Spinner) findViewById(R.id.spinner_areas);
        final TextView dateView = (TextView) findViewById(R.id.editDate);
        final TextView timeView = (TextView) findViewById(R.id.editTime);
        final TextView locationView = (TextView) findViewById(R.id.launch_location);
        final TextView paymentView = (TextView) findViewById(R.id.launch_payment);
        final TextView descriptionView = (TextView) findViewById(R.id.launch_description);


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
                double paymentDouble = Double.parseDouble(payment);
                String description = descriptionView.getText().toString();

                if(!subtaskType.isEmpty() && !area.isEmpty() && !date.isEmpty() && !time.isEmpty() && !location.isEmpty() && !payment.isEmpty()
                        && !description.isEmpty()) {


                    task task = new task();
                    task.setLauncher(user);
                    task.setLauncherName(user.getMyName());
                    task.setLauncherPhoneNumber(user.getPhonenumber());
                    task.setLauncherImageId(user.getMyImageId());
                    task.setSubtaskType(subtaskType);
                    task.setTaskType(subtaskType);
                    task.setDdlDate(date);
                    task.setDdlTime(time);
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


}
