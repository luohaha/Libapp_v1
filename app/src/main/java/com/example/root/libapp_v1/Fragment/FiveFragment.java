package com.example.root.libapp_v1.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
    private View mListView;
    private List<Map<String, Object>> mapList;
    private Drawable[] mBooks = new Drawable[]{R.drawable.book1, R.drawable.book2, R.drawable.book3,
                    R.drawable.book4, R.drawable.book5, R.drawable.book6, R.drawable.book7
                    R.drawable.book8};
    private LyxListViewAdapter mAdapter;
    private LinearLayout mLayout;
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
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        mLayout = (LinearLayout) view.findViewById(R.id.lyx_lv);
        headBar.setTitleText("扫一扫");
        mListView = inflater.inflate(R.layout.lyx_listview_item, null);
        setData();
        mAdapter = new LyxListViewAdapter(this.getActivity(), mapList);

        return view;
    }

    /**
     * put the data into mList
     *
     */
    private void setData() {
        Map<String, Object> map = new HashMap<String, Object>();
        String t = "NO.";
        String d = "You will like ";
        String im = "this is a great book!!";
        for (int i = 0; i < 8; i++) {
            map.put("title", t+Integer.toString(i));
            map.put("detail", d+t+Integer.toString(i));
            map.put("img", mBooks[i]);
            map.put("information", im);
            mapList.add(map);
        }
    }
}
