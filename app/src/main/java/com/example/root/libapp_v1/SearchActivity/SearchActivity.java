package com.example.root.libapp_v1.SearchActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.PublicBookActivity.UpdateOwner;
import com.example.root.libapp_v1.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-5-10.
 */
public class SearchActivity extends Activity {
    private ListView mListView;
    private EditText mEditText;
    private BootstrapButton mButton;
    private String mUrl = "http://192.168.0.153/android/get_book.php";
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
                } else {

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
