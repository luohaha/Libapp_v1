package com.example.root.libapp_v1.UserRegisterActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-5-4.
 */
public class UserRegisterActivity extends Activity {
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

    }
  }
