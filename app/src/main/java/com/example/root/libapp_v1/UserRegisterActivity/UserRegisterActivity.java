package com.example.root.libapp_v1.UserRegisterActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllEditText();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserName.getText().toString() != "") {
                    /**
                     * if the user's name isn't empty
                     * */
                    ContentResolver contentResolver = getContentResolver();
                    Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", mUserName.getText().toString());
                    contentValues.put("quote", mUserQuote.getText().toString());
                    contentValues.put("address", mUserAddress.getText().toString());
                    contentValues.put("mailbox", mUserMailbox.getText().toString());
                    contentValues.put("phone", mUserPhone.getText().toString());
                    contentValues.put("account_number", mAccountNumber.getText().toString());
                    contentValues.put("password", mUserPassword.getText().toString());
                    Uri ret = contentResolver.insert(uri, contentValues);
                    Log.i("new user register : ", ret.toString());
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
                    clearAllEditText();
                } else {
                    /**
                     * if the user's name is empty
                     * */
                    Dialog dialog = new AlertDialog.Builder(UserRegisterActivity.this).setTitle("注册失败")
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
}
