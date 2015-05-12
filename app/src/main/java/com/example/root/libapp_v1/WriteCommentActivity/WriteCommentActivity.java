package com.example.root.libapp_v1.WriteCommentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yixin on 15-4-23.
 */
public class WriteCommentActivity extends Activity {

    private HeadBar mHeadBar;
    private BootstrapButton mSendButton;
    private BootstrapButton mClearButton;
    private BootstrapEditText mTitle;
    private BootstrapEditText mMsg;
    private String mNowUser;
    private String mNowBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        initHeadBar();
        initEditText();
        initButton();
        getNowUserAndBook();
    }
    /**
     * init the hear bar module
     * */
    private void initHeadBar() {
        mHeadBar = (HeadBar)findViewById(R.id.writecomment_head_bar);
        mHeadBar.setTitleText("写书评");
        mHeadBar.setRightButtonNoused();
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * quit this activity
                 * */
                WriteCommentActivity.this.finish();
            }
        });
    }
    /**
     * init the title and msg edit text
     * */
    private void initEditText() {
        mTitle = (BootstrapEditText) findViewById(R.id.writecomment_title);
        mMsg = (BootstrapEditText) findViewById(R.id.writecomment_msg);

    }
    /**
     * init the buttons
     * */
    private void initButton() {
        mSendButton = (BootstrapButton) findViewById(R.id.writecomment_send_all);
        mClearButton = (BootstrapButton) findViewById(R.id.writecomment_clear_all);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setText("");
                mMsg.setText("");
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushCommentToLocalDb();
                Dialog dialog = new AlertDialog.Builder(WriteCommentActivity.this).setTitle("写书评成功!!!")
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
    /**
     * get the now user and book's name
     * */
    private void getNowUserAndBook() {
        Intent intent = getIntent();
        mNowUser = intent.getStringExtra("personname");
        mNowBook = intent.getStringExtra("bookname");
    }
    /**
     * send comment to local database
     * */
    private void pushCommentToLocalDb() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonCommentpage.PersonCommentpageProvider/personcommentpage");
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", mTitle.getText().toString());
        contentValues.put("detail", mMsg.getText().toString());
        contentValues.put("personname", mNowUser);
        contentValues.put("bookname", mNowBook);
        /**
         * get the time now
         * */
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        contentValues.put("date", dateFormat.format(now));
        contentResolver.insert(uri, contentValues);
    }
}
