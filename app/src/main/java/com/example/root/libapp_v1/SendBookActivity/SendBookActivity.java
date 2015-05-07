package com.example.root.libapp_v1.SendBookActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
 * Created by Yixin on 15-5-6.
 */
public class SendBookActivity extends Activity {
    private String url = "http://192.168.0.153/android/add_book.php";
    private BootstrapEditText mName;
    private BootstrapEditText mDetailInfo;
    private BootstrapEditText mAuthorInfo;
    private BootstrapButton mSubmitButton;
    private BootstrapButton mClearButton;

    private HeadBar mHeadBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendbook);
        initHeadBar();
        initEditView();
        initButtons();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.sendbook_head_bar);
        mHeadBar.setTitleText("发书");
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
    }

    private void initEditView() {
        mName = (BootstrapEditText) findViewById(R.id.sendbook_name);
        mDetailInfo = (BootstrapEditText) findViewById(R.id.sendbook_detail_info);
        mAuthorInfo = (BootstrapEditText) findViewById(R.id.sendbook_author_info);
    }

    private void initButtons() {
        mSubmitButton = (BootstrapButton) findViewById(R.id.sendbook_submit_button);
        mClearButton = (BootstrapButton) findViewById(R.id.sendbook_clear_button);
        /**
         * clear all edittext
         * */
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName.setText("");
                mDetailInfo.setText("");
                mAuthorInfo.setText("");
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mName.getText().toString().length() != 0 && mDetailInfo.getText().toString()
                        .length() != 0 && mAuthorInfo.getText().toString().length() != 0) {
                    /**
                     * if books' info is not empty
                     * */
                    String senderName = null;
                    ContentResolver contentResolver = getContentResolver();
                    Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
                    Cursor cursor = contentResolver.query(uri, null, null, null, null);
                    while (cursor.moveToNext()) {
                        senderName = cursor.getString(cursor.getColumnIndex("name"));
                    }
                    cursor.close();
                    /**
                     * if the user not login, then it won't send book
                     * */

                    if (senderName.length() == 0) {
                        Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("请先登陆!!!")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        List<NameValuePair> list = new ArrayList<NameValuePair>();
                        list.add(new BasicNameValuePair("name", mName.getText().toString()));
                        list.add(new BasicNameValuePair("detail_info", mDetailInfo.getText().toString()));
                        list.add(new BasicNameValuePair("author_info", mAuthorInfo.getText().toString()));
                        list.add(new BasicNameValuePair("sender", senderName));
                        HttpTask httpTask = new HttpTask(list);
                        httpTask.execute();
                    }
                } else {
                    Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("失败:请完成图书信息")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();
                }
            }
        });
    }

    /**
     * http module
     * */
    private JSONObject getDataFromHttp(List<NameValuePair> list) {

        try {
            JSONObject jsonObject = DoGetAndPost.doPost(url, list);
            Log.i("dasbb   hahahha  ->", jsonObject.toString());
            /**
             * set the data which we get from http server
             * */

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
        List<NameValuePair> list;
        private HttpTask(List<NameValuePair> l) {
            this.list = l;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject jsonObject = getDataFromHttp(this.list);
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
        protected void onPostExecute(JSONObject s) {
            try {

                if (s.getInt("success") == 0) {
                    //register fail
                    Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("注册失败"+s.getString("message"))
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();
                }
                else if (s.getInt("success") == 1){
                    //register success
                    try {

                        Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("注册成功")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        // clearAllEditText();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //I don't want to see this
                    Log.i("the success code ------->>>", "err");
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
