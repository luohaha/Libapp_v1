package com.example.root.libapp_v1.SQLiteModule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.root.libapp_v1.SQLiteModule.DatabaseService;
import com.example.root.libapp_v1.SQLiteModule.DbOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 *
 * I don't like any more.
 */
public class DatabaseClient implements DatabaseService {
    private DbOpenHelper mDbOpenHelper = null;

    public DatabaseClient(Context context) {
        mDbOpenHelper = DbOpenHelper.getInstance(context);
    }

    @Override
    public long insertData(String tableName, ContentValues contentValues) {
        long id = -1;
        SQLiteDatabase database = null;
        try {
            /**
             * insert data
             * */

            database = mDbOpenHelper.getWritableDatabase();
            id = database.insert(tableName, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don not close database now
             * */
        }
        return id;
    }

    @Override
    public int deleteData(String tableName, String whereClause, String[] whereArgs) {
        int count = -1;
        SQLiteDatabase database = null;
        try {
            database = mDbOpenHelper.getWritableDatabase();
            count = database.delete(tableName, whereClause, whereArgs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don not close database now
             * */
        }
        return count;
    }

    @Override
    public int updateData(String tableName, ContentValues contentValues, String whereClause,
                          String[] whereArgs) {
        int count = -1;
        SQLiteDatabase sqLiteDatabase = null;
        /**
         * using the table's name to decide which sql word to use
         * */
        try {

            sqLiteDatabase = mDbOpenHelper.getWritableDatabase();
            count = sqLiteDatabase.update(tableName, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don not close database now
             * */
        }
        return count;
    }

    @Override
    public Cursor queryData(String tableName, String selectionClause, String[] selectionArgs) {
        Cursor cursor = null;
        SQLiteDatabase database = null;
        try {

            database = mDbOpenHelper.getReadableDatabase();
            cursor = database.query(true, tableName, null, selectionClause, selectionArgs, null,
                    null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don not close database now
             * */
        }
        return cursor;
    }

    private void close(SQLiteDatabase database) {
        if (database != null && database.isOpen()) {
            try {
                database.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("database", "already close");
        }
    }

}
