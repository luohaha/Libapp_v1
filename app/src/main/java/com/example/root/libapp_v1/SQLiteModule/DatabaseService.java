package com.example.root.libapp_v1.SQLiteModule;

import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-29.
 */
public interface DatabaseService {
    public boolean addData();
    public boolean deleteData();
    public boolean updateData();
    public Map<String, String> viewData(String[] selectionArgs);
    public List<Map<String, String>> listDataMap(String[] selectionArgs);
}
