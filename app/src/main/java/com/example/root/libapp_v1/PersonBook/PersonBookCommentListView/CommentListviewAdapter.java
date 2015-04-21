package com.example.root.libapp_v1.PersonBook.PersonBookCommentListView;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
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
    private PopupWindow mPopupwindow;
    private View mView;

    public CommentListviewAdapter(Context context, ArrayList<HashMap<String, Object>> list,
                                  int itemLayout) {
        this.mContext = context;
        this.mList = list;
        this.mItemLayout = itemLayout;
        //get view customView
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        this.mView = layoutInflater.inflate(R.layout.personbook_comment_more_pop, null);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemLayout, null);
            viewHolder = new ViewHolder();
            viewHolder.lastUpdateTime = (TextView) convertView
                    .findViewById(R.id.personbook_comment_time);
            viewHolder.commentTitle = (TextView) convertView
                    .findViewById(R.id.personbook_comment_title);
            viewHolder.moreComment = (FontAwesomeText) convertView
                    .findViewById(R.id.personbook_comment_more_button);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.lastUpdateTime.setText((String)mList.get(position).get("last_update_time"));
        viewHolder.commentTitle.setText((String)mList.get(position).get("comment_title"));
        viewHolder.moreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initmPopupWindowView(position);
                mPopupwindow.showAsDropDown(v, 0, -3);
            }
        });
        return convertView;
    }

    private void initmPopupWindowView(int position) {

        // // 获取自定义布局文件pop.xml的视图
        // 创建PopupWindow实例,200,150分别是宽度和高度
        mPopupwindow = new PopupWindow(mView, 300, 100, true);
        mPopupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        mPopupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
        mView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */
        BootstrapButton shareBUtton = (BootstrapButton) mView.findViewById(R.id.personbook_comment_share_button);
        BootstrapButton editButton = (BootstrapButton) mView.findViewById(R.id.personbook_comment_edit_button);

        shareBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
