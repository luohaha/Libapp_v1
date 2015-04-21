package com.example.root.libapp_v1.PersonBook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-4-21.
 *
 * this acitivty is to show the personal books reading status.
 * And also he can send a book comment here.
 */
public class PersonBookActivity extends Activity {
    HeadBar mHeadBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personbook);
        setHeadBar();
    }
    /**
     * set the head bar
     * */
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
}
