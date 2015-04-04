package com.example.root.libapp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.root.libapp_v1.Fragment.FragmentFactory;
import com.example.root.libapp_v1.HeadBar.HeadBar;


public class MainActivity extends Activity implements OnClickListener {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private ActionBar actionBar;
    private HeadBar headBar;
    private Fragment fragment;
    private PopupWindow popupwindow;
    private Button rightButton;
    private View customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get view customView
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        customView = layoutInflater.inflate(R.layout.popview_item, null);

        fragmentManager = getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        rightButton = (Button) findViewById(R.id.button_right);
        actionBar = getActionBar();
        actionBar.hide();
        headBar = (HeadBar) findViewById(R.id.head_bar);
        headBar.setLeftButtonNoused();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    fragment = FragmentFactory.getInstanceByIndex(checkedId);
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
                if (v.getId() == R.id.button_right) {
                    if (popupwindow != null && popupwindow.isShowing()) {
                        popupwindow.dismiss();
                        return;
                    }
                    else {
                        initmPopupWindowView();
                        popupwindow.showAsDropDown(v, 0, 5);
                    }
                }

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
 private void initmPopupWindowView() {

     // // 获取自定义布局文件pop.xml的视图
     // 创建PopupWindow实例,200,150分别是宽度和高度
     popupwindow = new PopupWindow(customView, 200, 300, true);
     popupwindow.setBackgroundDrawable(new BitmapDrawable());
     // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
     popupwindow.setAnimationStyle(R.style.AnimationFade);
     // 自定义view添加触摸事件
     customView.setOnTouchListener(new View.OnTouchListener() {

         @Override
         public boolean onTouch(View v, MotionEvent event) {
             if (popupwindow != null && popupwindow.isShowing()) {
                 popupwindow.dismiss();
                 popupwindow = null;
             }

             return false;
         }
     });

     /** 在这里可以实现自定义视图的功能 */
     Button btton2 = (Button) customView.findViewById(R.id.send_book);
     Button btton3 = (Button) customView.findViewById(R.id.search_all);
     btton2.setOnClickListener((View.OnClickListener) this);
     btton3.setOnClickListener(this);
 }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_book :
                Toast.makeText(this, "send books!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_all :
                Toast.makeText(this, "button push!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
