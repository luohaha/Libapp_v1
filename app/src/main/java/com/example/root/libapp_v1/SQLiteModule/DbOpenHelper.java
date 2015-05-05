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
    private static DbOpenHelper mDbOpenHelper;
    /**
     * using the singleton
     * */
    public static synchronized DbOpenHelper getInstance (Context context) {
        if (mDbOpenHelper == null) {
            mDbOpenHelper = new DbOpenHelper(context.getApplicationContext());
        }
        return mDbOpenHelper;
    }
    public DbOpenHelper(Context context) {
        super(context, mDataBaseName, null, mDataBaseVersion);
    }
    /**
     * create new data base, it will exec the first time, and it won't start next time,
     * we can use it to init
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * create personpage table
         * */
        String sql = "create table personpage(id integer primary key autoincrement, name varchar(64)," +
                "quote varchar(128), img varchar(64), address varchar(256)," +
                "mailbox varchar(64), password varchar(64), account_number varchar(64), phone varchar(64), books_number varchar(64)," +
                "record_number varchar(64))";
        db.execSQL(sql);
        /**
         * create bookpage table
         * */
        sql = "create table bookpage(id integer primary key autoincrement, name varchar(64)," +
                " unique_id varchar(64), img varchar(64), detail_info varchar(512), short_detail varchar(64)," +
                "author_info varchar(512), catalog_info varchar(512))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
