package com.example.root.libapp_v1.PublicBookActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.HttpModule.DoGetAndPost;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.SetBitmapForImagView;
import com.example.root.libapp_v1.PersonBook.PersonBookCommentListView.CommentListviewAdapter;
import com.example.root.libapp_v1.PullRefreshListView.FreshListView;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;
import com.example.root.libapp_v1.WriteCommentActivity.WriteCommentActivity;
import com.example.root.libapp_v1.WriteCommentActivity.WritePublicCommentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by Yixin on 15-4-21.
 *
 * this acitivty is to show the public books reading status.
 * And also he can send a book comment here.
 */
public class PublicBookActivity extends Activity implements FreshListView.IReflashListener{
    /**
     * define var
     */
    private HeadBar mHeadBar;
    private ArrayList<HashMap<String, Object>> mList;
    private CommentListviewAdapter mAdapter;
    private FreshListView mListView;
    private TextView mTitle;
    private ImageView mCover;
    private TextView mOwner;
    private TextView mSender;
    private TextView mDetailInfo;
    private TextView mAuthorInfo;
    private TextView mCatalogInfo;

    private BootstrapButton mBecomeOwner;
    /**
     * define the view pagerr
     */
    private ViewPager mViewPager;
    private ArrayList<View> mViewList;
    private TextView firstTab, secondTab;
    private PopupWindow popupwindow;
    private View mPopView;
    /**
     * this page's book name
     * */
    private String mBookName;
    private String mPersonWantToBecomeOwner;
    private String mUniqueId;
    private AsyncBitmapLoader mLoader;
    /**
     * the url which we can get owner and sender
     * */
    private String mUrl = "http://192.168.0.153/android/get_book.php";
    private String mImgUrl = "http://192.168.0.153/upload/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicbook);
        initBitmapLoader();
        getBookName();
        setHeadBar();
        /**
         * first step, finish init listview data
         * */
       // setData();
        getListviewDataFromHttp();
        initListView();
        /**
         * second step, put the first layout and list view both into viewpager
         * */
        initView();
        getDataFromLocalDataBase();
        getOwner();
    }
    private void initBitmapLoader() {
        mLoader = new AsyncBitmapLoader();

    }
    private void getBookName() {
        Intent intent = getIntent();
        mBookName = intent.getStringExtra("bookname");
    }

    /**
     * set the head bar
     */
    private void setHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.publicbook_head_bar);
        mHeadBar.setTitleText("图书信息");
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHeadBar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    initmPopupWindowView();
            //    popupwindow.showAsDropDown(v);
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
        mPopView = layoutInflater.inflate(R.layout.publicbook_popview, null);
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


        BootstrapButton writeButton = (BootstrapButton) mPopView.findViewById(R.id.publicbook_write_comment);

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicBookActivity.this, WritePublicCommentActivity.class);
                intent.putExtra("personname", mPersonWantToBecomeOwner);
                intent.putExtra("bookname", mBookName);
                startActivity(intent);
            }
        });
    }

    /**
     * set the data
     */
    /*
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
    */
    /**
     * inital the view pager
     */
    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.publicbook_viewpager);
        firstTab = (TextView) findViewById(R.id.publicbook_tab1_tv);
        secondTab = (TextView) findViewById(R.id.publicbook_tab2_tv);
        mViewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        View view = mInflater.inflate(R.layout.publicbook_tab1, null);

        mTitle = (TextView) view.findViewById(R.id.publicbook_title);
        mCover = (ImageView) view.findViewById(R.id.publicbook_cover);
        mOwner = (TextView) view.findViewById(R.id.publicbook_owner);
        mSender = (TextView) view.findViewById(R.id.publicbook_sender);
        mDetailInfo = (TextView) view.findViewById(R.id.publicbook_book_intro);
        mAuthorInfo = (TextView) view.findViewById(R.id.publicbook_writer_intro);
        mCatalogInfo = (TextView) view.findViewById(R.id.publicbook_book_catalog);
        mBecomeOwner = (BootstrapButton) view.findViewById(R.id.publicbook_become_owner);
        mViewList.add(view);
        /**
         * notice : can not add R.layout.personbook_tab2 into mViewList,
         *          you must add listview into it.
         * */
        mViewList.add(mListView);
        mViewPager.setAdapter(new PublicBookPageAdapter(mViewList));
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
     * get data from http and local database
     * */
    private void getDataFromLocalDataBase() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
        String selection = "name=?";
        String[] args = {new String(mBookName)};
        Cursor cursor = contentResolver.query(uri, null, selection, args, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                mTitle.setText(cursor.getString(cursor.getColumnIndex("name")));
                mDetailInfo.setText(cursor.getString(cursor.getColumnIndex("detail_info")));
                mAuthorInfo.setText(cursor.getString(cursor.getColumnIndex("author_info")));
                mCatalogInfo.setText(cursor.getString(cursor.getColumnIndex("catalog_info")));
                mUniqueId = cursor.getString(cursor.getColumnIndex("unique_id"));
            }
            /**
             * set book's cover
             * */
            SetBitmapForImagView.setBitmapForImageView(mLoader, mCover, mImgUrl+"bookimg_"+mBookName+".png");
            //mTitle.setText(s);
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
        uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
        cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            mPersonWantToBecomeOwner = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
    }

    /**
     * get owner from http
     * */
    private void getOwner() {
        HttpTask httpTask = new HttpTask();
        httpTask.execute();
    }

    /**
     * refresh the data by getting from http
     * */
    private JSONObject getDataFromHttp() {

        try {
            JSONObject jsonObject = DoGetAndPost.doGet(mUrl+"?flag=name"+"&param="+mTitle.getText());

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * clss HttpTask : which using AsyncTask to open a new thread to download data in back.
     */
    private class HttpTask extends AsyncTask<String, Integer, JSONObject> {
        private HttpTask() {
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject jsonObject = getDataFromHttp();
                /**
                 * using publicProgress() to update progress bar's status
                 * */
                // publishProgress(100);
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject object) {
            try {
                JSONArray array = object.getJSONArray("book");
                String owner = "";
                String sender = "";
                for (int i = 0; i < array.length(); i++) {
                    owner = array.getJSONObject(i).getString("owner");
                    sender = array.getJSONObject(i).getString("sender");
                }
                if (owner == null || owner.length() == 0 || owner == "null") {
                    mOwner.setVisibility(View.GONE);
                    mBecomeOwner.setVisibility(View.VISIBLE);
                    mBecomeOwner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Log.i("we get ------------->>>>>", mUniqueId+"  "+mPersonWantToBecomeOwner);
                            UpdateOwner updateOwner = new UpdateOwner(mUniqueId, mPersonWantToBecomeOwner, PublicBookActivity.this);
                            updateOwner.start();
                            Dialog dialog = new AlertDialog.Builder(PublicBookActivity.this).setTitle("订书成功")
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                            /**
                             * refresh the view
                             * */
                            getOwner();
                        }
                    });
                } else {
                    mOwner.setVisibility(View.VISIBLE);
                    mBecomeOwner.setVisibility(View.GONE);
                    mOwner.setText("持有人:"+owner);

                }
                mSender.setText("发书人:" + sender);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    /**
     *  freshlistview
     * */

    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                //  setRefreshData();
                getListviewDataFromHttp();
                //通知界面显示
                showListview();
                //通知listview 刷新数据完毕；
                mListView.reflashComplete();
            }
        }, 2);
    }

    @Override
    public void onLoad() {
        mListView.loadComplete();
    }

    /**
     * get listview data from local db
     * */
    private void getListviewDataFromHttp() {
        ListviewHttpTask httpTask = new ListviewHttpTask(mBookName);
        httpTask.execute();
    }

    /**
     * init list view
     * */
    private void initListView() {
        LayoutInflater mInflater = getLayoutInflater();
        View mView = mInflater.inflate(R.layout.publicbook_tab2, null);
        mListView = (FreshListView) mView.findViewById(R.id.publicbook_listview);
        showListview();
    }
    /**
     * show list view
     * */

    private void showListview() {
        /**
         * show list view
         * */
        if (mAdapter == null) {
            mListView.setInterface(this);
            mAdapter = new CommentListviewAdapter(this, mList,
                        R.layout.personbook_comment_item);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mList);
        }
    }
    /**
     *
     * */
    private class ListviewHttpTask extends AsyncTask<String, Integer, JSONArray> {
        private String bookname;
        public ListviewHttpTask(String book) {
            this.bookname = book;
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                JSONObject jsonObject = DoGetAndPost.doGet("");
                JSONArray array = jsonObject.getJSONArray("");
                /**
                 * using publicProgress() to update progress bar's status
                 * */
                // publishProgress(100);
                return array;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
