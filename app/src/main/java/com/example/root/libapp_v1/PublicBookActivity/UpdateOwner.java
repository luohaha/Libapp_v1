package com.example.root.libapp_v1.PublicBookActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.root.libapp_v1.HttpModule.DoGetAndPost;

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
        HttpTask httpTask = new HttpTask();
        httpTask.execute();
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
                Dialog dialog = new AlertDialog.Builder(mContext).setTitle("订书成功")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

}
