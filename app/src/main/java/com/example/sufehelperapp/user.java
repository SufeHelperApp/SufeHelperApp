package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

import java.util.List;

public class user extends DataSupport {

    //基本信息
    //TODO: 数据库设计：完成未完成的set和get方法(黄色的)
    private String phonenumber;
    private String password;
    private String myName;   //姓名
    private String sex;
    private String nickname;   //昵称
    private String gender;
    private int myImageId;//个人头像  //TODO: 改成String
    private boolean isValid; //是否被冻结

    //个性信息
    private String dormArea;
    private String dormitoryLocation;
    private double dormitoryX;
    private double dormitoryY; //寝室位置
    private List<String> demand; //常见需求
    private List<String> specialty;  //个人特长

    //任务信息
    private int taskLNum;  //发布任务总数
    private int taskRNum;  //接受任务总数
    private int taskNum;  //任务总数
    private int curr_taskLNum;  //未完成的发布任务总数
    private int curr_taskRNum;  //未完成的接受任务总数
    private int default_taskNum;  //违约任务总数  //TODO: 如何在某个时间触发某个方法？

    //评价信息
    //TODO: 对接：任务评价页面：新评分加进平均评分
    private double averageScore;    //平均评分
    private int credit; //积分总量
    //TODO: 数据库设计：1.积分增加/减少函数 2.若积分总量少于0，账户被冻结isValid=false 3.建立各行为（接受任务+30，
    //TODO: 发布任务+15，违约-60，*登录+2，*在线时长超过六小时+1，*被邀请+5，*接受邀请+10，*平均评分增减+1：+10）和积分总量之间的对应增减关系（积分算法）
    //TODO: 数据库设计：建立积分与等级的对应关系（等级算法）
    private int level; //个人等级(1-10)


    //TODO*: 补充数据库设计：如何记录登录次数和登录时长？
    //private int loginTimes;
    //private int loginDuration;
    //TODO*: 补充数据库设计：优先级：1. 数据库默认查找指标：截止时间倒序，优先级顺序 2. 购买优先级10分耗费50积分。有效期为2周
    // private int priority; // 个人等级(1-10)
    //TODO*: 补充数据库设计：当前积分=积分总量-优先级的积分消费总量。当前积分不能小于0，若某次消费积分后小于零，禁止此次购买


    //达人信息
    private boolean ifTalent;  //是否是达人
    //TODO: 对接：获取所有达人称号字符串：显示在"我的达人"页面。
    private List<String> talentTitles;
    //private int invitedTimes;  //被邀请多少次


    //默认构造函数
    public user(){
        this.isValid = true;
        this.taskLNum=0;
        this.taskRNum=0;
        this.taskNum=0;
        this.curr_taskLNum=0;
        this. curr_taskRNum=0;
        this.default_taskNum=0;
        this.averageScore=-1;
        this.credit=0;
        //this.credit=0;
        this.level=1;
        //this.priority=0;
        this.ifTalent=false;
        this.talentTitles = null;
        //this.invitedTimes=0;
    }


    //完整构造函数

/*
    public user(String phonenumber, String myName, String nickname, String gender, String password,
                int myImageId, String dormitoryLocation, String demand, String specialty,
                String talent, int credit, int level, int tasknumber){
        this.phonenumber = phonenumber;
        this.myName = myName;
        this.nickname = nickname;
        this.gender = gender;
        this.password = password;
        this.myImageId = myImageId;
        this.dormitoryLocation = dormitoryLocation;
        this.demand = demand;
        this.specialty = specialty;
        this.talent = talent;
        this.credit = credit;
        this.level = level;
        this.tasknumber = tasknumber;
    }
    */

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getDormitoryX() {
        return dormitoryX;
    }

    public void setDormitoryX(double dormitoryX) {
        this.dormitoryX = dormitoryX;
    }

    public double getDormitoryY() {
        return dormitoryY;
    }

    public void setDormitoryY(double dormitoryY) {
        this.dormitoryY = dormitoryY;
    }

    public void setDemand(List<String> demand) {
        this.demand = demand;
    }

    public void setSpecialty(List<String> specialty) {
        this.specialty = specialty;
    }

    public int getTaskRNum() {
        return taskRNum;
    }

    public void setTaskRNum(int taskRNum) {
        this.taskRNum = taskRNum;
    }

    public int getCurr_taskLNum() {
        return curr_taskLNum;
    }

    public void setCurr_taskLNum(int curr_taskLNum) {
        this.curr_taskLNum = curr_taskLNum;
    }

    public int getCurr_taskRNum() {
        return curr_taskRNum;
    }

    public void setCurr_taskRNum(int curr_taskRNum) {
        this.curr_taskRNum = curr_taskRNum;
    }

    public int getDefault_taskNum() {
        return default_taskNum;
    }

    public void setDefault_taskNum(int default_taskNum) {
        this.default_taskNum = default_taskNum;
    }

    public boolean isIfTalent() {
        return ifTalent;
    }

    public void setIfTalent(boolean ifTalent) {
        this.ifTalent = ifTalent;
    }

    public List<String> getTalentTitles() {
        return talentTitles;
    }

    public void setTalentTitles(List<String> talentTitles) {
        this.talentTitles = talentTitles;
    }

    public void setDormitoryLocation(String dormitoryLocation){
        this.dormitoryLocation = dormitoryLocation;
    }

    public String getDormitoryLocation(){
        return dormitoryLocation;
    }

    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getPhonenumber(){
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber){
        this.phonenumber = phonenumber;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void setName(String name) {
        this.myName = name;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMyImageId() {
        return myImageId;
    }

    public void setMyImageId(int myImageId) {
        this.myImageId = myImageId;
    }

    public String getDormArea() {
        return dormArea;
    }

    public void setDormArea(String area) {
        this.dormArea = area;
    }

    public List<String> getDemand() {
        return demand;
    }

    public List<String> getSpecialty() {
        return specialty;
    }

    public List<String> getTalent() {
        return talentTitles;
    }

    //TODO: 达人称号动态添加
    public void addTalentTitle(String talent) {

    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int tasknumber) {
        this.taskNum = tasknumber;
    }

    public int getTaskLNum() {
        return taskLNum;
    }

    public void setTaskLNum(int TaskLNum) {
        this.taskLNum = TaskLNum;
    }
}



