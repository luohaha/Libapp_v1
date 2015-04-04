package com.example.root.libapp_v1.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by root on 15-3-26.
 */
public class FiveFragment extends FatherFragment implements OnClickListener{
    //public String initContent() {
      //  return "five!!!";
    //}
    private PopupWindow popupwindow;
    private HeadBar headBar;
    private Button button;
    private View customView;
    String[] strs = new String[] {"no1", "no2", "no3"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment5, null);
      //  TextView textView = (TextView) view.findViewById(R.id.txt_content);
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("扫一扫");
        button = (Button)view.findViewById(R.id.button1);
        button.setOnClickListener(this);
        customView = inflater.inflate(R.layout.popview_item, null);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                }
                else {
                    initmPopupWindowView();
                    popupwindow.showAsDropDown(v, 0 , 9);

                }
                break;
            case R.id.button2:
                Toast toast = Toast.makeText(this.getActivity(), "button push!", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                break;
        }
    }

    public void initmPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, 200, 300);
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
        Button btton2 = (Button) customView.findViewById(R.id.button2);
        Button btton3 = (Button) customView.findViewById(R.id.button3);
        Button btton4 = (Button) customView.findViewById(R.id.button4);
        btton2.setOnClickListener(this);
        btton3.setOnClickListener(this);
        btton4.setOnClickListener(this);

    }

}
