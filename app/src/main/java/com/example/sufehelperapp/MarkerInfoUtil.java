package com.example.sufehelperapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/4/18.
 * 实现序列化接口
 * 地图标注信息实体类
 */

public class MarkerInfoUtil implements Serializable{
    private static final long serialVersionUID = -758459502806858414L;//8633299996744734593L

    private double latitude;//维度
    private double longitude;//经度
    private String name;//名字
    //private int imgId;//图片
    private String sum;//描述
    private String distance; //距离

    public List<MarkerInfoUtil> infos = new ArrayList<MarkerInfoUtil>();
    private void setMarkerInfo() {
        infos.add(new MarkerInfoUtil(31.30709098883,
                121.49509906768697,
                "上海财经大学",
                "ShangHai")
        );
    }

    //构造方法
    public MarkerInfoUtil() {}
    public MarkerInfoUtil(double latitude,
                          double longitude,
                          String name,
                          String sum) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        //this.imgId = imgId;
        this.sum = sum;
    }
    //toString方法
    @Override
    public String toString() {
        return "MarkerInfoUtil [latitude=" + latitude + ",longitude=" + longitude + ",name=" + name+ ",sum=" + sum + "]";
    }
    //getter setter
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //public int getImgId() {return imgId;}
    //public void setImgId(int imgId) {this.imgId = imgId;}
    public String getsum() {
        return sum;
    }
    public void setsum(String description) {
        this.sum = description;
    }

}
