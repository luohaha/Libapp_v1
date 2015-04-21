package com.example.root.libapp_v1.PersonBook.PersonBookCommentListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yixin on 15-4-21.
 */
public class CommentListviewAdapter extends BaseAdapter {
    /**
     * define var
     * */
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemLayout;

    public CommentListviewAdapter(Context context, ArrayList<HashMap<String, Object>> list,
                                  int itemLayout) {
        this.mContext = context;
        this.mList = list;
        this.mItemLayout = itemLayout;
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
        return convertView;
    }
}
