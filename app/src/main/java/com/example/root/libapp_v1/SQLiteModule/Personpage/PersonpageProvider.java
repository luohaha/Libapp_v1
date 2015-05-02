package com.example.root.libapp_v1.SQLiteModule.Personpage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;


/**
 * Created by Yixin on 15-5-2.
 */
public class PersonpageProvider extends ContentProvider {
    private String mTableName = "personpage";
    private final String TAG = "personpage";
    private DatabaseClient mDatabaseClient = null;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_ID = 1;
    private static final int ONE_ID = 2;
    static {
        /**
         * want to use all record in table personpage
         * if it match, and then return 1
         * */
        URI_MATCHER.addURI("com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider",
                "personpage", ALL_ID);
        /**
         * want to use the record which id equal to #, and then return 2
         * */
        URI_MATCHER.addURI("com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider",
                "personpage/#", ONE_ID);
    }

    /**
     * insert into uri and return the new record's uri
     * @param uri
     * @param values
     * @return new record's uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        int flag = URI_MATCHER.match(uri);
        /**
         * the uri match, and then it will add a record into table
         * */
        if (flag == ALL_ID) {
            long id = mDatabaseClient.insertData(mTableName, values);
            result = ContentUris.withAppendedId(uri, id);
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        int flag = URI_MATCHER.match(uri);
        String type = null;
        switch (flag) {
            case ONE_ID :
                type = "vnd.android.cursor.item/personpage";
                break;
            case ALL_ID :
                type = "vnd.android.cursor.dir/personpages";
                break;
        }
        return type;
    }

    @Override
    public boolean onCreate() {
        mDatabaseClient = new DatabaseClient(getContext());
        return true;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = -1;
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case ALL_ID :
                    count = mDatabaseClient.updateData(mTableName, values, selection, selectionArgs);
                    break;
                case ONE_ID :
                    long id = ContentUris.parseId(uri);
                    String selectionClause = "id = ?";
                    String[] args = {String.valueOf(id)};
                    count = mDatabaseClient.updateData(mTableName, values, selectionClause, args);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * if the uri contain id then it delete the record which id equal to this id,
     * else use the selection and selectionArgs to decide which records should be deleted
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = -1;
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case ALL_ID :
                    count = mDatabaseClient.deleteData(mTableName, selection, selectionArgs);
                    break;
                case ONE_ID :
                    long id = ContentUris.parseId(uri);
                    String selectionClause = "id = ?";
                    String[] args = {String.valueOf(id)};
                    count = mDatabaseClient.deleteData(mTableName, selectionClause, args);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor = null;
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case ALL_ID :
                    cursor = mDatabaseClient.queryData(mTableName, selection, selectionArgs);
                    break;
                case ONE_ID :
                    long id = ContentUris.parseId(uri);
                    String selectionClause = "id = ?";
                    String[] args = {String.valueOf(id)};
                    cursor = mDatabaseClient.queryData(mTableName, selectionClause, args);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }
}
