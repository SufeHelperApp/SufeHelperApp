package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

//TODO: taskId 能删掉吗？实现：点击任务卡片时直接传Task。还有哪里会用到？
//private static int taskId = 0;

public class TASK1 extends DataSupport {

    private int taskId;
    private user launcher;
    private user receiver;

    private String launcherName;
    private int launcherImageId;
    private String launcherPhoneNumber;

    private String taskType;
    private String subtaskType;
    //TODO: 任务发布时间能删掉吗？（默认筛选以“截止时间”倒序）
    //private double launchtime; //任务发布时间
    private String ddlDate;   //任务截止日期
    private String ddlTime;   //任务截止时间
    private String payment;  //任务报酬
    private String location;   //任务位置
    private Double locationCoordinator; //任务位置坐标
    private String description;  //任务描述

    private boolean ifAccepted;   //任务是否已被接受
    //TODO: 第一次匹配任务成功后任务就不显示在筛选页面中了？
    //private int times; //与接收/发布人匹配次数
    private int progress;     //任务进度(1-5)
    private int score;    //任务评分(1-10)


    // 完整构造函数

    public TASK1(user launcher, String subtaskType, String ddlDate, String ddlTime, String payment, String location,
                 String description){

        //this.taskId = taskId++;

        this.launcher = launcher; //TODO: 如何获取当前的用户？

        this.launcherName = launcher.getMyName();
        this.launcherImageId = launcher.getMyImageId();
        this.launcherPhoneNumber = launcher.getPhonenumber();

        this.subtaskType = subtaskType; //TODO: 获取@id/spinner_subtasks 中的字符串
        if(subtaskType == "占座" || subtaskType =="拿快递" ||subtaskType =="买饭" ||subtaskType == "买东西"|| subtaskType =="拼单")
        {taskType = "跑腿";}
        else if(subtaskType == "电子产品修理" || subtaskType =="家具器件组装" ||subtaskType =="学习作业辅导" ||
                subtaskType == "技能培训"|| subtaskType =="找同好")
        {taskType = "技能";}
        else{taskType = "咨询";}

        this.ddlDate = ddlDate; //TODO: 获取@id/editDate 中的字符串
        this.ddlTime = ddlTime; //TODO: 获取@id/editTime 中的字符串
        this.payment = payment; //TODO: 获取@id/spinner_subtasks 中的字符串
        this.location = location; //TODO: 获取@id/launch_location 中的字符串
        this.description = description; //TODO: 获取@id/launch_description中的字符串

        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.score = -1; //默认评分为-1：无评分
        this.progress = 1; //默认进度为1：已发布
    }

    // 临时构造函数

    public TASK1(int taskId, String launcherName, int launcherImageId, String launcherPhoneNumber, String taskType,
                String location, String ddlDate, String payment){
        //this.taskId = ++taskId;
        this.launcherName = launcherName;
        this.launcherImageId = launcherImageId;
        this.launcherPhoneNumber = launcherPhoneNumber;
        this.taskType = taskType;
        this.location = location;
        this.ddlDate = ddlDate;
        this.payment = payment;
    }


    //TODO: 删掉讨论后决定不用的方法

    public String getLauncherName(){
        return launcherName;
    }

    /*
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    } */

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

    /*
    public double getLaunchtime() {
        return launchtime;
    }

    public void setLaunchtime(double launchtime) {
        this.launchtime = launchtime;
    } */

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*
    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    */

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


