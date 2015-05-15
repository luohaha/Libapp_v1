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
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-5-8.
 */
public class PersonOwnBookpageModule {
    private String mUrl;
    private Context mContext;
    private String mOwner;

    public PersonOwnBookpageModule(Context mContext) {
        this.mContext = mContext;
        this.mUrl = this.mContext.getResources().getString(R.string.app_url)+"get_book.php?flag=owner";
    }

    public void refreshDb() {
        //HttpTask httpTask = new HttpTask();
        //httpTask.execute();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        String owner = "";
        while (cursor.moveToNext()) {
            owner = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        Ion.with(mContext)
                .load(mUrl+"&param="+owner)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            DatabaseClient databaseClient = new DatabaseClient(mContext);
                            /**
                             * if the return jsonObject is null, then don't clear the table
                             * */
                            if (result != null) {
                                databaseClient.clearTablePage("personownbookpage");
                            }
                            JsonArray array = result.getAsJsonArray("book");
                            ContentResolver contentResolver = mContext.getContentResolver();
                            Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageProvider/personownbookpage");
                            for (int i = 0; i < array.size(); i++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", array.get(i).getAsJsonObject().get("name").getAsString());
                                contentValues.put("detail_info", array.get(i).getAsJsonObject().get("detail_info").getAsString());
                                contentValues.put("author_info", array.get(i).getAsJsonObject().get("author_info").getAsString());
                                contentValues.put("unique_id", array.get(i).getAsJsonObject().get("id").getAsString());
                                contentValues.put("catalog_info", array.get(i).getAsJsonObject().get("catalog_info").getAsString());
                                contentValues.put("timestamp", array.get(i).getAsJsonObject().get("timestamp").getAsString());
                                Uri tmp = contentResolver.insert(uri, contentValues);
                                //Log.i("get book from http --> ", tmp.toString());
                            }
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }

                    }
                });
    }

}
