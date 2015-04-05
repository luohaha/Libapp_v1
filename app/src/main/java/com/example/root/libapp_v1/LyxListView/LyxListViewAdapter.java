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
    private LayoutInflater layoutInflater;
    /**
     * @param context the context, you know what it is
     * @param list the list of map which need to put into the adapter
     */
    public LyxListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
