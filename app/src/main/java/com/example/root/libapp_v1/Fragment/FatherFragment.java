package com.example.root.libapp_v1.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * author : Yixin Luo
 * date : 2015-3-26
 * usage : define a abstract class for other fragment
 */
public abstract class FatherFragment extends Fragment{
    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

   // public abstract String initContent();
}
