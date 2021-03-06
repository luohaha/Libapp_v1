package com.example.root.libapp_v1.Fragment.FirstFragmentAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.R;
import com.koushikdutta.ion.Ion;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yixin on 15-4-25.
 */
public class FirstFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemView;
    private AsyncBitmapLoader mLoader;

    public FirstFragmentAdapter(Context mContext, ArrayList<HashMap<String, Object>> mList, int view) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemView = view;
        this.mLoader = new AsyncBitmapLoader();
    }
    /**
     * change the date when need it
     * @param list
     */
    public void onDataChange(ArrayList<HashMap<String, Object>> list) {
        this.mList = list;
        this.notifyDataSetChanged();
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
        /**
         * load bitmap
         * */
        String s = (String)mList.get(position).get("image");
        Ion.with(firstFragmentViewHolder.imageView)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_failure)
                .load(s);

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
