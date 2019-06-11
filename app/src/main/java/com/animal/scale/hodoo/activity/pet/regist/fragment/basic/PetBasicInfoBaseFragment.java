package com.animal.scale.hodoo.activity.pet.regist.fragment.basic;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by SongSeokwoo on 2019-05-13.
 */
public abstract class PetBasicInfoBaseFragment extends Fragment {
    private OnDataListener callback;
    public interface OnDataListener {
        void onDataCallback( int type, Object data );
    }
    public abstract PetBasicInfoBaseFragment newInstance();
    public void setCallback ( OnDataListener callback ) {
        this.callback = callback;
    }

    public OnDataListener getCallback() {
        return callback;
    }
}
