package com.example.root.libapp_v1.SQLiteModule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 */
public class DatabaseClient implements DatabaseService {
    private DbOpenHelper mDbOpenHelper = null;

    public DatabaseClient(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    @Override
    public boolean addData(String tableName, Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            /**
             * choose sql word by table's name
             * */
            switch (tableName) {
                case "personpage" :
                    String sql = "insert into "+tableName+"(name,quote,img,address,mailbox,phone," +
                            "books_number,record_number) values(?,?,?,?,?,?,?,?)";
                    database = mDbOpenHelper.getWritableDatabase();
                    database.execSQL(sql);
                    flag = true;
                    break;
                default:
                    break;
            }
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
    public boolean deleteData(String tableName, Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            String sql = "delete from "+tableName+" where id = ?";
            database = mDbOpenHelper.getWritableDatabase();
            database.execSQL(sql);
            flag = true;
        } catch (Exception e) {

        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateData(String tableName, Object[] params) {
        boolean flag = false;
        SQLiteDatabase sqLiteDatabase = null;
        /**
         * using the table's name to decide which sql word to use
         * */
        try {
            switch (tableName) {
                case "personpage" :
                    String sql = "update "+tableName+" set quote=?," +
                            "address=?,mailbox=?,phone=? where id=?";
                    sqLiteDatabase = mDbOpenHelper.getWritableDatabase();
                    sqLiteDatabase.execSQL(sql);
                    flag = true;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> viewData(String tableName, String[] selectionArgs) {
        Map<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = null;
        String sql = "";
        try {
            switch (tableName) {
                case "personpage" :
                    sql = "select * from personpage where id=?";
                    break;
                default:
                    break;
            }
            database = mDbOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, selectionArgs);
            int colums = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> listDataMap(String tableName, String[] selectionArgs) {
        return null;
    }
}
