package com.example.root.libapp_v1.PersonBook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.SetBitmapForImagView;
import com.example.root.libapp_v1.PersonBook.PersonBookCommentListView.CommentListviewAdapter;
import com.example.root.libapp_v1.PublicBookActivity.UpdateOwner;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.WriteCommentActivity.CommentActivity;
import com.example.root.libapp_v1.WriteCommentActivity.WriteCommentActivity;
import com.koushikdutta.ion.Ion;
import com.yalantis.phoenix.PullToRefreshView;

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
public class PersonBookActivity extends Activity{
    /**
     * define var
     */
    private HeadBar mHeadBar;
    private ArrayList<HashMap<String, Object>> mList;
    private ListView mListView;
    private PullToRefreshView mPullToRefreshView;
    private TextView mTitle;
    private ImageView mCover;
    private TextView mDetailInfo;
    private TextView mAuthorInfo;
    private TextView mCatalogInfo;
    /**
     * define the view pagerr
     */
    private ViewPager mViewPager;
    private ArrayList<View> mViewList;
    private TextView firstTab, secondTab;
    private PopupWindow popupwindow;
    private View mPopView;
    private String mBookName;
    private String mBookTime;
    private String mBookId;
    private String mNowUser;

    private CommentListviewAdapter mAdapter;
    private String mImgUrl;

    /**
     * bitmap loader
     * */
    private AsyncBitmapLoader mLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mImgUrl = getResources().getString(R.string.app_img_url);
        setContentView(R.layout.activity_personbook);
        getNowUser();
        getBookName();
        initBitmapLoader();
        setHeadBar();

        /**
         * get local data and init listview
         * */
        getListviewDataFromLocalDb();
        /**
         * second step, put the first layout and list view both into viewpager
         * */
        initView();
        initListViewOnItemClick();
        getDataFromLocalDataBase();
    }
    /**
     * init bitmap loader
     * */
    private void initBitmapLoader() {
        mLoader = new AsyncBitmapLoader();
    }
    /**
     * get book's name
     * */
    private void getBookName() {
        Intent intent = getIntent();
        mBookName = (String) intent.getStringExtra("name");
        mBookTime = (String) intent.getStringExtra("time");
    }
    /**
     * initial the listview
     */
    private void initListViewOnItemClick() {
        //LayoutInflater mInflater = getLayoutInflater();
        //View mView = mInflater.inflate(R.layout.personbook_tab2, null);
        //mListView = (FreshListView) mView.findViewById(R.id.personbook_listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.personbook_comment_time);
                Intent intent = new Intent(PersonBookActivity.this, CommentActivity.class);
                intent.putExtra("date", textView.getText().toString());
                startActivity(intent);
            }
        });
        showListview();
    }
    /**
     * show list view
     * */
    private void showListview() {
        if (mAdapter == null) {
            mAdapter = new CommentListviewAdapter(this, mList,
                    R.layout.personbook_comment_item);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mList);
        }
    }
    /**
     * set the head bar
     */
    private void setHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.personbook_head_bar);
        mHeadBar.setTitleText("个人读书");
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonBookActivity.this.finish();
            }
        });
        mHeadBar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initmPopupWindowView();
                popupwindow.showAsDropDown(v);
            }
        });
    }
    /**
     * init the popup window, so we can use it in setting hear bar.
     * */
    private void initmPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        // 创建PopupWindow实例,200,150分别是宽度和高度
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mPopView = layoutInflater.inflate(R.layout.personbook_popview, null);
        popupwindow = new PopupWindow(mPopView, 200, 300, true);
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
        mPopView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */
        BootstrapButton sendButton = (BootstrapButton) mPopView.findViewById(R.id.personbook_send_book);
        BootstrapButton writeButton = (BootstrapButton) mPopView.findViewById(R.id.personbook_write_comment);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOwner updateOwner = new UpdateOwner(mBookId, "", PersonBookActivity.this);
                updateOwner.start();
                Dialog dialog = new AlertDialog.Builder(PersonBookActivity.this).setTitle("发书成功")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonBookActivity.this, WriteCommentActivity.class);
                intent.putExtra("bookname", mBookName);
                intent.putExtra("personname", mNowUser);
                startActivity(intent);
            }
        });
    }

    /**
     * set the data of personbook_listview
     */
    private void getListviewDataFromLocalDb() {
        mList = new ArrayList<HashMap<String, Object>>();
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonCommentpage.PersonCommentpageProvider/personcommentpage");
        String selection = "personname = ? and bookname = ?";
        String[] args = {mNowUser, mBookName};
        Cursor cursor = contentResolver.query(uri, null, selection, args, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("last_update_time", cursor.getString(cursor.getColumnIndex("date")));
            map.put("comment_title", cursor.getString(cursor.getColumnIndex("title")));
            mList.add(map);
        }
        cursor.close();
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
        View view = mInflater.inflate(R.layout.personbook_tab1, null);
        /**
         * init compenont in view
         * */
        mTitle = (TextView) view.findViewById(R.id.personbook_title);
        mCover = (ImageView) view.findViewById(R.id.personbook_cover);
        mDetailInfo = (TextView) view.findViewById(R.id.personbook_book_intro);
        mAuthorInfo = (TextView) view.findViewById(R.id.personbook_writer_intro);
        mCatalogInfo = (TextView) view.findViewById(R.id.personbook_book_catalog);
        mViewList.add(view);
        /**
         * notice : can not add R.layout.personbook_tab2 into mViewList,
         *          you must add listview into it.
         * */
        View view2 = mInflater.inflate(R.layout.personbook_tab2, null);
        initRefreshView(view2);
        mPullToRefreshView = (PullToRefreshView)view2.findViewById(R.id.personbook_pull_to_refresh);
        View view3 = mInflater.inflate(R.layout.personbook_tab2_listview, null);
        mListView = (ListView)view3.findViewById(R.id.personbook_listview);
        mPullToRefreshView.addView(view3);
        /**
         * add listview to refreshview
         * */
        mViewList.add(mPullToRefreshView);
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
    /**
     * get book's detail from local database
     * */
    private void getDataFromLocalDataBase() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.PersonOwnBookpage.PersonOwnBookpageProvider/personownbookpage");
        String selection = "name=?";
        String[] args = {mBookName};
        Cursor cursor = contentResolver.query(uri, null, selection, args, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                mTitle.setText(cursor.getString(cursor.getColumnIndex("name")));
                mDetailInfo.setText(cursor.getString(cursor.getColumnIndex("detail_info")));
                mAuthorInfo.setText(cursor.getString(cursor.getColumnIndex("author_info")));
                mCatalogInfo.setText(cursor.getString(cursor.getColumnIndex("catalog_info")));
                mBookId = cursor.getString(cursor.getColumnIndex("unique_id"));
            }
            /**
             * set bitmap for book's cover
             * */
            Ion.with(mCover)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_failure)
                    .load(mImgUrl + "bookimg_" + mBookTime + ".png");
        } else {
            Dialog dialog = new AlertDialog.Builder(this).setTitle("获取图书信息失败")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }
    /**
     * get now user
     * */
    private void getNowUser() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            mNowUser = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
    }

    private void initRefreshView(View view) {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.personbook_pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListviewDataFromLocalDb();
                        //通知界面显示
                        showListview();
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 100);
            }
        });
    }

}
