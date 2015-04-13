package com.example.root.libapp_v1.UserLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.libapp_v1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yixin Luo on 15-4-13.
 * where?
 * here
 */
public class LoginListviewAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private int mItemLayout;
    /**
     * get the user's number and image
     * */
    private String[] mGetNumber;
    private Integer[] mGetImage;

    public LoginListviewAdapter(Context mContext, ArrayList<HashMap<String, Object>> arrayList,
                                int itemLayout, String[] getNumber, Integer[] getImage) {
        this.mContext = mContext;
        this.mList = arrayList;
        this.mItemLayout = itemLayout;
        this.mGetImage = getImage;
        this.mGetNumber = getNumber;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemLayout, null);
            holder = new ViewHolder();
            holder.userButton = (ImageButton) convertView.findViewById(R.id.login_deleteButton);
            holder.userNumber = (TextView) convertView.findViewById(R.id.login_userQQ);
            holder.userImage = (ImageView) convertView.findViewById(R.id.login_userPhoto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * set the holder's value
         * */
        holder.userImage.setImageResource((Integer) mList.get(position).get("userImage"));
        holder.userNumber.setText((String) mList.get(position).get("userNumber"));
        holder.userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                if (position)
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
