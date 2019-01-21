package com.animal.scale.hodoo.base;

import android.support.v4.app.Fragment;

public class BaseFragment<D extends Fragment> extends Fragment {
    public static BaseFragment newInstance() {
        return new BaseFragment();
    }
}
