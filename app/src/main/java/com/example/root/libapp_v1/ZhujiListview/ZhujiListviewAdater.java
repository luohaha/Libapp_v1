package com.example.root.libapp_v1.ZhujiListview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by root on 15-4-17.
 */
public class ZhujiListviewAdater extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String, Object>> mList;

    public ZhujiListviewAdater(Context context, ArrayList<Map<String, Object>> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
