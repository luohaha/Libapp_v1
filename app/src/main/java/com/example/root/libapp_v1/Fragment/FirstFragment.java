package com.example.root.libapp_v1.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.root.libapp_v1.Fragment.FirstFragmentAdapter.FirstFragmentAdapter;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.PublicBookActivity.PublicBookActivity;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageModule;
import com.example.root.libapp_v1.SearchActivity.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * author : Yixin Luo
 * date : 2015-3-26
 *
 *  this is the first fragment of main activity
 *  飞书馆
 */

public class FirstFragment extends FatherFragment {



    private HeadBar headBar;
    private GridView mGridView;
   // private ArrayList<HashMap<String, Object>> mList;
    //array of books to show in gallery
    private Object bookPicture[] = {R.drawable.book1, R.drawable.book2, R.drawable.book3,
            R.drawable.book4, R.drawable.book5, R.drawable.book6,
            R.drawable.book7, R.drawable.book8};

    private FirstFragmentAdapter mAdapter;
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
        initWhenRefresh(view);
        return view;
    }
    private void initWhenRefresh(View view) {
        initHeadBar(view);
        initData(view);
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
     * init the head bar
     * */
    private void initHeadBar(View view) {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("飞书馆");
        headBar.setRightSecondButtonListener(new MyOnClickListener(view));
        headBar.setLeftSecondButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * self define a listener
     * */
    private class MyOnClickListener implements View.OnClickListener {
        private View view;
        public MyOnClickListener(View v) {
            this.view = v;
        }

        @Override
        public void onClick(View v) {
            BookpageModule bookpageModule = new BookpageModule(getActivity());
            bookpageModule.refreshDb();
            initData(this.view);
        }
    }

    /**
     * init the test data
     * */
    private void initData(View view) {
                /*
        * the array of item which need to show in gridview
        * it contains string and a picture
        * */
        ArrayList<HashMap<String, Object>>mList = new ArrayList<HashMap<String, Object>>();


        /**
         * download info from local database
         * */
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Bookpage.BookpageProvider/bookpage");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                String bookname = cursor.getString(cursor.getColumnIndex("name"));
                map.put("image", "http://192.168.0.153/upload/bookimg_"+bookname+".png");
                map.put("text", bookname);
                mList.add(map);
            }
            /**
             * use the new data to show in gridview
             * */
            initGridView(view, mList);
        }
        cursor.close();
    }
    /**
     * init the grid view and set adapter for it
     * */
    private void initGridView(View view, ArrayList<HashMap<String, Object>> mList) {
        mGridView=(GridView) view.findViewById(R.id.publicbook_gridview);
                /*
        * create a adapter
        * */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.firstfragment_textview);;
                Intent intent = new Intent(getActivity(), PublicBookActivity.class);
                intent.putExtra("bookname", textView.getText());
                startActivity(intent);
            }
        });
        if (mAdapter == null) {
            mAdapter = new FirstFragmentAdapter(getActivity(), mList,
                    R.layout.firstfragment_gridview_item);

            mGridView.setAdapter(mAdapter);
        } else {
            mAdapter.onDataChange(mList);
        }
    }


}
