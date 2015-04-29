package com.example.root.libapp_v1.SQLiteModule;

import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 */
public interface DatabaseService {
    public boolean addData(String tableName, Object[] params);
    public boolean deleteData(String tableName, Object[] params);
    public boolean updateData(String tableName, Object[] params);
    public Map<String, String> viewData(String tableName, String[] selectionArgs);
    public List<Map<String, String>> listDataMap(String tableName, String[] selectionArgs);
}
