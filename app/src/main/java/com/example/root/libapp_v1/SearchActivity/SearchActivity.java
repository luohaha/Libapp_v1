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
import com.example.root.libapp_v1.PublicBookActivity.PublicBookActivity;
import com.example.root.libapp_v1.R;

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
    private String mUrl = "http://192.168.0.153/android/get_book.php";
    private List<Map<String, Object>> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_search_listview);
        mEditText = (EditText) findViewById(R.id.activity_search_edittext);
        mButton = (BootstrapButton) findViewById(R.id.activity_search_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpTask httpTask = new HttpTask();
                httpTask.execute();
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

    /**
     * refresh the data by getting from http
     * */
    private JSONObject getDataFromHttp() {

        try {
            JSONObject jsonObject = DoGetAndPost.doGet(mUrl + "?flag=name" + "&param=" + mEditText.getText().toString());

            return jsonObject;
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
                JSONObject object = getDataFromHttp();
                if (object.getInt("success") == 0) {
                    return null;
                } else if (object.getInt("success") == 1){
                    JSONArray array = object.getJSONArray("book");
                    return array;
                }
                /**
                 * using publicProgress() to update progress bar's status
                 * */
                // publishProgress(100);
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
                   for (int i = 0; i < array.length(); i++) {
                       Map<String, Object> map = new HashMap<String, Object>();
                       map.put("title", array.getJSONObject(i).getString("name"));
                       map.put("detail", "简介 : "+array.getJSONObject(i).getString("detail_info"));
                       map.put("img", "http://a3.att.hudong.com/23/71/01300001170731130085713463754.jpg");
                       mList.add(map);
                   }
                    LyxListViewAdapter adapter = new LyxListViewAdapter(SearchActivity.this, mList);
                    mListView.setAdapter(adapter);
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
