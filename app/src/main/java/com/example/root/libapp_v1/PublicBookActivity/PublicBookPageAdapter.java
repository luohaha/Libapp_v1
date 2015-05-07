package com.example.root.libapp_v1.PublicBookActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Yixin on 15-5-7.
 */
public class PublicBookPageAdapter extends PagerAdapter{
    public List<View> mListViews;

    public PublicBookPageAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mListViews.get(arg1));
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public Object instantiateItem(View arg0, int position) {
        ((ViewPager) arg0).addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }
}
