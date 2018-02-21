package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Explore_weekly_interal {
    private String name;
    private int imageID;

    public Explore_weekly_interal(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }
    public String getName() {
        return name;
    }
    public int getImageID() {
        return imageID;
    }
}
