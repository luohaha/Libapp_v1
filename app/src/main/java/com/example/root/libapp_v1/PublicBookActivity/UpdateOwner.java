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
}
