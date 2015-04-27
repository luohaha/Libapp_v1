package com.example.root.libapp_v1.ZhujiListview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.example.root.libapp_v1.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by root on 15-4-17.
 *
 * bug: unfinished !!!
 */
public class ZhujiListviewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String, Object>> mList;
    private LayoutInflater mInflater;
    public ZhujiListviewAdapter(Context context, ArrayList<Map<String, Object>> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }
    public void onDateChange(ArrayList<Map<String, Object>> apk_list) {
        this.mList = apk_list;
        this.notifyDataSetChanged();
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

            viewHolder.zhujiName = (TextView) convertView
                    .findViewById(R.id.zhuji_name);
            viewHolder.zhujiUpdateTime = (TextView) convertView
                    .findViewById(R.id.zhuji_update_time);
            viewHolder.zhujiMainText = (TextView) convertView
                    .findViewById(R.id.zhuji_main_text);

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
        viewHolder.zhujiHeadImg.setImage((Integer) mList.get(position).get("headImg"));
        viewHolder.zhujiName.setText((String) mList.get(position).get("name"));
        viewHolder.zhujiUpdateTime.setText((String)mList.get(position).get("updateTime"));

        viewHolder.zhujiMainText.setText((String) mList.get(position).get("mainText"));
        viewHolder.zhujiTagText.setText((String)mList.get(position).get("tagText"));
        viewHolder.zhujiZhanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            /**
             * when zhan button push
             * */
            }
        });
        viewHolder.zhujiCommentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            /**
             * when comment button push
             * */
            }
        });
        viewHolder.zhujiShareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            /**
             * when share button push
             * */
            }
        });

        return convertView;
    }
}
