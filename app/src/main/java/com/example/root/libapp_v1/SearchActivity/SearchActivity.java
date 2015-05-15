package com.example.root.libapp_v1.SearchActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.LyxListView.LyxListViewAdapter;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.PublicBookActivity.PublicBookActivity;
import com.example.root.libapp_v1.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-5-10.
 */
public class SearchActivity extends Activity {
    private ListView mListView;
    private EditText mEditText;
    private BootstrapButton mButton;
    private String mUrl;
    private String mImgUrl;
    private List<Map<String, Object>> mList;
    private AsyncBitmapLoader mLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getResources().getString(R.string.app_url)+"get_book.php";
        this.mImgUrl = getResources().getString(R.string.app_img_url);
        setContentView(R.layout.activity_search);
        initBitmapLoader();
        initView();

    }

    /**
     * init bitmap loader
     * */
    private void initBitmapLoader() {
        mLoader = new AsyncBitmapLoader();
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_search_listview);
        mEditText = (EditText) findViewById(R.id.activity_search_edittext);
        mButton = (BootstrapButton) findViewById(R.id.activity_search_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    HttpTask httpTask = new HttpTask();
            //    httpTask.execute();
                Ion.with(SearchActivity.this)
                        .load(mUrl + "?flag=name" + "&param=" + mEditText.getText().toString())
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                try {
                                    if (result.get("success").getAsInt() == 0) {

                                    } else if (result.get("success").getAsInt() == 1) {
                                        JsonArray array = result.get("book").getAsJsonArray();
                                        if (array == null) {
                                            /**
                                             * the book isn't exit
                                             * */
                                            Dialog dialog = new AlertDialog.Builder(SearchActivity.this).setTitle("没有这本书")
                                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            finish();
                                                        }
                                                    }).create();
                                            dialog.show();
                                        } else {
                                            mList = new ArrayList<Map<String, Object>>();
                                            for (int i = 0; i < array.size(); i++) {
                                                Map<String, Object> map = new HashMap<String, Object>();
                                                String bookname = array.get(i).getAsJsonObject().get("name").getAsString();
                                                map.put("title", bookname);
                                                map.put("detail", "简介 : "+array.get(i).getAsJsonObject().get("detail_info").getAsString());
                                                String booktime = array.get(i).getAsJsonObject().get("timestamp").getAsString();
                                                map.put("img", mImgUrl+"bookimg_"+booktime+".png");
                                                mList.add(map);
                                            }
                                            LyxListViewAdapter adapter = new LyxListViewAdapter(SearchActivity.this, mList);
                                            mListView.setAdapter(adapter);
                                        }
                                    }
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                                }
                            }

                            );
                        }
            });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.lyx_lv_title);
                Intent intent = new Intent(SearchActivity.this, PublicBookActivity.class);
                intent.putExtra("bookname", textView.getText().toString());
                startActivity(intent);
            }
        });
    }


}
