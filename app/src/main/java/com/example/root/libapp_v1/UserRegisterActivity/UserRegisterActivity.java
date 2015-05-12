package com.example.root.libapp_v1.UserRegisterActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yixin on 15-5-4.
 */
public class UserRegisterActivity extends Activity {
    /**
     * the url which we want to post to
     * */
    private String postUrl = "http://192.168.0.153/android/register_user.php";

    private HeadBar mHeadBar;
    private BootstrapEditText mAccountNumber;
    private BootstrapEditText mUserName;
    private BootstrapEditText mUserPassword;
    private BootstrapEditText mUserAddress;
    private BootstrapEditText mUserMailbox;
    private BootstrapEditText mUserPhone;
    private BootstrapEditText mUserQuote;

    private BootstrapButton mSubmitButton;
    private BootstrapButton mClearButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initHeadBar();
        initEditText();
        initButton();
    }
    /**
     * init the head bar
     * */
    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.userregister_head_bar);
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
        mHeadBar.setTitleText("注册新用户");
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterActivity.this.finish();
            }
        });

    }
    /**
     * init the edittext
     * */
    private void initEditText() {
        mAccountNumber = (BootstrapEditText) findViewById(R.id.userregister_account_number);
        mUserName = (BootstrapEditText) findViewById(R.id.userregister_username);
        mUserPassword = (BootstrapEditText) findViewById(R.id.userregister_password);
        mUserQuote = (BootstrapEditText) findViewById(R.id.userregister_quote);
        mUserAddress = (BootstrapEditText) findViewById(R.id.userregister_address);
        mUserMailbox = (BootstrapEditText) findViewById(R.id.userregister_mailbox);
        mUserPhone = (BootstrapEditText) findViewById(R.id.userregister_phone);

    }
    /**
     * init the buttons
     * */
    private void initButton() {
        mSubmitButton = (BootstrapButton) findViewById(R.id.userregister_submit_button);
        mClearButton = (BootstrapButton) findViewById(R.id.userregister_clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllEditText();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserName.getText().toString().length() != 0 && mAccountNumber.getText().toString()
                        .length() != 0 && mUserPassword.getText().toString().length() != 0) {
                    /**
                     * if the user's name isn't empty
                     * */

                    /**
                     * upload the user's info to server side
                     * */

                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("name", mUserName.getText().toString()));
                    list.add(new BasicNameValuePair("quote", mUserQuote.getText().toString()));
                    list.add(new BasicNameValuePair("address", mUserAddress.getText().toString()));
                    list.add(new BasicNameValuePair("mailbox", mUserMailbox.getText().toString()));
                    list.add(new BasicNameValuePair("phone", mUserPhone.getText().toString()));
                    list.add(new BasicNameValuePair("account_number", mAccountNumber.getText().toString()));
                    list.add(new BasicNameValuePair("password", mUserPassword.getText().toString()));
                    HttpTask httpTask = new HttpTask(list);
                    httpTask.execute();


                } else {
                    /**
                     * if the user's name is empty
                     * */
                    Dialog dialog = new AlertDialog.Builder(UserRegisterActivity.this).setTitle("用户信息不全")
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
     * clear all edit text
     * */
    void clearAllEditText() {
        mAccountNumber.setText("");
        mUserName.setText("");
        mUserPassword.setText("");
        mUserQuote.setText("");
        mUserAddress.setText("");
        mUserMailbox.setText("");
        mUserPhone.setText("");
    }
    /**
     * refresh the data by getting from http
     * */
    private JSONObject getDataFromHttp(List<NameValuePair> list) {

        try {
            JSONObject jsonObject = DoGetAndPost.doPost(postUrl, list);
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
                    Dialog dialog = new AlertDialog.Builder(UserRegisterActivity.this).setTitle("注册失败"+s.getString("message"))
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

                        Dialog dialog = new AlertDialog.Builder(UserRegisterActivity.this).setTitle("注册成功")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.putExtra("password", mUserPassword.getText().toString());
                                        intent.putExtra("account_number", mAccountNumber.getText().toString());
                                        setResult(1001, intent);
                                        UserRegisterActivity.this.finish();
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
