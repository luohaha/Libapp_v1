package com.example.root.libapp_v1.SQLiteModule.Personpage;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-4-29.
 */
public class PersonpageModule {
    private Context mContext;
    /**
     *the url we get from
     */
    private String mGetUrl;
    public PersonpageModule(Context context){
        this.mContext = context;
        this.mGetUrl = this.mContext.getResources().getString(R.string.app_url)+"check_user.php?username=admin&password=123";
    }

    public void insertDb() {
        /*
        DatabaseClient databaseClient = new DatabaseClient(mContext);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "luohaha");
        contentValues.put("quote", "don't tell me this !!!");
        contentValues.put("img", "nullimg");
        contentValues.put("address", "Hainan, China");
        contentValues.put("mailbox", "965166527@qq.com");
        contentValues.put("phone", "18810541851");
        contentValues.put("books_number", "12");
        contentValues.put("record_number", "21");
        long id = databaseClient.insertData("personpage", contentValues);
        Log.i("fuck you : ", String.valueOf(id));*/

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
