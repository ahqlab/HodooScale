package com.animal.scale.hodoo.util;

import android.content.Context;
import android.util.TypedValue;

public class VIewUtil {

    Context context;

    public VIewUtil(Context context) {
        this.context = context;
    }

    public int numberToDP(int number){
        final int dp = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, number, context.getResources().getDisplayMetrics());
        return dp;
    }
}
