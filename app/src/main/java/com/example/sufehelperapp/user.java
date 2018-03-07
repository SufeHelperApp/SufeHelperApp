package com.example.sufehelperapp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.litepal.crud.DataSupport;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class user extends DataSupport implements Serializable {

    private boolean isValid; //TODO: 是否被冻结（信用评价页-违约, 我的任务）。
    private byte[] headshot;//头像
    private boolean ifClicked;

    //基本信息
    private String phonenumber; //手机号（注册1）
    private String password; //密码（注册2）
    private String myName;   //用户名（注册2）
    private String sex; //性别（注册2）
    private int myImageId;//TODO：头像（注册3）

    //个性信息
    private String dormArea; //寝室校区（注册3）
    private String dormitoryLocation; //寝室名（注册3）
    private List<String> demand; //TODO: 常见需求（注册3）
    private List<String> specialty;  //TODO: 个人特长（注册3）

    //任务信息
    private int taskNum;  //任务总数（发布页，信息页）。
    private int taskLNum;  //发布任务总数（发布页）。
    private int taskRNum;  //接受任务总数（信息页）。

    private int taskRNum_errand; //跑腿任务总数（信息页）。
    public int taskRNum_e1=0;
    public int taskRNum_e2=0;
    public int taskRNum_e3=0;
    public int taskRNum_e4=0;
    public int taskRNum_e5=0;

    public int taskRNum_skill; //技能任务总数（信息页）。
    public int taskRNum_s1=0;
    public int taskRNum_s2=0;
    public int taskRNum_s3=0;
    public int taskRNum_s4=0;
    public int taskRNum_s5=0;


    public int taskRNum_counsel; //咨询任务总数（信息页）。
    public int taskRNum_c1=0;
    public int taskRNum_c2=0;
    public int taskRNum_c3=0;
    public int taskRNum_c4=0;
    public int taskRNum_c5=0;

    private int default_taskNum;  //TODO：违约任务总数（alarm）。

    //评价信息
    private float averageScore;    //平均评分（信用评价页）。

    //TODO: 数据库设计（积分算法）：2.若积分总量少于0，账户被冻结isValid=false 3.建立各行为（接受任务+30，
    //TODO: 发布任务+15，违约-60，*登录+2，*在线时长超过六小时+1，*被邀请+5，*接受邀请+10，
    //TODO: *平均评分增减+1：+10）和积分总量之间的对应增减关系
    private int credit; //积分总量（个人主页）。

    //TODO: 数据库设计（等级算法）：建立积分与等级的对应关系
    private int level; //个人等级(个人主页，他人主页)。


    //补充数据库设计：如何记录登录次数和登录时长？
    //private int loginTimes;
    //private int loginDuration;
    //补充数据库设计：优先级：1. 数据库默认查找指标：截止时间倒序，优先级顺序 2. 购买优先级10分耗费50积分。有效期为2周
    // private int priority; // 个人等级(1-10)
    //补充数据库设计：当前积分=积分总量-优先级的积分消费总量。当前积分不能小于0，若某次消费积分后小于零，禁止此次购买


    //达人信息
    private boolean ifTalent;  //TODO: 是否是达人（达人榜）。
    //TODO: 对接：获取所有达人称号字符串：显示在"我的达人"页面。
    private List<String> talentTitles = new ArrayList<>();  //TODO: 达人称号（达人榜，我的达人）。
    //private int invitedTimes;  //被邀请多少次

    //消息
    private int msg=0;
    private List<String> msgTaskList = new ArrayList<>();

    public void updateTalentTitles(){

        this.talentTitles = new ArrayList<>();
        this.talentTitles.clear();

        if(this.taskRNum_e1 >= 3){
            this.talentTitles.add("占座达人");
        }
        if(this.taskRNum_e2 >= 3){
            this.talentTitles.add("拿快递达人");
        }
        if(this.taskRNum_e3 >= 3){
            this.talentTitles.add("买饭达人");
        }
        if(this.taskRNum_e4 >= 3){
            this.talentTitles.add("买东西达人");
        }
        if(this.taskRNum_e5 >= 3){
            this.talentTitles.add("拼单达人");
        }
        if(this.taskRNum_s1 >= 3){
            this.talentTitles.add("电子产品修理达人");
        }
        if(this.taskRNum_e2 >= 3){
            this.talentTitles.add("家具器件组装达人");
        }
        if(this.taskRNum_e3 >= 3){
            this.talentTitles.add("学习作业辅导达人");
        }
        if(this.taskRNum_e4 >= 3){
            this.talentTitles.add("技能培训达人");
        }
        if(this.taskRNum_e5 >= 3){
            this.talentTitles.add("找同好达人");
        }
        if(this.taskRNum_c1 >= 3){
            this.talentTitles.add("选课指南达人");
        }
        if(this.taskRNum_c2 >= 3){
            this.talentTitles.add("考研出国经验达人");
        }
        if(this.taskRNum_c3 >= 3){
            this.talentTitles.add("求职经验达人");
        }
        if(this.taskRNum_c4 >= 3){
            this.talentTitles.add("票务转让达人");
        }
        if(this.taskRNum_c5 >= 3){
            this.talentTitles.add("二手闲置达人");
        }

    }


    //默认构造函数
    public user(){
        this.headshot=headshot;
        this.isValid = true;
        this.taskLNum=0;
        this.taskRNum=0;
        this.taskRNum_errand=0;
        this.taskRNum_skill=0;
        this.taskRNum_counsel=0;
        this.taskNum=0;
        this.default_taskNum=0;
        this.averageScore=0;
        this.credit=0;
        this.level=1;
        this.ifTalent=false;
        this.talentTitles = null;
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

    //图片转换为字节
    private byte[]img(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //函数：头像

    public byte[] getHeadshot() {
        return headshot;
    }

    public void setHeadshot(byte[] headshot) {
        this.headshot = headshot;
    }


    //函数：冻结

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }


    //函数：基本信息

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


    //评分

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }



    //个性信息

    public String getDormArea() {
        return dormArea;
    }

    public void setDormArea(String area) {
        this.dormArea = area;
    }

    public void setDormitoryLocation(String dormitoryLocation){
        this.dormitoryLocation = dormitoryLocation;
    }

    public String getDormitoryLocation(){
        return dormitoryLocation;
    }

    public void addDemand(String d) {
        this.demand.add(d);
    }

    public void addSpecialty(String s) {
        this.specialty.add(s);
    }

    public void setDemand(List<String>d){this.demand = d;}

    public void setSpecialty(List<String>s){this.specialty = s;}

    public List<String> getDemand() {
        return demand;
    }

    public List<String> getSpecialty() {
        return specialty;
    }




    //函数：增减任务数量

    public int getTaskRNum() {
        return taskRNum;
    }

    public void addTaskRNum(int num) {
        this.taskRNum = taskRNum + num;
    }

    public void addTaskRNum_errand(int num) {
        this.taskRNum_errand = taskRNum_errand + num;
    }

    public int getTaskRNum_errand() {
        return taskRNum_errand;
    }

    public void addTaskRNum_skill(int num) {
        this.taskRNum_skill = taskRNum_skill + num;
    }

    public int getTaskRNum_skill() {
        return taskRNum_skill;
    }

    public void  addTaskRNum_counsel(int num) {
        this.taskRNum_counsel = taskRNum_counsel + num;
    }

    public int getTaskRNum_counsel() {
        return taskRNum_counsel;
    }

    public int getDefault_taskNum() {
        return default_taskNum;
    }

    public void addTaskNum(int num) {
        this.taskNum = taskNum + num;
    }

    public void addDefault_taskNum(int num) {
        this.default_taskNum = default_taskNum + num;
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

    public void addTaskLNum(int num) {
        this.taskLNum = taskLNum + num;
    }


    //函数：达人

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

    public void addTalentTitle(String talent) {
        talentTitles.add(talent);
    }


    //函数：积分/等级/评分

    public void increaseCredit(int num){
        this.credit = credit + num;
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

    public void addToAverageScore(float newScore){
        if(averageScore == 0){
            averageScore = averageScore + newScore;
        }else {
            this.averageScore = (averageScore * (taskRNum - 1) + newScore) / taskRNum;
        }
    }


    //消息

    public int getMsg(){
        return this.msg;
    }

    public void setMsg(int i){
        this.msg = i;
    }

    public void clearMsg(){this.msg = 0;}

    public void addMsg(){
        this.msg++;
    }

    public void addMsgTaskList(String time){
        this.msgTaskList.add(time);
    }

    public List<String> getMsgTaskList(){
        return this.msgTaskList;
    }

    public void clearMsgTaskList(){
        this.msgTaskList = new ArrayList<>();
    }

    public void setIfClicked(boolean i){
        this.ifClicked = i;
    }

    public boolean getIfClicked(){
        return ifClicked;
    }


}



