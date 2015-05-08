package com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-5-8.
 */
public class PersonOwnBookpageModule {
    private String mUrl = "http://192.168.0.153/android/get_book?flag=owner";
    private Context mContext;
    private String mOwner;

    public PersonOwnBookpageModule(Context mContext) {
        this.mContext = mContext;
    }

    public void refreshDb() {
        HttpTask httpTask = new HttpTask();
        httpTask.execute();
    }

    /**
     * refresh the data by getting from http
     * */
    private JSONArray getDataFromHttp() {

        try {
            DatabaseClient databaseClient = new DatabaseClient(mContext);
            /**
             * get the person name from table personpage
             * */
            String[] args = new String[] {"1"};
            Cursor cursor = databaseClient.queryData("personpage", "id=?", args);
            if (!cursor.moveToFirst()) {
                mOwner = null;
                Dialog dialog = new AlertDialog.Builder(mContext).setTitle("失败")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            } else {
                while (cursor.moveToNext()) {
                    mOwner = cursor.getString(cursor.getColumnIndex("name"));
                }
            }
            JSONObject jsonObject = DoGetAndPost.doGet(mUrl+"&param="+mOwner);
            /**
             * if the return jsonObject is null, then don't clear the table
             * */
            if (jsonObject != null) {
                databaseClient.clearPersonOwnBookPage();
            }

            JSONArray array = jsonObject.getJSONArray("book");
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * clss HttpTask : which using AsyncTask to open a new thread to download data in back.
     */
    private class HttpTask extends AsyncTask<String, Integer, JSONArray> {
        private HttpTask() {
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                JSONArray jsonObject = getDataFromHttp();
                /**
                 * using publicProgress() to update progress bar's status
                 * */
                // publishProgress(100);
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            try {
                ContentResolver contentResolver = mContext.getContentResolver();
                Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageProvider/personownbookpage");
                for (int i = 0; i < array.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", array.getJSONObject(i).getString("name"));
                    contentValues.put("detail_info", array.getJSONObject(i).getString("detail_info"));
                    contentValues.put("author_info", array.getJSONObject(i).getString("author_info"));
                    contentValues.put("unique_id", array.getJSONObject(i).getString("id"));
                    contentValues.put("catalog_info", array.getJSONObject(i).getString("catalog_info"));
                    Uri tmp = contentResolver.insert(uri, contentValues);
                    //Log.i("get book from http --> ", tmp.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
}
