package com.example.root.libapp_v1.SQLiteModule;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 */
public class DatabaseClient implements DatabaseService {
    private DbOpenHelper mDbOpenHelper = null;

    public DatabaseClient(DbOpenHelper mDbOpenHelper) {
        this.mDbOpenHelper = mDbOpenHelper;
    }

    @Override
    public boolean addData(String tableName, Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            String sql = "insert into "+tableName+"(?,?,?) values(?,?,?)";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * don't happen please!
             * */
            if (database != null)
                database.close();
        }
        return flag;
    }

    @Override
    public boolean deleteData() {
        return false;
    }

    @Override
    public boolean updateData() {
        return false;
    }

    @Override
    public Map<String, String> viewData(String[] selectionArgs) {
        return null;
    }

    @Override
    public List<Map<String, String>> listDataMap(String[] selectionArgs) {
        return null;
    }
}
