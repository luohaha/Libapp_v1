package com.example.root.libapp_v1.WriteCommentActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.test.mock.MockContentResolver;
import android.view.View;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-5-9.
 */
public class CommentActivity extends Activity {
    private HeadBar mHeadBar;
    private TextView mTitle;
    private TextView mDetail;
    private String mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initHeadBar();
        initView();
        getCommentFromDb();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.personbook_comment_activity_head_bar);
        mHeadBar.setTitleText("书评");
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.personbook_comment_activity_title);
        mDetail = (TextView) findViewById(R.id.personbook_comment_activity_detail);
    }

    private void getCommentFromDb() {
        Intent intent = getIntent();
        mDate = intent.getStringExtra("date");
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonCommentpage.PersonCommentpageProvider/personcommentpage");
        String selection = "date=?";
        String[] args = {mDate};
        Cursor cursor = contentResolver.query(uri,null, selection, args, null);
        while (cursor.moveToNext()) {
            mTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
            mDetail.setText(cursor.getString(cursor.getColumnIndex("detail")));
        }
        cursor.close();
    }


}
