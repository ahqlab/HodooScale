package com.animal.scale.hodoo.activity.home.fragment.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ActivityFragmentIn {
    interface View {
        void setWeatherIcon(int image);
        void setProgress(boolean state);
    }
    interface Presenter {
        void getWeather(double lat, double lon, ActivityFragmentPresenter.WeatherCallback callback);
        void getWeatherIcon(Context context, String iconName);
        void getRangeStr ( int type, float num );
    }
}
