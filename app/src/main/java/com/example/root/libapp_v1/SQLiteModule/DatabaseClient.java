package com.example.root.libapp_v1.SQLiteModule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        mDbOpenHelper = new DbOpenHelper(context);
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
            id = database.insert("personpage", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don't happen please!
             * */
            if (database != null)
                database.close();
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
            if (database != null) {
                database.close();
            }
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
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
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
            if (database != null) {
                database.close();
            }
        }
        return cursor;
    }


}
