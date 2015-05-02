package com.example.root.libapp_v1.SQLiteModule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 *
 * I don't like any more.
 */
public class PersonpageClient implements DatabaseService {
    private DbOpenHelper mDbOpenHelper = null;

    public PersonpageClient(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    @Override
    public boolean addData(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            /**
             * choose sql word by table's name
             * */
             String sql = "insert into personpage(name,quote,img,address,mailbox,phone," +
                            "books_number,record_number) values(?,?,?,?,?,?,?,?)";
             database = mDbOpenHelper.getWritableDatabase();
             database.execSQL(sql);
             flag = true;
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
    public boolean deleteData(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            String sql = "delete from personpage where id = ?";
            database = mDbOpenHelper.getWritableDatabase();
            database.execSQL(sql);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateData(Object[] params) {
        boolean flag = false;
        SQLiteDatabase sqLiteDatabase = null;
        /**
         * using the table's name to decide which sql word to use
         * */
        try {
            String sql = "update personpage set quote=?," +
                            "address=?,mailbox=?,phone=? where id=?";
            sqLiteDatabase = mDbOpenHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(sql);
            flag = true;

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
    public Map<String, String> viewData(String[] selectionArgs) {
        Map<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = null;
        String sql = "";
        try {
            sql = "select * from personpage where id=?";

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
    public List<Map<String, String>> listDataMap(String[] selectionArgs) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select * from personpage";
        SQLiteDatabase database = null;
        try {
            database = mDbOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, selectionArgs);
            int colums = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < colums; i++) {
                    String name = cursor.getColumnName(i);
                    String value = cursor.getString(i);
                    if (name == null) {
                        name = "";
                    }
                    map.put(name, value);
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }
}
