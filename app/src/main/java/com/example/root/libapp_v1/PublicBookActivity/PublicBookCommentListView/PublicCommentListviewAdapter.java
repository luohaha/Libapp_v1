package com.example.root.libapp_v1.PublicBookActivity.PublicBookCommentListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.SetBitmapForImagView;
import com.example.root.libapp_v1.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-5-12.
 */
public class PublicCommentListviewAdapter extends BaseAdapter {
    /**
     * define var
     * */
    private AsyncBitmapLoader mLoader;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemView;
    private Context mContext;
    public PublicCommentListviewAdapter(Context context, ArrayList<HashMap<String, Object>> list, int view) {
        this.mList = list;
        this.mItemView = view;
        this.mContext = context;
        mLoader = new AsyncBitmapLoader();
    }
    /**
     * change the date when need it
     * @param list
     */
    public void onDateChange(ArrayList<HashMap<String, Object>> list) {
        this.mList = list;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemView, null);
            viewHolder = new ViewHolder();
            viewHolder.comment = (TextView) convertView.findViewById(R.id.publicbook_comment_title);
            viewHolder.postTime = (TextView) convertView.findViewById(R.id.publicbook_comment_time);
            viewHolder.personName = (TextView) convertView.findViewById(R.id.publicbook_comment_person);
            viewHolder.head = (ImageView) convertView.findViewById(R.id.publicbook_comment_head);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * fix bug : if refresh too fast and crash
         * */
        if (viewHolder == null || viewHolder.personName == null ||
                viewHolder.postTime == null || viewHolder.comment == null) {
            Toast.makeText(mContext, "刷新太快咯~~", Toast.LENGTH_LONG);
        } else {
            viewHolder.personName.setText((String)mList.get(position).get("personname"));
            viewHolder.postTime.setText((String)mList.get(position).get("posttime"));
            viewHolder.comment.setText((String)mList.get(position).get("comment"));
            Ion.with(viewHolder.head)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .load("http://192.168.0.153/upload/personimg_"+(String)mList.get(position).get("persontime")+".png");
        }
        return convertView;
    }
}
