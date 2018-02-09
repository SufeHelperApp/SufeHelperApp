package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

public class TASK1 extends DataSupport {

    private int taskId;
    private String launcherName;
    private String taskType;   //任务大类（跑腿、技能、咨询）
    private String subtask;    //子任务名称
    private String description;       //任务描述
    private double launchtime; //发布时间
    private double deadline;   //完成时间
    private double payment;  //报酬
    private boolean ifAccept;       //任务是否已被接受
    private String taskLocation;   //位置？
    private int times; //与接收/发布人匹配次数
    private int process;       //任务进度
    private int grade;    //任务评分
    private int launcherImageId;
    private String launcherPhoneNumber;



    public TASK1(int taskId, String taskType, String subtask, String description, double launchtime,
                double deadline, double payment, boolean ifAccept, String taskLocation, int times,
                int process, int grade, int launcherImageId, String launcherPhoneNumber){
        this.taskId = taskId;
        this.taskType = taskType;
        this.subtask = subtask;
        this.description = description;
        this.launchtime = launchtime;
        this.deadline = deadline;
        this.payment = payment;
        this.ifAccept = ifAccept;
        this.taskLocation = taskLocation;
        this.times = times;
        this.process = process;
        this.grade = grade;
        this.launcherImageId = launcherImageId;
        this.launcherPhoneNumber = launcherPhoneNumber;
    }

    public String getLauncherName(){
        return launcherName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLaunchtime() {
        return launchtime;
    }

    public void setLaunchtime(double launchtime) {
        this.launchtime = launchtime;
    }

    public double getDeadline() {
        return deadline;
    }

    public void setDeadline(double deadline) {
        this.deadline = deadline;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public boolean isIfAccept() {
        return ifAccept;
    }

    public void setIfAccept(boolean ifAccept) {
        this.ifAccept = ifAccept;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

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


