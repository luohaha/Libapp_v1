package com.example.root.libapp_v1.UserLogin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yixin Luo on 15-4-13.
 * where?
 * here
 */
public class LoginListviewAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemLayout;

    public LoginListviewAdapter(Context mContext, ArrayList<HashMap<String, Object>> arrayList,
                                int itemLayout) {
        this.mContext = mContext;
        this.mList = arrayList;
        this.mItemLayout = itemLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
