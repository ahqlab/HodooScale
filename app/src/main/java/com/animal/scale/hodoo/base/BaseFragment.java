package com.animal.scale.hodoo.base;


import android.app.AlertDialog;
import android.support.v4.app.Fragment;

public class BaseFragment<D extends Fragment> extends Fragment {
    public static BaseFragment newInstance() {
        return new BaseFragment();
    }

    public AlertDialog.Builder showBasicOneBtnPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }
        return builder;
    }
}
