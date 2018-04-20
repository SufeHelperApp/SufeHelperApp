package com.example.sufehelperapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

public class NoteActivity extends AppCompatActivity {

    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private PendingIntent mResultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note2);

        Button btnShow = (Button) findViewById(R.id.btnShow);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /*
         * 取得PendingIntent， 并设置跳转到的Activity，
         */
        Intent intent = new Intent(this, Selection1.class);
        mResultIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        btnShow.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                Bitmap largeIcon = BitmapFactory.decodeResource(
                        NoteActivity.this.getResources(), R.drawable.ic_task);

                mNotification = new NotificationCompat.Builder(getBaseContext())

                        // 设置大图标
                        .setLargeIcon(largeIcon)

                        // 设置小图标
                        .setSmallIcon(R.drawable.ic_task)

                        // 设置小图标旁的文本信息
                        .setContentInfo("1")

                        // 设置状态栏文本标题
                        .setTicker("你的系统有更新")

                        // 设置标题
                        .setContentTitle("系统更新")

                        // 设置内容
                        .setContentText("发现系统更新，点击查看详情")

                        // 设置ContentIntent
                        .setContentIntent(mResultIntent)

                        // 设置Notification提示铃声为系统默认铃声
                        .setSound(
                                RingtoneManager.getActualDefaultRingtoneUri(
                                        getBaseContext(),
                                        RingtoneManager.TYPE_NOTIFICATION))

                        // 点击Notification的时候使它自动消失
                        .setAutoCancel(true).build();

                mNotificationManager.notify(0, mNotification);
            }
        });

    }
}
