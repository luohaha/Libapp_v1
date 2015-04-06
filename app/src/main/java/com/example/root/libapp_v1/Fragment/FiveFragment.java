package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.LyxListView.LyxListViewAdapter;
import com.example.root.libapp_v1.R;

import java.util.*;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *  the fifth fragment of main activity
 *  ps, I like that girl
 */
public class FiveFragment extends FatherFragment {
    //
    private HeadBar headBar;
    private View mListViewItem;
    private ArrayList<Map<String, Object>> mapList;
    private Integer[] mBooks = new Integer[]{R.drawable.book1, R.drawable.book2, R.drawable.book3,
                    R.drawable.book4, R.drawable.book5, R.drawable.book6, R.drawable.book7,
                    R.drawable.book8};
    private LyxListViewAdapter mAdapter;
    private ListView mListView;
    /**
     * it start when view need to be created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment5, null);
      //  TextView textView = (TextView) view.findViewById(R.id.txt_content);
        setHeadBar();
        getView(view, inflater);
        setData();
        mAdapter = new LyxListViewAdapter(this.getActivity(), mapList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    /**
     * get mListView and mListViewItem from layout.
     * @param view it is the place we need to put our things into.
     * @param inflater
     */
    private void getView(View view, LayoutInflater inflater) {
        mListView = (ListView) view.findViewById(R.id.lyx_lv);
        mListViewItem = inflater.inflate(R.layout.lyx_listview_item, null);
    }
    /**
     * do some head bar setting jobs
     */
    private void setHeadBar() {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("扫一扫");

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
}
