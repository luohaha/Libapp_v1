package com.example.root.libapp_v1.LyxListView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.R;

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
            viewHolder.moreButton = (BootstrapButton) convertView.findViewById(R.id.lyx_lv_btn);
            convertView.setTag(viewHolder);
        }
        else {
            convertView.setTag(viewHolder);
        }
        //you also need to add here!
        if (viewHolder == null)
        {
            Log.i("haha : ", "it is null");
        }
        /*
        * I have to add the try and catch the err and it work, i don't know why.
        * If I don't do that, it will crash.
        * So, don't touch the code, it is magic!!!
        * */
        try {
            viewHolder.img.setBackgroundResource((Integer) mList.get(position).get("img"));
            viewHolder.title.setText((String) mList.get(position).get("title"));
            viewHolder.detal.setText((String) mList.get(position).get("detail"));
            viewHolder.moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.lyx_lv_btn) {
                        showMore((String) mList.get(position).get("information"));
                    } else {
                        //now, you have to know it won't happen
                    }
                }
            });
        } catch (Exception e) {
            //It will happen always, don't worry
            Log.i("err", e.toString());
        }
        return convertView;
    }

    /**
     * when push the button more, it show a AlertDialog
     *
     * @param str it is the more information which need to show
     */
    private void showMore(String str) {
        new AlertDialog.Builder(mContext).setTitle("详细信息").setMessage(str).
                setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
