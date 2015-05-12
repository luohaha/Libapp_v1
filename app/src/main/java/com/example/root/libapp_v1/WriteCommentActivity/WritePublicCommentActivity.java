package com.example.root.libapp_v1.WriteCommentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yixin on 15-5-12.
 */
public class WritePublicCommentActivity extends Activity {
    private HeadBar mHeadBar;
    private BootstrapEditText mMsg;
    private BootstrapButton mSubmitButton;
    private BootstrapButton mClearButton;

    private String mPerson;
    private String mBook;

    private String mUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_publiccomment);
        initHeadBar();
        initPersonAndBookName();
        initEditView();
        initButton();
    }
    /**
     * init person and book's name
     * */
    private void initPersonAndBookName() {
        Intent intent = getIntent();
        this.mBook = intent.getStringExtra("bookname");
        this.mPerson = intent.getStringExtra("personname");
    }
    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.writepubliccomment_head_bar);
        mHeadBar.setRightButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setTitleText("写评论");
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initEditView() {
        mMsg = (BootstrapEditText) findViewById(R.id.writepubliccomment_msg);
    }

    private void initButton() {
        mSubmitButton = (BootstrapButton) findViewById(R.id.writepubliccomment_send_all);
        mClearButton = (BootstrapButton) findViewById(R.id.writepubliccomment_clear_all);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMsg.setText("");
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
                Dialog dialog = new AlertDialog.Builder(WritePublicCommentActivity.this).setTitle("评论成功!")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                finish();
            }
        });
    }

    private void postData() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("bookname", mBook));
        list.add(new BasicNameValuePair("personname", mPerson));
        list.add(new BasicNameValuePair("comment", mMsg.getText().toString()));
        HttpTask httpTask = new HttpTask(list);
        httpTask.execute();
    }

    /**
     *
     * */
    private class HttpTask extends AsyncTask<String, Integer, JSONObject> {
        private List<NameValuePair> mList;
        public HttpTask(List<NameValuePair> list) {
            this.mList = list;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                DoGetAndPost.doPost("", mList);
                return null;
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
    }
}
