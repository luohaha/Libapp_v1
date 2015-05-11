package com.example.root.libapp_v1.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.libapp_v1.Fragment.FourFragmentAdapter.SendBookRecordActivity;
import com.example.root.libapp_v1.GetPictureActivity.GetPictureActivity;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.AsyncBitmapLoader;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageModule;

/**
 * Created by Yixin on 15-3-26.
 * 我的
 * bug : ui crash when start http
 */
public class FourFragment extends FatherFragment{

    /**
     * define var
     * */
    private HeadBar headBar;
    private LinearLayout mBooksDetail;
    private LinearLayout mRecordDetail;
    /**
     * all ids which fragment4.xml contains
     * */
    private ImageView mPersonpageImg;// person head's img
    private TextView mPersonpageName;//person's name
    private TextView mPersonpageQuote;//person's Quote
    private TextView mPersonpageAddress;//
    private TextView mPersonpageMailbox;
    private TextView mPersonpagePhone;
    private TextView mPersonpageBooksNumber;//the number of books which person own
    private TextView mPersonpageRecordNumber;//the number of person records

    private View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment4, null);
        initIds(mView);
        initHeadBar();
        initLayout(mView);

        showDbData();
        initImageView(mView);
        return mView;
    }

    /**
     * the the fragment show again, and this function will be call
     */
    @Override
    public void onResume() {
        super.onResume();
        showDbData();
        initImageView(mView);
    }

    /**
     * init the head bar
     * */
    private void initHeadBar() {
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("我的");
        headBar.setRightSecondButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonpageModule sqLiteModule = new PersonpageModule(getActivity());
                sqLiteModule.updateDb();
                Log.i("you suck!!", "yes");
            }
        });
    }
    private void initLayout(View v) {
        mBooksDetail = (LinearLayout) v.findViewById(R.id.personpage_books_detail);
        mRecordDetail = (LinearLayout) v.findViewById(R.id.personpage_record_detail);
        mBooksDetail.setOnClickListener(new View.OnClickListener() {
            /**
             * when the books detail is clicked, then
             * */
            @Override
            public void onClick(View v) {

            }
        });
        mRecordDetail.setOnClickListener(new View.OnClickListener() {
            /**
             * when the record detail layout is clicked, then
             * */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendBookRecordActivity.class);
                intent.putExtra("name", mPersonpageName.getText().toString());
                startActivity(intent);
            }
        });
    }
    /**
     * init the ids
     * */
    private void initIds(View view) {
        mPersonpageImg = (ImageView)view.findViewById(R.id.personpage_img);
        mPersonpageName = (TextView)view.findViewById(R.id.personpage_name);
        mPersonpageQuote = (TextView)view.findViewById(R.id.personpage_quote);
        mPersonpageAddress = (TextView)view.findViewById(R.id.personpage_address);
        mPersonpageMailbox = (TextView)view.findViewById(R.id.personpage_mailbox);
        mPersonpagePhone = (TextView)view.findViewById(R.id.personpage_phone);
        mPersonpageBooksNumber = (TextView)view.findViewById(R.id.personpage_books_number);
        mPersonpageRecordNumber = (TextView)view.findViewById(R.id.personpage_record_number);
        mPersonpageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetPictureActivity.class);
                intent.putExtra("getpic_name", mPersonpageName.getText().toString());
                startActivity(intent);
            }
        });
    }
    /**
     * show data using sqlite
     * */
    private void showDbData() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.libapp_v1.SQLiteModule.Personpage.PersonpageProvider/personpage/1");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            mPersonpageName.setText(cursor.getString(cursor.getColumnIndex("name")));
            mPersonpageQuote.setText(cursor.getString(cursor.getColumnIndex("quote")));
            mPersonpageAddress.setText(cursor.getString(cursor.getColumnIndex("address")));
            mPersonpageMailbox.setText(cursor.getString(cursor.getColumnIndex("mailbox")));
            mPersonpagePhone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            mPersonpageBooksNumber.setText(cursor.getString(cursor.getColumnIndex("books_number")));
            mPersonpageRecordNumber.setText(cursor.getString(cursor.getColumnIndex("record_number")));
        }
        cursor.close();
    }

    private void initImageView(View view) {
        AsyncBitmapLoader asyncBitmapLoader = new AsyncBitmapLoader();
        mPersonpageImg = (ImageView) view.findViewById(R.id.personpage_img);
        String url = "http://192.168.0.153/upload/personimg_"+mPersonpageName.getText().toString()+".png";
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(mPersonpageImg, url, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        if (bitmap == null) {
            mPersonpageImg.setImageResource(R.drawable.ic_launcher);
        } else {
            mPersonpageImg.setImageBitmap(bitmap);
        }
    }
 }
