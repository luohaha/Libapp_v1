package com.example.root.libapp_v1.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.PersonBook.PersonBookActivity;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView.IReflashListener;
import com.example.root.libapp_v1.LyxListView.LyxListViewAdapter;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageModule;

import java.util.*;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *  the fifth fragment of main activity
 *  ps, I like that girl
 *  阅览室
 */
public class ThirdFragment extends FatherFragment implements IReflashListener {
    //
    private HeadBar headBar;
    private View mListViewItem;
    private ArrayList<Map<String, Object>> mapList;
    private Integer[] mBooks = new Integer[]{R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6, R.drawable.book7,
            R.drawable.book8};
    private LyxListViewAdapter mAdapter;
    private FreshListView mListView;
    /**
     * it start when view need to be created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment3, null);
        //  TextView textView = (TextView) view.findViewById(R.id.txt_content);
        setHeadBar();
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
            mListView.setInterface(this);
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
        mListView = (FreshListView) view.findViewById(R.id.lyx_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * you can get the item's information here by parent, and send it to the server
                 *
                 * ListView listView = (ListView)parent;
                 * HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                 * */
                Intent intent = new Intent(getActivity(), PersonBookActivity.class);
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
                PersonOwnBookpageModule personOwnBookpageModule = new PersonOwnBookpageModule(getActivity());
                personOwnBookpageModule.refreshDb();
            }
        });
    }

    /**
     * put the data into mList
     *
     */
    private void setData() {
        mapList = new ArrayList<Map<String, Object>>(8);
        Map<String, Object> map;
        String t = "NO.";
        String d = "You will like ";
        String im = "this is a great book!!";
        for (int i = 0; i < 8; i++) {
            map = new HashMap<String, Object>();
            map.put("title", t+Integer.toString(i+1));
            map.put("detail", d+t+Integer.toString(i+1));
            map.put("img", mBooks[i]);
            map.put("information", im);
            mapList.add(map);
        }
    }

    /**
     * refresh the new data into mList,
     * I just test it now!!
     */
    private void setRefreshData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "the new gay");
        map.put("detail", "heyheyhey");
        map.put("img", mBooks[2]);
        map.put("informaion", "it sucks");
        mapList.add(0, map);
    }

    /**
     * reload the new data into mList,
     * just a test
     */
    private void setLoadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "the new gay");
        map.put("detail", "heyheyhey");
        map.put("img", mBooks[2]);
        map.put("informaion", "it sucks");
        mapList.add(map);
    }
    /**
     * finish the interface function onReflash()
     * */
    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
              //  setRefreshData();
                //通知界面显示
                showList();
                //通知listview 刷新数据完毕；
                mListView.reflashComplete();
            }
        }, 2000);
    }

    /**
     * finish the interface function onLoad()
     */
    @Override
    public void onLoad() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
            //    setLoadData();
                //通知界面显示
                showList();
                //通知listview 刷新数据完毕；
                mListView.loadComplete();
            }
        }, 2000);
    }

}
