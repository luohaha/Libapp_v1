package com.example.root.libapp_v1.SQLiteModule.Personpage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by root on 15-5-2.
 */
public class PersonpageProvider extends ContentProvider {
    private final String TAG = "Personpage";
    private PersonpageClient mPersonpageClient = null;
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
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        int flag = URI_MATCHER.match(uri);
        /**
         * the uri match, and then it will add a record into table
         * */
        if (flag == ALL_ID) {

        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        mPersonpageClient = new PersonpageClient(getContext());
        return true;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
