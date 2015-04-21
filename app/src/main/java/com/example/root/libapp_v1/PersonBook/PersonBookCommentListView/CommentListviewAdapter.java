package com.example.root.libapp_v1.PersonBook.PersonBookCommentListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.R;

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemLayout, null);
            viewHolder = new ViewHolder();
            viewHolder.lastUpdateTime = (TextView) convertView
                    .findViewById(R.id.personbook_comment_time);
            viewHolder.commentTitle = (TextView) convertView
                    .findViewById(R.id.personbook_comment_title);
            viewHolder.editComment = (BootstrapButton) convertView
                    .findViewById(R.id.personbook_comment_edit_button);
            viewHolder.shareComment = (BootstrapButton) convertView
                    .findViewById(R.id.personbook_comment_share_button);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.lastUpdateTime.setText((String)mList.get(position).get("last_update_time"));
        viewHolder.commentTitle.setText((String)mList.get(position).get("comment_title"));
        viewHolder.editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.shareComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
}
