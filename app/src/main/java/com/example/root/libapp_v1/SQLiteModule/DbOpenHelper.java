package com.example.root.libapp_v1.SQLiteModule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 15-4-29.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    /**
     * data base's name and version
     * */
    private static String mDataBaseName = "feishu.db";
    private static int mDataBaseVersion = 1;

    public DbOpenHelper(Context context) {
        super(context, mDataBaseName, null, mDataBaseVersion);
    }
    /**
     * create new data base, it will exec the first time, and it won't start next time,
     * we can use it to init
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table personpage(id integer primary key autoincrement, name varchar(64)," +
                "quote varchar(128), img varchar(64), address varchar(256)," +
                "mailbox varchar(64), phone varchar(64), books_number varchar(64)," +
                "record_number varchar(64))";
        db.execSQL(sql);
    }
    /**
     * change the database
     * */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
