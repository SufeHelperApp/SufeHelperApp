package com.example.sufehelperapp;

public class task {

    private String launcherName;
    private int taskId;
    private int launcherImageId;
    private String launcherPhoneNumber;
    private String location;
    private String deadline;
    private String payment;
    private String taskType;

    /*
    private String subtaskType;
    private String description;
    private boolean ifAccept;
    private int times;
    private String process;
    private int grade;
    */

    public task(int taskId, String launcherName, int launcherImageId, String launcherPhoneNumber, String taskType,
                String location, String deadline, String payment){
        this.taskId = taskId;
        this.launcherName = launcherName;
        this.launcherImageId = launcherImageId;
        this.launcherPhoneNumber = launcherPhoneNumber;
        this.taskType = taskType;
        this.location = location;
        this.deadline = deadline;
        this.payment = payment;
    }

    public String getLauncherName(){
        return launcherName;
    }

    public int getImageId(){
        return launcherImageId;
    }

    public String getLauncherPhoneNumber(){
        return launcherPhoneNumber;
    }

    public String getTaskType(){
        return taskType;
    }

    public String getLocation(){
        return location;
    }

    public String getDeadline(){
        return deadline;
    }

    public String getPayment(){
        return payment;
    }

    public int getTaskId(){return taskId;}

}


