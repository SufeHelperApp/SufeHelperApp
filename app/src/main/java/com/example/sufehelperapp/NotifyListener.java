package com.example.sufehelperapp;

import android.os.Vibrator;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;


public class NotifyListener extends BDNotifyListener {

    private Vibrator mVibrator01;

    public void onNotify(BDLocation mlocation, float distance){
        mVibrator01.vibrate(1000);//振动提醒已到设定位置附近

    }

}
