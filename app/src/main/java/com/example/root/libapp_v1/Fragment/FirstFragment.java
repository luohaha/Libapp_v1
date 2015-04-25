package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;

import com.example.root.libapp_v1.Fragment.FirstFragmentAdapter.FirstFragmentAdapter;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *
 *  this is the first fragment of main activity
 *  飞书馆
 */

public class FirstFragment extends FatherFragment {

    //
    private HeadBar headBar;
    private RadioButton mAllButton;
    private RadioButton mFriendButton;
    private GridView mGridView;
    private ArrayList<HashMap<String, Object>> mList;
    //array of books to show in gallery
    private Object bookPicture[] = {R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6,
            R.drawable.book7, R.drawable.book8};

    /**
     * @param inflater :
     * @param container : view container
     * @param savedInstanceState
     * @return the view this fragment create
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //create a view for fragment1
        View view = inflater.inflate(R.layout.fragment1, null);
        //find the things we want to show in view
        initRadioButton(view);
        initHeadBar(view);
        initData();
        initGridView(view);
        return view;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //

    }
    /**
     * init the radiobutton, and set check first tab
     * */
    void initRadioButton(View view) {
        mAllButton = (RadioButton) view.findViewById(R.id.firstfragment_tab1);
        mFriendButton = (RadioButton) view.findViewById(R.id.firstfragment_tab2);
        mAllButton.setChecked(true);
    }
    /**
     * init the head bar
     * */
    void initHeadBar(View view) {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("飞书馆");
    }
    /**
     * init the test data
     * */
    void initData() {
                /*
        * the array of item which need to show in gridview
        * it contains string and a picture
        * */
        mList = new ArrayList<HashMap<String, Object>>();

        /*
        *  these are just tests, I have not finished yet!!
        *  push some tests in arraylist
        * */
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", bookPicture[i]);// 添加图像资源的ID
            String tmp = ("NO." + String.valueOf(i+1));
            map.put("text", tmp);// 按序号做ItemText
            mList.add(map);
        }
    }
    /**
     * init the grid view and set adapter for it
     * */
    void initGridView(View view) {
        mGridView=(GridView) view.findViewById(R.id.publicbook_gridview);
                /*
        * create a adapter
        * */
        FirstFragmentAdapter simple = new FirstFragmentAdapter(getActivity(), mList,
                R.layout.firstfragment_gridview_item);
        mGridView.setAdapter(simple);
    }
}
