package com.example.root.libapp_v1.UserLogin;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.UserRegisterActivity.UserRegisterActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yixin Luo on 15-4-12.
 * Today is a beautiful day!!
 */
public class LoginActivity extends Activity {
    /**
     * the login url
     * */
    private String loginUrl;


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
     * define the head bar
     * */
    private HeadBar mHeadBar;
    private ActionBar mActionBar;
     /**
     * the list view and it's item's position which is selected
     * */
    private ListView mListView;
    private int mCurrentItemPosition = -1;

    private boolean isListViewVisiable = false;
    private boolean isDownButtonUp = false;
    /**
     * an array for list view
     * */
    ArrayList<HashMap<String, Object>> mList;
    /**
     * test data, you can get them for internet
     * */
    Integer[] mHeads = new Integer[]{R.drawable.book7, R.drawable.book8, R.drawable.book5};
    String[] mNumber = new String[] {"123456", "741852", "852963"};
    /**
     * @param savedInstanceState
     */
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         this.loginUrl = getResources().getString(R.string.app_url)+"check_user.php";
        setContentView(R.layout.activity_login);
         /**
          * initial the components in login page
          * */
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
         * initial the head title
         * */
   //     mActionBar = getActionBar();
   //     mActionBar.hide();
        mHeadBar = (HeadBar) findViewById(R.id.login_head_bar);
        mHeadBar.setTitleText("登陆");
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
        mHeadBar.setLeftButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * close this acitvity
                 * */
                LoginActivity.this.finish();
             }
        });
         /**
         * initial the list view
         * */
        mListView = (ListView) findViewById(R.id.loginQQList);
         /**
         * when we click input number area,
         * if user input some number, then it will show more users which suit
         * */
        mUserNumberEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserNumberEditText.getText().toString().equals("") == false) {
                    mDeleteButton.setVisibility(View.VISIBLE);
                }
            }
        });
        /**
         * when we delete all word, then the head's image change to default one
         * */
        mUserNumberEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                        mUserNumberEditText.getText().toString().equals("") == true) {
                    mHeadImage.setImageResource(R.drawable.ic_launcher);
                }
                return false;
            }
        });
        /**
         * when we push the delete button
         * */
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserNumberEditText.setText("");
                mCurrentItemPosition = -1;
                mDeleteButton.setVisibility(View.GONE);
                mHeadImage.setImageResource(R.drawable.ic_launcher);
            }
        });
        /**
         * add some data to array list
         * */
         for (int i = 0; i < 2; i++) {
             HashMap<String, Object> map = new HashMap<String, Object>();
             map.put("userImage", mHeads[i]);
             map.put("userNumber", mNumber[i]);
             map.put("button", R.drawable.delete_button);
             mList.add(map);
         }
         /**
         * decide which head's image to show
         * */
        if (mCurrentItemPosition == -1) {
            mHeadImage.setImageResource(R.drawable.ic_launcher);
            mUserNumberEditText.setText("");
        } else {
            mHeadImage.setImageResource((Integer) mList.get(mCurrentItemPosition).get("userImage"));
            mUserNumberEditText.setText((String)mList.get(mCurrentItemPosition).get("userNumber"));
        }
        /**
         * define a new adapter
         * */
        LoginListviewAdapter loginListviewAdapter = new LoginListviewAdapter(this, mList,
                R.layout.login_list_item, mNumber, mHeads, mCurrentItemPosition,
                mHeadImage, mUserNumberEditText);
        mListView.setAdapter(loginListviewAdapter);
         /**
          * when we select an item from list view
          * */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * use the selected image and number as the default input
                 * */
                mHeadImage.setImageResource((Integer) mList.get(position).get("userImage"));
                mUserNumberEditText.setText((String) mList.get(position).get("userNumber"));
                mCurrentItemPosition = position;
                /**
                 * when it finish, the list view disappear
                 * */
                mListView.setVisibility(View.GONE);

             }
        });
        /**
         * when we click the show more item button
         * */
        mDownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * we can see list view now, so when we click the button, it disappear
                 * */
                if (isDownButtonUp) {
                    isDownButtonUp = false;
                    isListViewVisiable = false;
                    mListView.setVisibility(View.GONE);
                }
                /**
                 * we can't see list view, so when click, it show up
                 * */
                else {
                    isDownButtonUp = true;
                    isListViewVisiable = true;
                    mListView.setVisibility(View.VISIBLE);
                }
            }
        });
        /**
         * when login button is pushed !!!
         * */
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * to judge if the user exit
                 * */
                /**
                 * first step : get the login_user.php, and get the result
                 * second step : judge if the account_number and password is correct,
                 *              if correct, it will return JSONObject with personpage's msg,
                 *                  and we should update local personpage table's msg
                 *              else it won't return msg
                 * */
                Ion.with(LoginActivity.this)
                        .load(loginUrl)
                        .setBodyParameter("account_number", mUserNumberEditText.getText().toString())
                        .setBodyParameter("password", mUserPasswordEditText.getText().toString())
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                try {
                                    JSONObject s = new JSONObject(result);
                                    if (s.getInt("success") == 0) {
                                        //register fail
                                        Dialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("登陆失败")
                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).create();
                                        dialog.show();
                                    }
                                    else if (s.getInt("success") == 1){

                                            /**
                                             * restore the data into local database
                                             * */
                                            ContentResolver contentResolver = getContentResolver();
                                            Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("name", s.getString("name"));
                                            contentValues.put("quote", s.getString("quote"));
                                            contentValues.put("address", s.getString("address"));
                                            contentValues.put("mailbox", s.getString("mailbox"));
                                            contentValues.put("phone", s.getString("phone"));
                                            contentValues.put("account_number", s.getString("account_number"));
                                            contentValues.put("password", s.getString("password"));
                                            contentValues.put("timestamp", s.getString("timestamp"));
                                            contentResolver.update(uri, contentValues, null, null);
                                            Dialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("登陆成功")
                                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            LoginActivity.this.finish();
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
        });
          /**
          * when we push down the register button
          * */
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserRegisterActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
     }
    /**
     * get the result intent from register activity
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String resultPassword = data.getStringExtra("password");
            String resultAccountNumber = data.getStringExtra("account_number");
            mUserNumberEditText.setText(resultAccountNumber);
            mUserPasswordEditText.setText(resultPassword);

        }
    }

    /**
     * when the list view show, and we scroll it out of range
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * when we scroll it down and the list view is visible
         * */
        if (event.getAction() == MotionEvent.ACTION_DOWN && isListViewVisiable) {
            /**
             * get the list view's upper left corner's coordinate,
             * */
            int[] location = new int[2];
            mListView.getLocationInWindow(location);
            int x = (int)event.getX();
            int y = (int)event.getY();
            if (x < location[0] || x > location[0]+mListView.getWidth() || y < location[1] ||
                    y > location[1]+mListView.getHeight()) {
                isListViewVisiable = false;
                isDownButtonUp = false;
                mListView.setVisibility(View.GONE);
            }
         }
        return super.onTouchEvent(event);

    }

}
