package com.example.root.libapp_v1.PublicBookActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-5-8.
 */
public class UpdateOwner {
    private String mUrl = "http://192.168.0.153/android/update_owner.php";
    private String mBookId;
    private String mPersonName;
    private Context mContext;

    public UpdateOwner(String mBookId, String mPersonName, Context context) {
        this.mBookId = mBookId;
        this.mPersonName = mPersonName;
        this.mContext = context;
    }
    public void start() {
       // HttpTask httpTask = new HttpTask();
       // httpTask.execute();
        Ion.with(mContext)
                .load(mUrl + "?id="+mBookId+"&personname="+mPersonName)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error

                    }
                });
    }
    /**
     * refresh the data by getting from http
     * */
    private JSONObject getDataFromHttp() {

        try {
            JSONObject jsonObject = DoGetAndPost.doGet(mUrl + "?id="+mBookId+"&personname="+mPersonName);

            return jsonObject;
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
        protected void onPostExecute(JSONObject object) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

}
