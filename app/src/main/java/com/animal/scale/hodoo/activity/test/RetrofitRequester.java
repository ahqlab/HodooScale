package com.animal.scale.hodoo.activity.test;

import android.content.Context;

import com.animal.scale.hodoo.R;

/**
 * Created by Song on 2018-09-07.
 */
public class RetrofitRequester {

    public static String  methodGetUrl ( Context context, int url ) {
        return context.getString(R.string.base_url) + context.getString(url);
    }

    public void requester (String url, ResultCallbacks callbacks ) {

    }

    public interface ResultCallbacks {
        void onResult( String data );
    }

}
