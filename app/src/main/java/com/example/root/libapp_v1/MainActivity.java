package com.example.root.libapp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.example.root.libapp_v1.Fragment.FragmentFactory;
import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.SQLiteModule.DatabaseClient;
import com.example.root.libapp_v1.UserLogin.LoginActivity;


public class MainActivity extends Activity implements OnClickListener {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private RadioButton deaultButton;
    private ActionBar actionBar;
    private HeadBar headBar;
    private Fragment fragment;
    private Fragment nowFragment;
    private PopupWindow popupwindow;
    private FontAwesomeText rightButton;
    private View customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabaseModule();
        //get view customView
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        customView = layoutInflater.inflate(R.layout.popview_item, null);

        fragmentManager = getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        deaultButton = (RadioButton) findViewById(R.id.radiobution_1);
        rightButton = (FontAwesomeText) findViewById(R.id.button_right);
//        actionBar = getActionBar();
//        actionBar.hide();
        headBar = (HeadBar) findViewById(R.id.head_bar);
        headBar.setLeftButtonNoused();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    fragment = FragmentFactory.getInstanceByIndex(checkedId);
                    nowFragment = fragmentManager.findFragmentById(R.id.content);
                    if (!fragment.isAdded()) {
                        if (nowFragment == null)
                            transaction.add(R.id.content, fragment).commit();
                        else
                            transaction.hide(nowFragment).add(R.id.content, fragment).commit();
                    } else {
                        transaction.hide(nowFragment).show(fragment).commit();
                    }
                  //  transaction.replace(R.id.content, fragment);
                  //  transaction.commit();
                } catch (Exception e) {
                    Log.i("fuck", e.toString());
                }
            }
        });
        /**
         * set the default checked button
         * */
        deaultButton.setChecked(true);
        headBar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_right) {


                    initmPopupWindowView();
                    popupwindow.showAsDropDown(v, 0, 3);

                }

            }
        });
    }

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

             return false;
         }
     });

     /** 在这里可以实现自定义视图的功能 */
     BootstrapButton btton2 = (BootstrapButton) customView.findViewById(R.id.send_book);
     BootstrapButton btton3 = (BootstrapButton) customView.findViewById(R.id.search_all);
     BootstrapButton btton4 = (BootstrapButton) customView.findViewById(R.id.login_logout);

     btton4.setOnClickListener(this);
     btton2.setOnClickListener(this);
     btton3.setOnClickListener(this);
 }

    /**
     * when click on the right side button
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_book :
                Toast.makeText(this, "send books!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_all :
                Toast.makeText(this, "button push!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_logout :
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (popupwindow != null && popupwindow.isShowing()) {
                popupwindow.dismiss();
                popupwindow = null;
            }
        }
        return super.onTouchEvent(event);
    }
    /**
     * init the database module
     * */
    private void initDatabaseModule() {
        DatabaseClient databaseClient = new DatabaseClient(this);
    }
}
