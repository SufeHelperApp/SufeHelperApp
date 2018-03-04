package com.example.sufehelperapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table user ("
            + "phonenumber integer primary key , "
            + "headshot blob,"
            + "myName text, "
            + "nickname text,"
            + "gender text,"
            + "password integer,"
            + "myImageId integer,"
            + "dormitoryLocation text,"
            + "demand text,"
            + "specialty text,"
            + "talent text,"
            + "credit integer,"
            + "level integer,"
            + "tasknumber integer)";

    public static final String CREATE_TASK = "creat table task("
            + "taskId integer primary key ,"
            +"taskType text,"
            +"subtask text,"
            +"description text,"
            +"launchtime real,"
            +"deadline real,"
            +"payment real,"
            +"ifAccept blob,"
            +"taskLocation text,"
            +"times integer,"
            +"process integer,"
            +"grade integer,"
            +"launcherImageId integer,"
            +"launcherPhoneNumber text)";

    public static final String CREATE_WEEKLYDATA = "creat table weeklyData("
            +"phonenumber integer primary key ,"
            +"releasenumber integer,"
            +"acceptnumber integer,"
            +"mylaunchtime real,"
            +"mydeadline real)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_TASK );
        db.execSQL(CREATE_WEEKLYDATA);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        //db.execSQL("drop table if exists Category");
        //db.execSQL("drop table if exists weeklyData");
        onCreate(db);
    }
}



