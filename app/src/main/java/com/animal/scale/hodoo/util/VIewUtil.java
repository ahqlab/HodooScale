package com.animal.scale.hodoo.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.animal.scale.hodoo.R;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

    /**
     * 회전 애니메이션을 시작한다.
     * @param v
     * @return
     */
    public static boolean rotationStart ( View v ) {
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(900);
        rotate.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotate);
        return true;
    }

    /**
     * 회전 애니메이션을 중지한다.
     * @param v
     * @return
     */
    public static boolean rotationStop ( final View v ) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.clearAnimation();
                v.animate().cancel();
            }
        }, 2000);
        return false;
    }

    /**
     * 시스템 언어를 가져온다.
     * @param context
     * @return
     */
    public static int getLocationCode ( Context context ) {
        Locale systemLocale = context.getApplicationContext().getResources().getConfiguration().locale;
        int result = 0;
        String language = systemLocale.getLanguage();
        String[] languageArr = context.getResources().getStringArray(R.array.language);
        for (int i = 0; i < languageArr.length; i++) {
            if ( language.equals(languageArr[i]) ) {
                result = i;
                break;
            }
        }
        return result;
    }
    public static  String getMyLocationCode(Context context) {
        Locale systemLocale = context.getApplicationContext().getResources().getConfiguration().locale;
        String language = systemLocale.getLanguage();
        return language;
    }
    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }
}
