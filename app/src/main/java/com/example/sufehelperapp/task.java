package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Time;

import com.example.sufehelperapp.TimeUtils;


public class task extends DataSupport implements Serializable{

    private int taskId;
    private boolean isValid;

    private user launcher;
    private user helper;
    //private user invited //TODO后期：任务邀请人
    //private boolean invitationAccepted

    private String launcherName;
    private int launcherImageId;
    private String launcherPhoneNumber;

    private String taskType;
    private String subtaskType;
    private String launchtime; //任务发布时间
    private String finishtime;
    private String ddlDate;   //任务截止日期
    private String ddlTime;   //任务截止时间
    private String ddl;
    private String payment;  //任务报酬
    private String area;     //任务校区
    private String location;   //任务位置
    private Double locationCoordinator; //TODO: 任务位置经度纬度
    private String description;  //任务描述

    private boolean ifAccepted;   //任务是否已被接受
    private int progress;     //任务进度(1-5)
    private int score;    //任务评分(1-10)

    private boolean ifDefault;  //是否违约
    private boolean within1;
    private boolean within2;
    private boolean within3;
    private boolean within4;
    private boolean within5;

    private int compatibility;

    public task(){
        this.taskId = getIdTask();
        this.isValid = true;

        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.progress = 1; //默认进度为1：已发布
        this.score = -1; //默认评分为-1：无评分
        this.within1 = false;
        this.within2 = false;
        this.within3 = false;
        this.within4 = false;
        this.within5 = false;
    }


    // 完整构造函数

    public task(user launcher, String subtaskType, String ddlDate, String ddlTime, String payment, String location,
                String description){

        this.taskId = getIdTask();

        this.launcher = launcher;

        this.launcherName = launcher.getMyName();
        this.launcherImageId = launcher.getMyImageId();
        this.launcherPhoneNumber = launcher.getPhonenumber();

        this.subtaskType = subtaskType;
        this.taskType = chooseTaskType(subtaskType);

        this.ddlDate = ddlDate;
        this.ddlTime = ddlTime;
        this.payment = payment;
        this.location = location;
        this.description = description;

        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.progress = 1; //默认进度为1：已发布
        this.score = -1; //默认评分为-1：无评分
    }

    // 临时构造函数

    public task(String launcherName, int launcherImageId, String launcherPhoneNumber, String subtaskType,
                String location, String ddlDate, String ddlTime, String payment, String description){

        this.taskId = getIdTask();

        this.launcher = launcher;

        this.launcherName = launcherName;
        this.launcherImageId = launcherImageId;
        this.launcherPhoneNumber = launcherPhoneNumber;
        this.subtaskType = subtaskType;
        this.taskType = chooseTaskType(subtaskType);
        this.location = location;
        this.ddlDate = ddlDate;
        this.ddlTime = ddlTime;
        this.payment = payment;
        this.description = description;

        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.progress = 1; //默认进度为1：已发布
        this.score = -1; //默认评分为-1：无评分

    }


    public String chooseTaskType(String subtaskType){
        if(subtaskType == "占座" || subtaskType =="拿快递" ||subtaskType =="买饭" ||subtaskType == "买东西"|| subtaskType =="拼单")
        {return"跑腿";}
        else if(subtaskType == "电子产品修理" || subtaskType =="家具器件组装" ||subtaskType =="学习作业辅导" ||
                subtaskType == "技能培训"|| subtaskType =="找同好")
        {return"技能";}
        else{return"咨询";}
    }


    //检查项目在给定时间段内

    public int getCompatibility(){
        return compatibility;
    }

    public void setCompatibility(user user){
        
    }

    public boolean checkWithin(int position){
        String taskTime = this.getDdl();
        switch (position){
            case 1: within1 = TimeUtils.isDateWithinThreeHour(taskTime); return within1;
            case 2: within2 = TimeUtils.isDateWithinOneDay(taskTime); return within2;
            case 3: within3 = TimeUtils.isDateWithinThreeDay(taskTime); return within3;
            case 4: within4 = TimeUtils.isDateWithinOneWeek(taskTime); return within4;
            case 5: within5 = TimeUtils.isDateWithinOneMonth(taskTime); return within5;
            default: return false;
        }
    }

    //检查项目是否过期

    public boolean checkIsValid(){
        if(TimeUtils.isDateOneBigger(TimeUtils.getNowTime(),launchtime)){
            this.isValid = false;
        }
        return this.isValid;
    }

    public void setIsValid(boolean a){
        this.isValid = a;
    }

    public String getLaunchtime() {
        return launchtime;
    }

    public void setLaunchtime() {
        this.launchtime = TimeUtils.getNowTime();
    }

    public String getFinishtime(){return finishtime;}

    public void setFinishtime(){
        this.finishtime = TimeUtils.getNowTime();
    }

    public String getDdl(){return ddl;}

    public void setDdl(){
        this.ddl = ddlDate + " " + ddlTime; //ddl:2018/12/31 17:00
    }

    public int getTaskId(){return taskId;}

    public int getIdTask(){return ++Static.ID_TASK;}

    public user getLauncher(){
        return launcher;
    }

    public user getHelper(){
        return helper;
    }

    public void setLauncher(user launcher){
        this.launcher =  launcher;
    }

    public void setHelper(user helper){
        this.helper = helper;
    }

    public String getLauncherName(){
        return launcherName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSubtaskType() {
        return subtaskType;
    }

    public void setSubtaskType(String subtask) {
        this.subtaskType = subtask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDdlDate() {
        return ddlDate;
    }

    public void setDdlDate(String ddlDate) {this.ddlDate = ddlDate;}

    public String getDdlTime() {
        return ddlTime;
    }

    public void setDdlTime(String ddlTime) {this.ddlTime = ddlTime;}

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public boolean ifAccepted() {
        return ifAccepted;
    }

    public void setIfAccepted(boolean ifAccepted) {
        this.ifAccepted = ifAccepted;
    }

    public String getArea(){
        return area;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getProgress() {return progress;}

    public void setProgress(int process) {
        this.progress = progress;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {this.score = score;}

    public int getLauncherImageId() {
        return launcherImageId;
    }

    public void setLauncherImageId(int launcherImageId) {
        this.launcherImageId = launcherImageId;
    }

    public String getLauncherPhoneNumber() {
        return launcherPhoneNumber;
    }

    public void setLauncherPhoneNumber(String launcherPhoneNumber) {
        this.launcherPhoneNumber = launcherPhoneNumber;
    }

}





