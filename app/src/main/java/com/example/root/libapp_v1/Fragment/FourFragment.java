package com.example.root.libapp_v1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.UserLogin.LoginActivity;

/**
 * Created by Yixin on 15-3-26.
 * 我的
 */
public class FourFragment extends FatherFragment{

    /**
     * define var
     * */
    private HeadBar headBar;
    private LinearLayout mBooksDetail;
    private LinearLayout mRecordDetail;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment4, null);
        initHeadBar();
        initLayout(view);
        return view;
    }
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
}
