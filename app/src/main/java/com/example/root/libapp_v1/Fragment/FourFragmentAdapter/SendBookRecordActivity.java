package com.example.root.libapp_v1.Fragment.FourFragmentAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yixin on 15-5-9.
 */
public class SendBookRecordActivity extends Activity {
    private ListView mListView;
    private HeadBar mHeadBar;
    private String mName;
    private List<String> mList;
    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getResources().getString(R.string.app_url)+"get_book.php?flag=sender";
        setContentView(R.layout.activity_sendbook_record);
        initHeadBar();
        initListview();

        connected();
    }
    private void connected() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        Ion.with(SendBookRecordActivity.this)
                .load(mUrl+"&param="+mName)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            int success = result.get("success").getAsInt();
                            if (success == 1) {
                                /**
                                 * if send book before
                                 * */
                                JsonArray array = result.getAsJsonArray("book");
                                mList = new ArrayList<String>();
                                for (int i = 0; i < array.size(); i++) {
                                    JsonObject object = array.get(i).getAsJsonObject();
                                    mList.add("第" + String.valueOf(i + 1) + "本 : <<" + object.get("name").getAsString() + ">>");
                                }
                            } else {
                                mList = new ArrayList<String>();
                                Toast.makeText(SendBookRecordActivity.this, "send null book", Toast.LENGTH_SHORT);
                            }
                            mListView.setAdapter(new ArrayAdapter<String>(SendBookRecordActivity.this, android.R.layout.simple_list_item_1, mList));
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }
    private void initHeadBar() {
        mHeadBar = (HeadBar)findViewById(R.id.sendbook_record_head_bar);
        mHeadBar.setTitleText("飞书记录");
        mHeadBar.setRightButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListview() {
        mListView = (ListView) findViewById(R.id.sendbook_record_listview);
    }


}
