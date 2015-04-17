package com.example.root.libapp_v1.ZhujiListview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.UserLogin.ViewHolder;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by root on 15-4-17.
 */
public class ZhujiListviewAdater extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String, Object>> mList;
    private LayoutInflater mInflater;
    public ZhujiListviewAdater(Context context, ArrayList<Map<String, Object>> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
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
        ZhujiViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ZhujiViewHolder();
            convertView = mInflater.inflate(R.layout.zhuji_list_item, null);
            viewHolder.zhujiMainImg = (ImageView) convertView
                    .findViewById(R.id.zhuji_main_img);
            viewHolder.zhujiHeadImg = (BootstrapCircleThumbnail) convertView
                    .findViewById(R.id.zhuji_head_img);
            viewHolder.zhujiName = (TextView) convertView
                    .findViewById(R.id.zhuji_name);
            viewHolder.zhujiUpdateTime = (TextView) convertView
                    .findViewById(R.id.zhuji_update_time);
            viewHolder.zhujiMoreButton = (FontAwesomeText) convertView
                    .findViewById(R.id.zhuji_more_button);
            viewHolder.zhujiMainText = (TextView) convertView
                    .findViewById(R.id.zhuji_main_text);
            viewHolder.zhujiTagText = (TextView) convertView
                    .findViewById(R.id.zhuji_tag_text);
            viewHolder.zhujiZhanButton = (BootstrapButton) convertView
                    .findViewById(R.id.zhuji_zhan_button);
            viewHolder.zhujiCommentButton = (BootstrapButton) convertView
                    .findViewById(R.id.zhuji_comment_button);
            viewHolder.zhujiShareButton = (BootstrapButton) convertView
                    .findViewById(R.id.zhuji_share_button);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ZhujiViewHolder) convertView.getTag();
        }
        viewHolder.zhujiMainImg.setImageResource((Integer)mList.get(position).get("mainImg"));
        viewHolder.zhujiHeadImg.setImage((Integer)mList.get(position).get("headImg"));
        
        return null;
    }
}
