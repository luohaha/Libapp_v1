package com.example.root.libapp_v1.SQLiteModule;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 */
public interface DatabaseService {
    public long insertData(String tableName, ContentValues contentValues);

    public int deleteData(String tableName, String whereClause, String[] whereArgs);

    public int updateData(String tableName, ContentValues contentValues, String whereClause,
                          String[] whereArgs);

    public Cursor queryData(String tableName, String selectionClause, String[] selectionArgs);
}