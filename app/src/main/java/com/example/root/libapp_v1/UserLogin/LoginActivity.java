package com.example.root.libapp_v1.UserLogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.libapp_v1.R;

/**
 * Created by Yixin Luo on 15-4-12.
 * Today is a beautiful day!!
 */
public class LoginActivity extends Activity {
    /**
     * get the user input message
     * */
    private String mInputUserNumber;
    private String mInputUserPassword;

    /**
     * buttons, view and images
     * */
    private Button mLoginButton;
    private ImageButton mDownButton;
    private ImageButton mDeleteButton;
    private TextView mNumberTextView;
    private TextView mPasswordTextView;
    private EditText mUserNumberEditText;
    private EditText mUserPasswordEditText;

    private ListView mListView;

    private boolean isListViewVisiable;
    private boolean isDownButtonUp;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
