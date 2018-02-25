package com.example.sufehelperapp;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtils {

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    /**
     * 获取时间戳
     *
     * @return 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }
    /**
     * 时间转换为时间戳
     * @param time:需要转换的时间
     * @return
     */
    public static String dateToStamp(String time)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 时间戳转换为字符串
     * @param time:时间戳
     * @return
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sf.format(d);
    }
    /**
     *获取距现在某一小时的时刻
     * @param hour hour=-1为上一个小时，hour=1为下一个小时
     * @return
     */
    public static String getLongTime(int hour){
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int h; // 需要更改的小时
        h = c.get(Calendar.HOUR_OF_DAY) - hour;
        c.set(Calendar.HOUR_OF_DAY, h);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Log.v("time",df.format(c.getTime()));
        return df.format(c.getTime());
    }
    /**
     * 比较时间大小
     * @param str1：要比较的时间
     * @param str2：要比较的时间
     * @return
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    public static boolean isDateWithinThreeHour(String ddl){
        //格式化时间
        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date beginTime=CurrentTime.parse(getNowTime());
            Date endTime=CurrentTime.parse(ddl);
            if(((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))<=0.125) {
                return true;
            }else{
                return false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isDateWithinOneDay(String ddl){
        //格式化时间
        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date beginTime = CurrentTime.parse(getNowTime());
            Date endTime = CurrentTime.parse(ddl);
            if(((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))<=1) {
                return true;
            }else{
                return false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isDateWithinThreeDay(String ddl){
        //格式化时间
        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {

            Date beginTime = CurrentTime.parse(getNowTime());
            Date endTime = CurrentTime.parse(ddl);
            if(((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))<=3) {
                return true;
            }else{
                return false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


    public static boolean isDateWithinOneWeek(String ddl){
        //格式化时间
        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date beginTime = CurrentTime.parse(getNowTime());
            Date endTime = CurrentTime.parse(ddl);
            if(((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))<=7) {
                return true;
            }else{
                return false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isDateWithinOneMonth(String ddl){
        //格式化时间
        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date beginTime = CurrentTime.parse(getNowTime());
            Date endTime = CurrentTime.parse(ddl);
            if(((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))<=31) {
                return true;
            }else{
                return false;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }



}
