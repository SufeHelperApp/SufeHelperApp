package com.example.sufehelperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Trace {

   /*
   时间
    */
   private String acceptTime;
   /*
   描述
    */
   private String acceptStation;


   public Trace(String acceptTime,String acceptStation) {
       this.acceptTime = acceptTime;
       this.acceptStation = acceptStation;
   }
   public String getAcceptTime() {
       return acceptTime;
   }
   public void setAcceptTime(String acceptTime) {
       this.acceptTime = acceptTime;
   }
   public String getAcceptStation() {
       return acceptStation;
   }
   public void setAcceptStation(String acceptStation) {
       this.acceptStation = acceptStation;
   }
}
