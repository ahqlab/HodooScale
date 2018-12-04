package com.animal.scale.hodoo.activity.test;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.R;

import static android.support.constraint.Constraints.TAG;

public class TestModel implements TestImpl.Presenter {
    private boolean DEBUG = true;
    private RetrofitRequester requester;

    @Override
    public void workModelTest(Context context) {

        if(DEBUG) Log.e(TAG, "workModelTest");
        requester = new RetrofitRequester();
        requester.requester(
                RetrofitRequester.methodGetUrl(context, R.string.test_url),
                new RetrofitRequester.ResultCallbacks() {
                    @Override
                    public void onResult(String data) {

                    }
                }
        );
    }
}
