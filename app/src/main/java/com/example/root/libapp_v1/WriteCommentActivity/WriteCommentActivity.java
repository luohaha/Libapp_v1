package com.example.root.libapp_v1.WriteCommentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-4-23.
 */
public class WriteCommentActivity extends Activity {

    private HeadBar mHeadBar;
    private BootstrapButton mSendButton;
    private BootstrapButton mClearButton;
    private BootstrapEditText mTitle;
    private BootstrapEditText mMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        initHeadBar();
        initEditText();
        initButton();
    }
    /**
     * init the hear bar module
     * */
    private void initHeadBar() {
        mHeadBar = (HeadBar)findViewById(R.id.writecomment_head_bar);
        mHeadBar.setTitleText("写书评");
        mHeadBar.setRightButtonNoused();
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
                mMsg.setText("");
            }
        });
    }
}
