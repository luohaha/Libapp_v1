package com.example.root.libapp_v1.SQLiteModule.Bookpage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by root on 15-5-5.
 */
public class BookpageModule {
    private Context mContext;
    /**
     *the url we get from
     */
    private String mGetUrl = "http://192.168.0.153/android/get_booklist.php?start=0&count=20";
    public BookpageModule(Context context){
        this.mContext = context;
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
            JSONObject jsonObject = DoGetAndPost.doGet(mGetUrl);
            DatabaseClient databaseClient = new DatabaseClient(mContext);
            /**
             * if the return jsonObject is null, then don't clear the table
             * */
            if (jsonObject != null) {
                databaseClient.clearBookPage();
            }

            JSONArray array = jsonObject.getJSONArray("books");
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
                Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
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
