package com.animal.scale.hodoo.util;

import android.content.Context;
import android.content.res.Resources;
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
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
