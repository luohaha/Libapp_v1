package com.example.root.libapp_v1.Fragment.FourFragmentAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yixin on 15-5-9.
 */
public class SendBookRecordActivity extends Activity {
    private ListView mListView;
    private HeadBar mHeadBar;
    private String mName;
    private List<String> mList;
    private String mUrl = "http://192.168.0.153/android/get_book.php?flag=sender";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendbook_record);
        initHeadBar();
        initListview();
        HttpTask httpTask = new HttpTask();
        httpTask.execute();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar)findViewById(R.id.sendbook_record_head_bar);
        mHeadBar.setTitleText("飞书记录");
        mHeadBar.setRightButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListview() {
        mListView = (ListView) findViewById(R.id.sendbook_record_listview);
    }

    /**
     * http module
     * */
    private JSONArray getDataFromHttp() {

        try {
            Intent intent = getIntent();
            mName = intent.getStringExtra("name");
            JSONObject jsonObject = DoGetAndPost.doGet(mUrl+"&param="+mName);
            /**
             * set the data which we get from http server
             * */
            JSONArray array = jsonObject.getJSONArray("book");
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

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                JSONArray array = getDataFromHttp();
                /**
                 * using publicProgress() to update progress bar's status
                 * */
                // publishProgress(100);
                return array;
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
                mList = new ArrayList<String>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    mList.add("第"+String.valueOf(i+1)+"本 : <<"+object.getString("name")+">>");
                }

                mListView.setAdapter(new ArrayAdapter<String>(SendBookRecordActivity.this, android.R.layout.simple_list_item_1, mList));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}
