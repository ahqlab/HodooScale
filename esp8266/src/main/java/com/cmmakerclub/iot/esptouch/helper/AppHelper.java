package com.cmmakerclub.iot.esptouch.helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;

public class AppHelper {
    private static boolean male;
    public static final String TAG = AppHelper.class.getSimpleName();

    public static SharedPreferences getSharedPreference(Context context) {
        SharedPreferences mSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);
        return mSharedPref;
    }


    public static String getDeviceId(Context context) {
        String android_id = android.provider.Settings.Secure.
                getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        return android_id;
    }



    public static boolean setPsk(Context context, String psk) {
        SharedPreferences mSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mSharedPref.edit();

        editor.putString(Constants.WIFI_PSK, psk);
        return editor.commit();
    }

    public static String getPsk(Context context, String fallback) {
        SharedPreferences mSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);


        return mSharedPref.getString(Constants.WIFI_PSK, fallback);
    }

    public static boolean setBoolean(Context context, String key, boolean value) {
        SharedPreferences mSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mSharedPref.edit();

        editor.putBoolean(key, value);
        return editor.commit();
    }


    public static boolean getBoolean(Context context, String key, boolean fallback) {
        SharedPreferences mSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);


        return mSharedPref.getBoolean(key, fallback);
    }


    public static boolean xset(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <E> E get(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (E) field.get(object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


}
