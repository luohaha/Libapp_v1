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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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
    private String mPersonTime;
    private String mBook;

    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getResources().getString(R.string.app_url)+"add_bookcomment.php";
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
        this.mPersonTime = intent.getStringExtra("persontime");
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

                finish();
            }
        });
    }

    private void postData() {
        Ion.with(WritePublicCommentActivity.this)
                .load(mUrl)
                .setBodyParameter("bookname", mBook)
                .setBodyParameter("personname", mPerson)
                .setBodyParameter("persontime", mPersonTime)
                .setBodyParameter("comment", mMsg.getText().toString())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.getInt("success") == 1) {
                                Dialog dialog = new AlertDialog.Builder(WritePublicCommentActivity.this).setTitle("评论成功!")
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.show();
                            } else {
                                Dialog dialog = new AlertDialog.Builder(WritePublicCommentActivity.this).setTitle("评论失败")
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.show();
                            }
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }

}
