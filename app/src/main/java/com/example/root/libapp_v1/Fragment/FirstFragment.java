package com.example.root.libapp_v1.Fragment;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;

import com.example.root.libapp_v1.Fragment.FirstFragmentAdapter.FirstFragmentAdapter;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author : Yixin Luo
 * date : 2015-3-26
 *
 *  this is the first fragment of main activity
 *  飞书馆
 */

public class FirstFragment extends FatherFragment {

    /**
     * define a loader manager
     * */

    private LoaderManager mLoaderManager;

    private HeadBar headBar;
    private RadioButton mAllButton;
    private RadioButton mFriendButton;
    private GridView mGridView;
    private ArrayList<HashMap<String, Object>> mList;
    //array of books to show in gallery
    private Object bookPicture[] = {R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6,
            R.drawable.book7, R.drawable.book8};

    /**
     * @param inflater :
     * @param container : view container
     * @param savedInstanceState
     * @return the view this fragment create
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //create a view for fragment1
        View view = inflater.inflate(R.layout.fragment1, null);
        //find the things we want to show in view
        initRadioButton(view);
        initHeadBar(view);
        initData();
        initGridView(view);
        return view;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //

    }
    /**
     * init the radiobutton, and set check first tab
     * */
    private void initRadioButton(View view) {
     /*   mAllButton = (RadioButton) view.findViewById(R.id.firstfragment_tab1);
        mFriendButton = (RadioButton) view.findViewById(R.id.firstfragment_tab2);
        mAllButton.setChecked(true);*/
    }
    /**
     * init the head bar
     * */
    private void initHeadBar(View view) {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("飞书馆");
        headBar.setRightSecondButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookpageModule bookpageModule = new BookpageModule(getActivity());
                bookpageModule.insertDb();
            }
        });
    }
    /**
     * init the test data
     * */
    private void initData() {
                /*
        * the array of item which need to show in gridview
        * it contains string and a picture
        * */
        mList = new ArrayList<HashMap<String, Object>>();

        /*
        *  these are just tests, I have not finished yet!!
        *  push some tests in arraylist
        * */
        for (int i = 0; i < 8; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", bookPicture[i]);// 添加图像资源的ID
            String tmp = ("NO." + String.valueOf(i+1));
            map.put("text", tmp);// 按序号做ItemText
            mList.add(map);
        }
    }
    /**
     * init the grid view and set adapter for it
     * */
    private void initGridView(View view) {
        mGridView=(GridView) view.findViewById(R.id.publicbook_gridview);
                /*
        * create a adapter
        * */
        /*
        FirstFragmentAdapter simple = new FirstFragmentAdapter(getActivity(), mList,
                R.layout.firstfragment_gridview_item);
        mGridView.setAdapter(simple);
        */
    }
    /**
     * init LoaderManager
     * */
    private void initLoaderManager() {
        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(1000, null, callbacks);
    }

    /**
     * the call back of LoaderManager
     * */
    private LoaderManager.LoaderCallbacks<Cursor> callbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        /**
         * when first create the loader, it start
         * @param id loader's id
         * @param args
         * @return
         */
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
            CursorLoader loader = new CursorLoader(getActivity(), uri, null, null, null, null);
            Log.i("bookpage --->>>", "onCreateLoader");
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            while (data.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", data.getString(data.getColumnIndex("name")));
                map.put("img", data.getString(data.getColumnIndex("img")));
                map.put("short_detail", data.getString(data.getColumnIndex("short_detail")));
                list.add(map);
            }
            FirstFragmentAdapter firstFragmentAdapter = new FirstFragmentAdapter(getActivity(),
                    list, R.layout.firstfragment_gridview_item);
            mGridView.setAdapter(firstFragmentAdapter);
            Log.i("bookpage --->>>", "onLoadFinished");
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
