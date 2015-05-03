package com.example.root.libapp_v1.SQLiteModule;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

/**
 * Created by Yixin on 15-4-29.
 */
public class SQLiteModule {
    private Context mContext;
    public SQLiteModule(Context context){
        this.mContext = context;
    }

    public void insertDb() {
        DatabaseClient databaseClient = new DatabaseClient(mContext);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "luohaha");
        contentValues.put("quote", "don't tell me this !!!");
        contentValues.put("img", "nullimg");
        contentValues.put("address", "Hainan, China");
        contentValues.put("mailbox", "965166527@qq.com");
        contentValues.put("phone", "18810541851");
        contentValues.put("books_number", "12");
        contentValues.put("record_number", "21");
        long id = databaseClient.insertData("personpage", contentValues);
        Log.i("fuck you : ", String.valueOf(id));
    }
}
