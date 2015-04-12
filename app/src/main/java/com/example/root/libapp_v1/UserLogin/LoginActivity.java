package com.example.root.libapp_v1.UserLogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.R;

import java.util.ArrayList;
import java.util.HashMap;

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
    private BootstrapButton mLoginButton;
    private ImageButton mDownButton;
    private ImageButton mDeleteButton;
    private TextView mGetPasswordBack;
    private TextView mRegister;
    private ImageView mHeadImage;
    /**
     * two edit texts
     * */
    private BootstrapEditText mUserNumberEditText;
    private BootstrapEditText mUserPasswordEditText;
    /**
     * the list view and it's item's position which is selected
     * */
    private ListView mListView;
    private int mItemPosition = -1;

    private boolean isListViewVisiable;
    private boolean isDownButtonUp;
    /**
     * an array for list view
     * */
    ArrayList<HashMap<String, Object>> mList;

    /**
     * @param savedInstanceState
     */
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (BootstrapButton) findViewById(R.id.login_button);
        mDownButton = (ImageButton) findViewById(R.id.down_button);
        mDeleteButton = (ImageButton) findViewById(R.id.delete_button);

        mGetPasswordBack = (TextView) findViewById(R.id.get_password_back_textview);
        mRegister = (TextView) findViewById(R.id.regist_textview);

        mList = new ArrayList<HashMap<String, Object>>();

        mHeadImage = (ImageView) findViewById(R.id.head_image);
        mUserNumberEditText = (BootstrapEditText) findViewById(R.id.login_user_number);
        mUserPasswordEditText = (BootstrapEditText) findViewById(R.id.login_user_password);
        /**
         * when we click the show more user button,
         * if user input some number, then it will show more users which suit
         * */
        mDownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserNumberEditText.getText().toString().equals("") == false) {
                    mDeleteButton.setVisibility(View.VISIBLE);
                }
            }
        });
        /**
         * when we push the delete button
         * */
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserNumberEditText.setText("");
                mItemPosition = -1;
                mDeleteButton.setVisibility(View.GONE);
            }
        });
        /**
         * decide which head's image to show
         * */
        if (mItemPosition == -1) {
            mHeadImage.setImageResource(R.drawable.ic_launcher);
            mUserNumberEditText.setText("");
        } else {
            mHeadImage.setImageResource((Integer) mList.get(mItemPosition).get(""));
            mUserNumberEditText.setText(mList.get(mItemPosition).get("userNumber"));
        }
    }
}
