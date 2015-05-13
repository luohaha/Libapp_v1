package com.example.root.libapp_v1.SendBookActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.GetPictureActivity.GetPictureActivity;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URLEncoder;
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
    private BootstrapEditText mCatalogInfo;
    private BootstrapButton mSubmitButton;
    private BootstrapButton mClearButton;
    private Button mSelectPicButton;

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
        mCatalogInfo = (BootstrapEditText) findViewById(R.id.sendbook_catalog_info);
    }

    private void initButtons() {
        mSubmitButton = (BootstrapButton) findViewById(R.id.sendbook_submit_button);
        mClearButton = (BootstrapButton) findViewById(R.id.sendbook_clear_button);
        mSelectPicButton = (Button) findViewById(R.id.sendbook_selectpic_button);
        mSubmitButton.setVisibility(View.GONE);
        mClearButton.setVisibility(View.GONE);
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
                        /**
                         * use Ion to send book to the service side
                         * */
                        Ion.with(SendBookActivity.this)
                                .load(url)
                                .setBodyParameter("name", mName.getText().toString())
                                .setBodyParameter("detail_info", mDetailInfo.getText().toString())
                                .setBodyParameter("author_info", mAuthorInfo.getText().toString())
                                .setBodyParameter("catalog_info", mCatalogInfo.getText().toString())
                                .setBodyParameter("sender", senderName)
                                .asString()
                                .setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {
                                        try {
                                            JSONObject s = new JSONObject(result);
                                            if (s.getInt("success") == 0) {
                                                //register fail
                                                Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("发书失败"+s.getString("message"))
                                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        }).create();
                                                dialog.show();
                                            }
                                            else if (s.getInt("success") == 1){

                                                Dialog dialog = new AlertDialog.Builder(SendBookActivity.this).setTitle("发书成功")
                                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                    finish();
                                                                }
                                                            }).create();
                                                dialog.show();

                                            } else {
                                                //I don't want to see this
                                                Log.i("the success code ------->>>", "err");
                                            }
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }
                                });

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
        mSelectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(SendBookActivity.this, GetPictureActivity.class);

                    String urlbook = URLEncoder.encode(mName.getText().toString(), "utf-8");

                    intent.putExtra("getpic_name", urlbook);
                    intent.putExtra("flag", "bookpic");
                    startActivity(intent);
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mClearButton.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


 }
