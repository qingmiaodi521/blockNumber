package com.example.com.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by BlackWhite on 15/7/28.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

    public static final  String CREATE_BLOCK = "create table block("
            + "id integer primary key autoincrement," +
            "name text," +
            "number text)";
    private Context mcontext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BLOCK);
        Log.e("create table", "create table success");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
