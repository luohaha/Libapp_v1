package com.example.root.libapp_v1.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.R;
import com.example.root.libapp_v1.SpinnerButton.SpinnerButton;

import java.util.ArrayDeque;

/**
 * Created by root on 15-3-26.
 */
public class FiveFragment extends FatherFragment {
    //public String initContent() {
      //  return "five!!!";
    //}
    private HeadBar headBar;
    private SpinnerButton spinnerButton;
    String[] strs = new String[] {"no1", "no2", "no3"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment5, null);
      //  TextView textView = (TextView) view.findViewById(R.id.txt_content);
        headBar = (HeadBar)this.getActivity().findViewById(R.id.head_bar);
        headBar.setTitleText("扫一扫");
        spinnerButton = (SpinnerButton) view.findViewById(R.id.custom_sinnper);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, strs);
        spinnerButton.setAdapter(adapter);
        spinnerButton.setOnItemSeletedListener(new SpinnerButton.OnItemSeletedListener() {
            @Override
            public void onItemSeleted(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                Log.i("onitemselected ", obj + "");
            }
        });
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(spinnerButton.isShowPopup())
            {
                spinnerButton.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
