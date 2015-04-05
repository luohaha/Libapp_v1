package com.example.root.libapp_v1.LyxListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.*;

/**
 * Author : Yixin Luo
 * Date : 2015-4-5
 *  this is the custom adapter for LyxListView
 *  ps.. I hate today !!!
 */
public class LyxListViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> mList;
    /**
     * @param context the context, you know what it is
     * @param list the list of map which need to put into the adapter
     */
    public LyxListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    /**
     * @return the size of mList
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * @param position where the item it is
     * @return the position
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder 
    }
}
