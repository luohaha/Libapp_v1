package com.example.root.libapp_v1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.UserLogin.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-3-26.
 * 我的
 * bug : ui crash when start http
 */
public class FourFragment extends FatherFragment{
    /**
     *the url we get from
     */
    private String mGetUrl = "http://192.168.0.153/check_user.php?username=admin&password=123";
    /**
     * define var
     * */
    private HeadBar headBar;
    private LinearLayout mBooksDetail;
    private LinearLayout mRecordDetail;
    /**
     * all ids which fragment4.xml contains
     * */
    private ImageView mPersonpageImg;// person head's img
    private TextView mPersonpageName;//person's name
    private TextView mPersonpageQuote;//person's Quote
    private TextView mPersonpageAddress;//
    private TextView mPersonpageMailbox;
    private TextView mPersonpagePhone;
    private TextView mPersonpageBooksNumber;//the number of books which person own
    private TextView mPersonpageRecordNumber;//the number of person records

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment4, null);
        initIds(view);
        initHeadBar();
        initLayout(view);
        new Thread(loadDataFromHttp).start();
        return view;
    }
    /**
     * load data from http's thread
     * */
    Runnable loadDataFromHttp = new Runnable() {
        @Override
        public void run() {
            getDataFromHttp();
        }
    };
     /**
     * init the head bar
     * */
    private void initHeadBar() {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("我的");
    }
    private void initLayout(View v) {
        mBooksDetail = (LinearLayout) v.findViewById(R.id.personpage_books_detail);
        mRecordDetail = (LinearLayout) v.findViewById(R.id.personpage_record_detail);
        mBooksDetail.setOnClickListener(new View.OnClickListener() {
            /**
             * when the books detail is clicked, then
             * */
            @Override
            public void onClick(View v) {

            }
        });
        mRecordDetail.setOnClickListener(new View.OnClickListener() {
            /**
             * when the record detail layout is clicked, then
             * */
            @Override
            public void onClick(View v) {

            }
        });
    }
    /**
     * init the ids
     * */
    private void initIds(View view) {
        mPersonpageImg = (ImageView)view.findViewById(R.id.personpage_img);
        mPersonpageName = (TextView)view.findViewById(R.id.personpage_name);
        mPersonpageQuote = (TextView)view.findViewById(R.id.personpage_quote);
        mPersonpageAddress = (TextView)view.findViewById(R.id.personpage_address);
        mPersonpageMailbox = (TextView)view.findViewById(R.id.personpage_mailbox);
        mPersonpagePhone = (TextView)view.findViewById(R.id.personpage_phone);
        mPersonpageBooksNumber = (TextView)view.findViewById(R.id.personpage_books_number);
        mPersonpageRecordNumber = (TextView)view.findViewById(R.id.personpage_record_number);

    }
    /**
     * refresh the data by getting from http
     * */
    private void getDataFromHttp() {

        try {
            JSONObject jsonObject = DoGetAndPost.doGet(mGetUrl);
            /**
             * set the data which we get from http server
             * */
            JSONArray userInfo = jsonObject.getJSONArray("userInfo");
            JSONObject userOne = userInfo.getJSONObject(0);
            mPersonpageName.setText((String)userOne.get("username"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 }
