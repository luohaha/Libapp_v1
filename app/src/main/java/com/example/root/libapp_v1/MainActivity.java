package com.example.root.libapp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.root.libapp_v1.Fragment.FragmentFactory;
import com.example.root.libapp_v1.HeadBar.HeadBar;


public class MainActivity extends Activity{
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private ActionBar actionBar;
    private HeadBar headBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        actionBar = getActionBar();
        actionBar.hide();
        headBar = (HeadBar) findViewById(R.id.head_bar);
        headBar.setLeftButtonNoused();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);
                    transaction.replace(R.id.content, fragment);
                    transaction.commit();
                } catch (Exception e) {
                    Log.i("fuck", e.toString());
                }
            }
        });
        headBar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
