package com.example.root.libapp_v1.LyxListView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.R;
import com.koushikdutta.ion.Ion;

import java.util.*;

/**
 * Author : Yixin Luo
 * Date : 2015-4-5
 *  this is the custom adapter for LyxListView
 *  ps.. I hate today !!!
 */
public class LyxListViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> mList;
    private Context mContext;
    /**
     * @param context the context, you know what it is
     * @param list the list of map which need to put into the adapter
     */
    public LyxListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mList = list;
        this.mContext = context;
    }

    /**
     * change the date when need it
     * @param list
     */
    public void onDateChange(List<Map<String, Object>> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }
    /**
     * @return the size of mList
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * @param position where the item it is
     * @return the position
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    /**
     * @param position
     * @return
     */

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position it means which line it will show
     * @param convertView it came from the layout file
     * @param parent sorry, i don't know too
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.lyx_listview_item, null);
            //you can add your change here
            viewHolder.title = (TextView)convertView.findViewById(R.id.lyx_lv_title);
            viewHolder.detal = (TextView)convertView.findViewById(R.id.lyx_lv_detal);
            viewHolder.img = (ImageView)convertView.findViewById(R.id.lyx_lv_img);
            convertView.setTag(viewHolder);
        }
        else {
           // get an old one, and use it later.
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //you also need to add here!
        viewHolder.title.setText((String) mList.get(position).get("title"));
        viewHolder.detal.setText((String) mList.get(position).get("detail"));
        Ion.with(viewHolder.img)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_failure)
                .load((String)mList.get(position).get("img"));
        return convertView;
    }


}
