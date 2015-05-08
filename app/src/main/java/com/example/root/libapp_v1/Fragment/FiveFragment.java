package com.example.root.libapp_v1.Fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

import com.example.root.libapp_v1.Gallery.CustomGallery;
import com.example.root.libapp_v1.Gallery.ImageUtil;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *
 *  the third fragment of main activity
 *  扫一扫
 */
public class FiveFragment extends FatherFragment {

    private int[] imageResIDs;
    private String[] imageStrings;
    private CustomGallery customGallery;
    private HeadBar mHeadBar;
    public View view;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment5, null);
        setHeadBar();
        //the ids of books
        imageResIDs = new int[] {R.drawable.book8, R.drawable.book7,R.drawable.book6
                ,R.drawable.book5, R.drawable.book4, R.drawable.book3, R.drawable.book2,
                R.drawable.book1};
        //the descriptions of books
        imageStrings = new String[] {"NO.1", "NO.2", "NO.3", "NO.4", "NO.5", "NO.6", "NO.7", "NO.8"};
        //
        customGallery = (CustomGallery) view.findViewById(R.id.customgallery);
        ImageAdapter adapter = new ImageAdapter();
        customGallery.setAdapter(adapter);
        return view;
    }

    /**
     * set the head bar title
     * */
    private void setHeadBar() {
        mHeadBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        mHeadBar.setTitleText("扫一扫");
    }
    /**
     * define the ImageAdapter which match the customgallery
     */
    public class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageResIDs.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return imageResIDs[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /**
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ImageView imageView;
            if (convertView != null) {
                imageView = (ImageView) convertView;
            } else {
                imageView = new ImageView(view.getContext());
            }
            Bitmap bitmap = ImageUtil.getImageBitmap(getResources(),
                    imageResIDs[position]);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            drawable.setAntiAlias(true); // 消除锯齿
            imageView.setImageDrawable(drawable);
            LayoutParams params = new LayoutParams(240, 320);
            imageView.setLayoutParams(params);
            return imageView;
        }
    }
}
