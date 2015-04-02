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
 * Created by root on 15-3-26.
 */
public class FirstFragment extends FatherFragment {
    //public String initContent() {
    //  return "third!";
    //}
    private HeadBar headBar;
    private StaggeredGridView mGridView;
    private Object bookPicture[] = {R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6,
            R.drawable.book7, R.drawable.book8};
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment1, null);
        mGridView=(StaggeredGridView) view.findViewById(R.id.staggeredGridView1);
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("飞书馆");
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        String tmp = "hahaha";
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", bookPicture[i]);// 添加图像资源的ID
            tmp += ("NO." + String.valueOf(i+1));
            map.put("ItemText", tmp);// 按序号做ItemText
            lstImageItem.add(map);
        }
        //构建一个适配器
        SimpleAdapter simple = new SimpleAdapter(getActivity(), lstImageItem,
                R.layout.small_item,
                new String[] { "ItemImage", "ItemText" }, new int[] {
                R.id.ItemImage, R.id.ItemText });
        mGridView.setAdapter(simple);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 生成动态数组，并且传入数据

    }
}
