package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

public class weeklyData extends DataSupport {
    private int phonenumber;
    private int releasenumber; //我的任务发布数
    private int acceptnumber;  //我的任务完成数
    private double mylaunchtime;   //我的任务发布时间
    private double mydeadline; //我的任务完成时间


    public weeklyData ( int phonenumber, int releasenumber, int acceptnumber, double mylaunchtime,
                        double mydeadline){
        this.phonenumber = phonenumber;
        this.releasenumber = releasenumber;
        this.acceptnumber = acceptnumber;
        this.mylaunchtime = mylaunchtime;
        this.mydeadline = mydeadline;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getReleasenumber() {
        return releasenumber;
    }

    public void setReleasenumber(int releasenumber) {
        this.releasenumber = releasenumber;
    }

    public int getAcceptnumber() {
        return acceptnumber;
    }

    public void setAcceptnumber(int acceptnumber) {
        this.acceptnumber = acceptnumber;
    }

    public double getMylaunchtime() {
        return mylaunchtime;
    }

    public void setMylaunchtime(double mylaunchtime) {
        this.mylaunchtime = mylaunchtime;
    }

    public double getMydeadline() {
        return mydeadline;
    }

    public void setMydeadline(double mydeadline) {
        this.mydeadline = mydeadline;
    }
}



