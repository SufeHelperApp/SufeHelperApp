package com.example.sufehelperapp;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import java.util.List;

public class MyActivity_evaluation extends AppCompatActivity implements View.OnClickListener{
    public static final String TASK_SELECTED = "task_selected";
    private RatingBar ratingBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluation);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user user = (user) getIntent().getSerializableExtra("user_data");
        String myName = user.getMyName();

        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_evaluation.this, MyActivity_Mytask.class);
                startActivity(intent);
            }
        });

        ratingBar = (RatingBar) this.findViewById(R.id.ratingBar1);
        textView = (TextView) this.findViewById(R.id.textView1);
//      设置ratingBar的监听方法
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
//              textView append显示数据
//          textView.append("***当前值："+rating+"**步长："+ratingBar.getStepSize()+"\n");
//          switch case 语句对评价的星星个数进行判断 给出相应的操作
                switch ((int)rating) {
                    case 1:
                        textView.setText("当前的用户评价：太糟了");
                        break;
                    case 2:
                        textView.setText("当前的用户评价：不太好");
                        break;
                    case 3:
                        textView.setText("当前的用户评价：不错哦");
                        break;
                    case 4:
                        textView.setText("当前的用户评价：很好哦");
                        break;
                    case 5:
                        textView.setText("当前的用户评价：太棒了");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_submit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_evaluation.this);
                dialog.setTitle("提示");
                dialog.setMessage("您确定提交您的评价吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<user> users = DataSupport.where("myName = ?","sophia")
                                .find(user.class); //TODO: 用当前用户代替
                        user userSophia = users.get(0);

                        //TODO: 排查
                        task task = (task) getIntent().getSerializableExtra("task_selected");
                        task.setScore(ratingBar.getRating());
                        task.save();

                        userSophia.addToAverageScore(ratingBar.getRating());
                        userSophia.save();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

}
