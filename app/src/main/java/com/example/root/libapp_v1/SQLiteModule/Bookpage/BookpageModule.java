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
    private String mGetUrl = "";
    public BookpageModule(Context context){
        this.mContext = context;
    }

    public void insertDb() {

        DatabaseClient databaseClient = new DatabaseClient(mContext);
        for (int i = 0; i < 20; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "the book's name : "+String.valueOf(i));
            //contentValues.put("short_detail", "hey, I am no."+String.valueOf(i));
            contentValues.put("img", "http://203club.com/wp-content/uploads/2015/04/shuping1.jpg");
            long id = databaseClient.insertData("bookpage", contentValues);
            Log.i("fuck you : ", String.valueOf(id));
        }

    }

    public void updateDb() {
        HttpTask httpTask = new HttpTask();
        httpTask.execute();
    }
    /**
     * refresh the data by getting from http
     * */
    private JSONObject getDataFromHttp() {

        try {
            JSONObject jsonObject = DoGetAndPost.doGet(mGetUrl);
            /**
             * set the data which we get from http server
             * */
            JSONArray userInfo = jsonObject.getJSONArray("userInfo");
            JSONObject userOne = userInfo.getJSONObject(0);
            return userOne;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * clss HttpTask : which using AsyncTask to open a new thread to download data in back.
     */
    private class HttpTask extends AsyncTask<String, Integer, JSONObject> {
        private HttpTask() {
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject jsonObject = getDataFromHttp();
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
        protected void onPostExecute(JSONObject s) {
            try {
                ContentResolver contentResolver = mContext.getContentResolver();
                // mPersonpageName.setText((String)s.get("username"));
                Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", (String)s.get("username"));
                contentResolver.update(uri, contentValues, null, null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
}
