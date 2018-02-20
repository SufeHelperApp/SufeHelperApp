package com.example.sufehelperapp;
import org.litepal.crud.DataSupport;

public class user extends DataSupport {
    private String phonenumber;
    private String myName;   //姓名
    private String nickname;   //昵称
    private String gender;
    private String password;
    private int myImageId;//个人头像
    private String dormitoryLocation;  //寝室位置
    private String demand; //常见需求
    private String specialty;  //个人特长
    private String talent;    //达人称号
    private int credit;    //信用等级
    private int level; //个人等级
    private int tasknumber;    //任务发布及完成量


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

    public String getDormitoryLocation() {
        return dormitoryLocation;
    }

    public void setDormitoryLocation(String dormitoryLocation) {
        this.dormitoryLocation = dormitoryLocation;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
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

    public int getTasknumber() {
        return tasknumber;
    }

    public void setTasknumber(int tasknumber) {
        this.tasknumber = tasknumber;
    }
}



