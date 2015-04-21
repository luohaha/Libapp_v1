package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-3-26.
 * 我的
 */
public class FourFragment extends FatherFragment {
    //public String initContent() {
      //  return "four!!!";
    //}
    private HeadBar headBar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment4, null);
       // TextView textView = (TextView) view.findViewById(R.id.txt_content);
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("我的");
        return view;
    }
}
