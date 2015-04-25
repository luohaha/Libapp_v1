package com.example.root.libapp_v1.Fragment.FirstFragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.libapp_v1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yixin on 15-4-25.
 */
public class FirstFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemView;

    public FirstFragmentAdapter(Context mContext, ArrayList<HashMap<String, Object>> mList, int view) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemView = view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirstFragmentViewHolder firstFragmentViewHolder = null;
        if (convertView == null) {
            firstFragmentViewHolder = new FirstFragmentViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mItemView, null);
            firstFragmentViewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.firstfragment_imageview);
            firstFragmentViewHolder.textView = (TextView) convertView
                    .findViewById(R.id.firstfragment_textview);
            convertView.setTag(firstFragmentViewHolder);
        } else {
            firstFragmentViewHolder = (FirstFragmentViewHolder) convertView.getTag();
        }
        firstFragmentViewHolder.imageView.setImageResource((Integer)mList.get(position).get("image"));
        firstFragmentViewHolder.textView.setText((String) mList.get(position).get("text"));

        return convertView;
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
}
