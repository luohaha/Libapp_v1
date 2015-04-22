package com.example.root.libapp_v1.PersonBook;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.PersonBook.PersonBookCommentListView.CommentListviewAdapter;
import com.example.root.libapp_v1.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by Yixin on 15-4-21.
 *
 * this acitivty is to show the personal books reading status.
 * And also he can send a book comment here.
 */
public class PersonBookActivity extends Activity {
    /**
     * define var
     */
    HeadBar mHeadBar;
    ArrayList<HashMap<String, Object>> mList;
    ListView mListView;
    /**
     * define the view pagerr
     */
    private ViewPager mViewPager;
    private ArrayList<View> mViewList;
    private TextView firstTab, secondTab;
    private int mCurrentView = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personbook);
        setHeadBar();
        /**
         * first step, finish init listview data
         * */
        setData();
        initListView();
        /**
         * second step, put the first layout and list view both into viewpager
         * */
        initView();

    }

    /**
     * initial the listview
     */
    private void initListView() {
        LayoutInflater mInflater = getLayoutInflater();
        View mView = mInflater.inflate(R.layout.personbook_tab2, null);
        mListView = (ListView) mView.findViewById(R.id.personbook_listview);
        CommentListviewAdapter adapter = new CommentListviewAdapter(this, mList,
                R.layout.personbook_comment_item);
        mListView.setAdapter(adapter);
    }

    /**
     * set the head bar
     */
    private void setHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.personbook_head_bar);
        mHeadBar.setTitleText("个人读书");
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonBookActivity.this.finish();
            }
        });
    }

    /**
     * set the data
     */
    private void setData() {
        mList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Date now = new Date();
            DateFormat d1 = DateFormat.getDateInstance();
            map.put("last_update_time", d1.format(now));
            map.put("comment_title", "细品电音，提升逼格");
            mList.add(map);
        }
    }

    /**
     * inital the view pager
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.personbook_viewpager);
        firstTab = (TextView) findViewById(R.id.personbook_tab1_tv);
        secondTab = (TextView) findViewById(R.id.personbook_tab2_tv);
        mViewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        mViewList.add(mInflater.inflate(R.layout.personbook_tab1, null));
        /**
         * notice : can not add R.layout.personbook_tab2 into mViewList,
         *          you must add listview into it.
         * */
        mViewList.add(mListView);
        mViewPager.setAdapter(new PersonBookPageAdapter(mViewList));
        mViewPager.setCurrentItem(0);

        /**
         * set on click listener for tab
         * */
        firstTab.setOnClickListener(new MyClickListener(0));
        secondTab.setOnClickListener(new MyClickListener(1));
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * a class to listen a click event
     */
    private class MyClickListener implements View.OnClickListener {
        private int index = 0;

        public MyClickListener(int i) {
            index = i;

        }

        public void onClick(View arg0) {

            mViewPager.setCurrentItem(index);

        }

    }
    /**
     * a class which is listening the view page change
     * */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        //    int one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        //    int two = one * 2;// 移动两页的偏移量,比如1直接跳3

        @Override
        public void onPageSelected(int index) {
            switch (index) {
                case 0:
                    firstTab.setTextColor(getResources().getColor(R.color.black));
                    firstTab.setBackgroundColor(getResources().getColor(R.color.white));
                    secondTab.setTextColor(getResources().getColor(R.color.gray));
                    secondTab.setBackgroundColor(getResources().getColor(R.color.card_back));
                    break;
                case 1:
                    secondTab.setTextColor(getResources().getColor(R.color.black));
                    secondTab.setBackgroundColor(getResources().getColor(R.color.white));
                    firstTab.setTextColor(getResources().getColor(R.color.gray));
                    firstTab.setBackgroundColor(getResources().getColor(R.color.card_back));
                    break;

            }
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {}

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
    }
}
