package com.example.root.libapp_v1.Fragment;

import android.app.Fragment;

import com.example.root.libapp_v1.R;

/**
 * Created by root on 15-3-26.
 */
public class FragmentFactory {

    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.radiobution_1:
                fragment = new FirstFragment();
                break;
            case R.id.radiobution_2:
                fragment = new SecondFragment();
                break;
            case R.id.radiobution_3:
                fragment = new ThirdFragment();
                break;
            case R.id.radiobution_4:
                fragment = new FourFragment();
                break;
            case R.id.radiobution_5:
                fragment = new FiveFragment();
                break;
        }
        return fragment;
    }
}
