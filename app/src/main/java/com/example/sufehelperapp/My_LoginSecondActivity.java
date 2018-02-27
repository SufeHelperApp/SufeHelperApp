package com.example.sufehelperapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class My_LoginSecondActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);

        dbHelper = new MyDatabaseHelper(this,"USER.db",null,1);
        LitePal.getDatabase();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Button buttonback = (Button) findViewById(R.id.title_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(My_LoginSecondActivity.this, My_LoginFirstActivity.class);
                startActivity(intent1);
            }
        });
        Button button1 = (Button) findViewById(R.id.button_3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(My_LoginSecondActivity.this, My_LoginThirdActivity.class);
                startActivity(intent2);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView nameView = findViewById(R.id.login_second_name);
                TextView passwordView = findViewById(R.id.login_second_password);
                String name = nameView.getText().toString();
                String password = passwordView.getText().toString();

                List<user> userList = DataSupport.where("myName = ?",name).
                        where("password = ?",password).find(user.class);

                if(userList.isEmpty()){
                    Toast.makeText(My_LoginSecondActivity.this, "用户名或密码错误！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    user user = userList.get(0);
                    String myName = user.getMyName();
                    String txt = "欢迎回来, "+user.getMyName()+"!";

                    Intent intent = new Intent(My_LoginSecondActivity.this, MainActivity.class);
                    intent.putExtra("user_now", user);
                    startActivity(intent);
                    Toast.makeText(My_LoginSecondActivity.this,txt,
                            Toast.LENGTH_SHORT).show();

                    /*
                    //TODO: 传user
                    Intent intent3 = new Intent(My_LoginSecondActivity.this, MainActivity.class);
                    intent3.putExtra("user_data", user);
                    startActivity(intent3);

                    Intent intent4 = new Intent(My_LoginSecondActivity.this, DBTESTActivity.class);
                    intent4.putExtra("user_data", user);
                    startActivity(intent4);

                    Intent intent5 = new Intent(My_LoginSecondActivity.this, Explore_MyTalentActivity.class);
                    intent5.putExtra("user_data", user);
                    startActivity(intent5);

                    Intent intent6 = new Intent(My_LoginSecondActivity.this, Explore_WeeklyTalentActivity.class);
                    intent6.putExtra("user_data", user);
                    startActivity(intent6);

                    Intent intent7 = new Intent(My_LoginSecondActivity.this, ExploreActivity.class);
                    intent7.putExtra("user_data", user);
                    startActivity(intent7);

                    Intent intent8 = new Intent(My_LoginSecondActivity.this, My_HomeActivity.class);
                    intent8.putExtra("user_data", user);
                    startActivity(intent8);

                    Intent intent9 = new Intent(My_LoginSecondActivity.this, My_LoginFirstActivity.class);
                    intent9.putExtra("user_data", user);
                    startActivity(intent9);

                    Intent intent10 = new Intent(My_LoginSecondActivity.this, My_LoginForthActivity.class);
                    intent10.putExtra("user_data", user);
                    startActivity(intent10);

                    Intent intent11 = new Intent(My_LoginSecondActivity.this, My_LoginSecondActivity.class);
                    intent11.putExtra("user_data", user);
                    startActivity(intent11);

                    Intent intent12 = new Intent(My_LoginSecondActivity.this, My_LoginThirdActivity.class);
                    intent12.putExtra("user_data", user);
                    startActivity(intent12);

                    Intent intent13 = new Intent(My_LoginSecondActivity.this, My_RegisterFirstActivity.class);
                    intent13.putExtra("user_data", user);
                    startActivity(intent13);

                    Intent intent14 = new Intent(My_LoginSecondActivity.this, My_RegisterSecondActivity.class);
                    intent14.putExtra("user_data", user);
                    startActivity(intent14);

                    Intent intent15 = new Intent(My_LoginSecondActivity.this, MyActivity.class);
                    intent15.putExtra("user_data", user);
                    startActivity(intent15);

                    Intent intent16 = new Intent(My_LoginSecondActivity.this, MyActivity_chat.class);
                    intent16.putExtra("user_data", user);
                    startActivity(intent16);

                    Intent intent17 = new Intent(My_LoginSecondActivity.this, MyActivity_chat_home.class);
                    intent17.putExtra("user_data", user);
                    startActivity(intent17);

                    Intent intent18 = new Intent(My_LoginSecondActivity.this, MyActivity_credit.class);
                    intent18.putExtra("user_data", user);
                    startActivity(intent18);

                    Intent intent19 = new Intent(My_LoginSecondActivity.this, MyActivity_Edit_NickName.class);
                    intent19.putExtra("user_data", user);
                    startActivity(intent19);

                    Intent intent20 = new Intent(My_LoginSecondActivity.this, MyActivity_Edit_password.class);
                    intent20.putExtra("user_data", user);
                    startActivity(intent20);

                    Intent intent21 = new Intent(My_LoginSecondActivity.this, MyActivity_Edit_phonenumber.class);
                    intent21.putExtra("user_data", user);
                    startActivity(intent21);

                    Intent intent22 = new Intent(My_LoginSecondActivity.this, MyActivity_evaluation.class);
                    intent22.putExtra("user_data", user);
                    startActivity(intent22);

                    Intent intent23 = new Intent(My_LoginSecondActivity.this, MyActivity_Historical_Task.class);
                    intent23.putExtra("user_data", user);
                    startActivity(intent23);

                    Intent intent24 = new Intent(My_LoginSecondActivity.this, MyActivity_History_Recieved.class);
                    intent24.putExtra("user_data", user);
                    startActivity(intent24);

                    Intent intent25 = new Intent(My_LoginSecondActivity.this, MyActivity_History_Republish.class);
                    intent25.putExtra("user_data", user);
                    startActivity(intent25);

                    Intent intent26 = new Intent(My_LoginSecondActivity.this, MyActivity_Myaward.class);
                    intent26.putExtra("user_data", user);
                    startActivity(intent26);

                    Intent intent27 = new Intent(My_LoginSecondActivity.this, MyActivity_Mytask.class);
                    intent27.putExtra("user_data", user);
                    startActivity(intent27);

                    Intent intent28 = new Intent(My_LoginSecondActivity.this, MyActivity_mytask_currenttask.class);
                    intent28.putExtra("user_data", user);
                    startActivity(intent28);

                    Intent intent29 = new Intent(My_LoginSecondActivity.this, MyActivity_mytask_historicaltask.class);
                    intent29.putExtra("user_data", user);
                    startActivity(intent29);

                    Intent intent30 = new Intent(My_LoginSecondActivity.this, MyActivity_mytask_personalhome.class);
                    intent30.putExtra("user_data", user);
                    startActivity(intent30);

                    Intent intent31 = new Intent(My_LoginSecondActivity.this, MyActivity_recievedaward.class);
                    intent31.putExtra("user_data", user);
                    startActivity(intent31);

                    Intent intent32 = new Intent(My_LoginSecondActivity.this, MyActivity_Setup.class);
                    intent32.putExtra("user_data", user);
                    startActivity(intent32);

                    Intent intent33 = new Intent(My_LoginSecondActivity.this, MyActivity_Setup_Edit.class);
                    intent33.putExtra("user_data", user);
                    startActivity(intent33);

                    Intent intent34 = new Intent(My_LoginSecondActivity.this, MyActivity_Talent.class);
                    intent34.putExtra("user_data", user);
                    startActivity(intent34);

                    Intent intent35 = new Intent(My_LoginSecondActivity.this, MyActivity_Vip.class);
                    intent35.putExtra("user_data", user);
                    startActivity(intent35);

                    Intent intent36 = new Intent(My_LoginSecondActivity.this, Task_CounselActivity.class);
                    intent36.putExtra("user_data", user);
                    startActivity(intent36);

                    Intent intent37 = new Intent(My_LoginSecondActivity.this, Task_CounselSelectActivity.class);
                    intent37.putExtra("user_data", user);
                    startActivity(intent37);

                    Intent intent38 = new Intent(My_LoginSecondActivity.this, Task_ErrandActivity.class);
                    intent38.putExtra("user_data", user);
                    startActivity(intent38);

                    Intent intent39 = new Intent(My_LoginSecondActivity.this, Task_ErrandSelectActivity.class);
                    intent39.putExtra("user_data", user);
                    startActivity(intent39);

                    Intent intent40 = new Intent(My_LoginSecondActivity.this, Task_InfoActivity.class);
                    intent40.putExtra("user_data", user);
                    startActivity(intent40);

                    Intent intent41 = new Intent(My_LoginSecondActivity.this, Task_LaunchActivity.class);
                    intent41.putExtra("user_data", user);
                    startActivity(intent41);

                    Intent intent42 = new Intent(My_LoginSecondActivity.this, Task_SkillActivity.class);
                    intent42.putExtra("user_data", user);
                    startActivity(intent42);

                    Intent intent43 = new Intent(My_LoginSecondActivity.this, Task_SkillSelectActivity.class);
                    intent43.putExtra("user_data", user);
                    startActivity(intent43);

                    Intent intent44 = new Intent(My_LoginSecondActivity.this, MyService.class);
                    intent44.putExtra("user_data", user);
                    startActivity(intent44);

                    */
                }
            }
        });

    }
}
