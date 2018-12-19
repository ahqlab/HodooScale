package com.animal.scale.hodoo.activity.home.fragment.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.Weatherbit;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragmentPresenter implements ActivityFragmentIn.Presenter {
    private static final String TAG = ActivityFragmentPresenter.class.getSimpleName();


    public static final int WIND_TYPE = 0;
    public static final int UV_TYPE = 1;
    public static final int OZON_TYPE = 2;


    private ActivityFragmentIn.View mView;
    private Context mContext;
    public interface WeatherCallback {
        <T> void onResponse( Response<T> response );
    }
    public ActivityFragmentPresenter (Context context, ActivityFragmentIn.View view ) {
        mView = view;
        mContext = context;
    }
    @Override
    public void getWeather(double lat, double lon, final WeatherCallback callback) {
//        ?lat=37.376385&amp;lon=126.635564
        final Call<Weatherbit> call = NetRetrofit.getInstance().getActivityService().getWeather(mContext.getString(R.string.get_weather_url), String.valueOf(lat), String.valueOf(lon));
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.setProgress(true);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                call.enqueue(new Callback<Weatherbit>() {
                    @Override
                    public void onResponse(Call<Weatherbit> call, Response<Weatherbit> response) {
                        if ( callback != null ) {
                            callback.onResponse(response);
                        }
                        Log.e(TAG, "debug");
                    }

                    @Override
                    public void onFailure(Call<Weatherbit> call, Throwable t) {

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.setProgress(false);
            }
        }.execute();
    }

    @Override
    public void getWeatherIcon(Context context, String iconName) {
        int image = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        mView.setWeatherIcon(image);
    }

    @Override
    public void getRangeStr(int type, float num) {
        switch ( type ) {
            case WIND_TYPE :
                break;
            case UV_TYPE :
                break;
            case OZON_TYPE :
                break;
        }
    }
}
