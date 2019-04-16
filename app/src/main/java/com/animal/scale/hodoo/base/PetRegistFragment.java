package com.animal.scale.hodoo.base;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;

/**
 * Created by SongSeokwoo on 2019-04-12.
 */
public class PetRegistFragment<D extends Fragment> extends Fragment {
    protected boolean state = false;
    public static PetRegistFragment newInstance() {
        return new PetRegistFragment();
    }
    protected void changeState( boolean state ) {

    }
    public boolean getState() {
        return state;
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
