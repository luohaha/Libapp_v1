package com.example.root.libapp_v1.SQLiteModule.Bookpage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.root.libapp_v1.Fragment.FirstFragmentAdapter.FirstFragmentAdapter;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.PublicBookActivity.PublicBookActivity;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-5-5.
 */
public class BookpageModule {
    private Context mContext;
    private View mView;
    private GridView mGridView;
    private FirstFragmentAdapter mAdapter;
    /**
     *the url we get from
     */
    private String mGetUrl;
    private String mImgUrl;
    public BookpageModule(Context context, View view, GridView gridView, FirstFragmentAdapter adapter){
        this.mContext = context;
        this.mView = view;
        this.mGridView = gridView;
        this.mAdapter = adapter;
        this.mGetUrl = this.mContext.getResources().getString(R.string.app_url)+"get_booklist.php?start=0&count=20";
        this.mImgUrl = this.mContext.getResources().getString(R.string.app_img_url);
    }

    public void refreshDb() {
       // HttpTask httpTask = new HttpTask();
       // httpTask.execute();
        Ion.with(mContext)
                .load(mGetUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            // do stuff with the result or error
                            DatabaseClient databaseClient = new DatabaseClient(mContext);
                            /**
                             * if the return jsonObject is null, then don't clear the table
                             * */
                            if (result != null) {
                                databaseClient.clearTablePage("bookpage");
                            }

                            JsonArray array = result.getAsJsonArray("books");
                            /**
                             * put data into db
                             * */
                            ContentResolver contentResolver = mContext.getContentResolver();
                            Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
                            for (int i = 0; i < array.size(); i++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", array.get(i).getAsJsonObject().get("name").getAsString());
                                contentValues.put("detail_info", array.get(i).getAsJsonObject().get("detail_info").getAsString());
                                contentValues.put("author_info", array.get(i).getAsJsonObject().get("author_info").getAsString());
                                contentValues.put("unique_id", array.get(i).getAsJsonObject().get("id").getAsString());
                                contentValues.put("catalog_info", array.get(i).getAsJsonObject().get("catalog_info").getAsString());
                                contentValues.put("timestamp", array.get(i).getAsJsonObject().get("timestamp").getAsString());
                                Uri tmp = contentResolver.insert(uri, contentValues);
                                initData(mView);
                            }
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }
    /**
     * init the test data
     * */
    private void initData(View view) {
                /*
        * the array of item which need to show in gridview
        * it contains string and a picture
        * */
        ArrayList<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();


        /**
         * download info from local database
         * */
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                String bookname = cursor.getString(cursor.getColumnIndex("name"));
                map.put("image", mImgUrl+"bookimg_"+bookname+".png");
                map.put("text", bookname);
                mList.add(map);
            }
            /**
             * use the new data to show in gridview
             * */
            initGridView(view, mList);
        }
        cursor.close();
    }
    /**
     * init the grid view and set adapter for it
     * */
    private void initGridView(View view, ArrayList<HashMap<String, Object>> mList) {
        mGridView=(GridView) view.findViewById(R.id.publicbook_gridview);
                /*
        * create a adapter
        * */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.firstfragment_textview);;
                Intent intent = new Intent(mContext, PublicBookActivity.class);
                intent.putExtra("bookname", textView.getText());
                mContext.startActivity(intent);
            }
        });
        if (mAdapter == null) {
            mAdapter = new FirstFragmentAdapter(mContext, mList,
                    R.layout.firstfragment_gridview_item);

            mGridView.setAdapter(mAdapter);
        } else {
            mAdapter.onDataChange(mList);
        }
    }


}
