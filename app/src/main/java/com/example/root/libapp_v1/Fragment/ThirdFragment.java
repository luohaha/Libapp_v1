package com.example.root.libapp_v1.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.PersonBook.PersonBookActivity;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView.IReflashListener;
import com.example.root.libapp_v1.LyxListView.LyxListViewAdapter;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageModule;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.*;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *  the fifth fragment of main activity
 *  10.110
 *  阅览室
 */
public class ThirdFragment extends FatherFragment {
    //
    private String mPicUrl;
    private HeadBar headBar;
    private View mListViewItem;
    private ArrayList<Map<String, Object>> mapList;
    private Integer[] mBooks = new Integer[]{R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6, R.drawable.book7,
            R.drawable.book8};
    private LyxListViewAdapter mAdapter;
    private ListView mListView;
    private PullToRefreshView mPullToRefreshView;
    private Map<String, String> mNameToTime;
    /**
     * it start when view need to be created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.mPicUrl = getResources().getString(R.string.app_img_url);
        View view = inflater.inflate(R.layout.fragment3, null);
        //  TextView textView = (TextView) view.findViewById(R.id.txt_content);
        setHeadBar();
        initRefreshView(view);
        getAllView(view, inflater);
        setData();
        showList();
        return view;
    }

    /**
     * begin to show the data in mList
     */
    private void showList() {
        if (mAdapter == null) {
            mAdapter = new LyxListViewAdapter(this.getActivity(), mapList);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mapList);
        }
    }
    /**
     * get mListView and mListViewItem from layout.
     * @param view it is the place we need to put our things into.
     * @param inflater
     */
    private void getAllView(View view, LayoutInflater inflater) {
        mListView = (ListView) view.findViewById(R.id.lyx_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * you can get the item's information here by parent, and send it to the server
                 *
                 * ListView listView = (ListView)parent;
                 * HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                 * */
                TextView textView = (TextView) view.findViewById(R.id.lyx_lv_title);
                Intent intent = new Intent(getActivity(), PersonBookActivity.class);
                intent.putExtra("name", textView.getText());
                intent.putExtra("time", mNameToTime.get(textView.getText().toString()));
                startActivity(intent);
             }
        });
    }
    /**
     * do some head bar setting jobs
     */
    private void setHeadBar() {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("阅览室");

        headBar.setRightSecondButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PersonOwnBookpageModule personOwnBookpageModule = new PersonOwnBookpageModule(getActivity());
//                personOwnBookpageModule.refreshDb();
            }
        });
    }

    /**
     * put the data into mList
     *
     */
    private void setData() {
        mNameToTime = new HashMap<String, String>();
        PersonOwnBookpageModule personOwnBookpageModule = new PersonOwnBookpageModule(getActivity());
        personOwnBookpageModule.refreshDb();
        mapList = new ArrayList<Map<String, Object>>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageProvider/personownbookpage");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                String personName = cursor.getString(cursor.getColumnIndex("name"));
                map.put("title", personName);
                String detail = cursor.getString(cursor.getColumnIndex("detail_info"));
                /**
                 * if the detail info is more than 20, then split it
                 * */
                if (detail.length() > 20) {
                    detail = detail.substring(0, 19);
                }
                map.put("detail", "简介 : "+detail+".....");
                String time = cursor.getString(cursor.getColumnIndex("timestamp"));
                map.put("img", mPicUrl+"bookimg_"+time+".png");
                mNameToTime.put(personName, time);
                mapList.add(map);
            }
        }
        cursor.close();
    }




    private void initRefreshView(View view) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.fragment3_pull_to_refresh);
        mPullToRefreshView.setRefreshing(true);
        mPullToRefreshView.setRefreshing(false);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                        //通知界面显示
                        showList();
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 100);
            }
        });
    }

}
