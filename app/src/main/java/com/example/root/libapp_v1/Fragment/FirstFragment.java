package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.waterfall.StaggeredGridView;

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
    private StaggeredGridView mGridView;
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
        mGridView=(StaggeredGridView) view.findViewById(R.id.staggeredGridView1);
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("飞书馆");
        /*
        * the array of item which need to show in gridview
        * it contains string and a picture
        * */
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        /*
        *  these are just tests, I have not finished yet!!
        *  push some tests in arraylist
        * */
        String tmp = "hahaha";
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", bookPicture[i]);// 添加图像资源的ID
            tmp += ("NO." + String.valueOf(i+1));
            map.put("ItemText", tmp);// 按序号做ItemText
            lstImageItem.add(map);
        }
        /*
        * create a adapter
        * */
        SimpleAdapter simple = new SimpleAdapter(getActivity(), lstImageItem,
                R.layout.small_item,
                new String[] { "ItemImage", "ItemText" }, new int[] {
                R.id.ItemImage, R.id.ItemText });
        mGridView.setAdapter(simple);
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
}
