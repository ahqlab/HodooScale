package com.animal.scale.hodoo.activity.home.fragment.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.FragmentActivityLayoutBinding;
import com.animal.scale.hodoo.domain.Weatherbit;
import com.animal.scale.hodoo.util.VIewUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ActivityFragment extends Fragment implements ActivityFragmentIn.View, LocationListener {

    String TAG = ActivityFragment.class.getSimpleName();
    FragmentActivityLayoutBinding binding;
    ActivityFragmentIn.Presenter presenter;
    private LocationManager lm;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

    private final int REQUEST_LOCATION = 100;
    private long nowTime;
    private boolean rotationState = false, isActivity = false, isLocation = true;
    private int LIMIT_TIME = 20 * 1000; //20초 셋팅

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_layout, container, false);
        binding.setFragment(this);

        nowTime = System.currentTimeMillis();
        binding.lastRefresh.setText( getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)) );
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            // 권한 없음
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Log.e("DB", "PERMISSION GRANTED");
            }
        } else {
            // 권한 있음
            getWeather();
        }

        return binding.getRoot();
    }

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    @Override
    public void setWeatherIcon(final int image) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.weatherIcon.setImageResource(image);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void setProgress(boolean state) {
        if (state) {
            binding.overlay.setVisibility(View.VISIBLE);
        } else {
            binding.overlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeather();
            }
        }
    }

    private void getWeather() {
        if ( lm == null )
            lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if ( !isLocation ) isLocation = true;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        boolean statusOfGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean statusOfNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if ( statusOfGPS ) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    this);
        }
        if ( statusOfNetwork ) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);
        }

        binding.kcalView.setNumber(236);
        presenter = new ActivityFragmentPresenter(getContext(), this);
        setProgress(true);
    }

    @Override
    public void onLocationChanged(final Location location) {
        if ( isLocation ) {
            double lon = location.getLongitude(); //경도
            double lat= location.getLatitude();   //위도
            float acc = location.getAccuracy();    //정확도
            String provider = location.getProvider();
            presenter.getWeather(lat, lon, new ActivityFragmentPresenter.WeatherCallback() {
                @Override
                public <T> void onResponse(Response<T> response) {
                    if ( response.body() != null ) {
                        if ( isActivity ) {
                            Weatherbit weatherbit = (Weatherbit) response.body();
                            float uv = weatherbit.getData().get(0).getUv(),
                                    ozone = weatherbit.getData().get(0).getOzone(),
                                    windspeed = weatherbit.getData().get(0).getWind_spd();
                            binding.temp.setText(String.format("%.0f˚", weatherbit.getData().get(0).getTemp()));
                            binding.cityName.setText(weatherbit.getCity_name());
                            binding.district.setText(weatherbit.getDistrict());
                            binding.windSpeed.setText(String.format("%.1fm/s", windspeed));
                            binding.uv.setText(String.format("%.0f", uv));
                            binding.ozone.setText(String.format("%.3fppm", ozone));
                            presenter.getWeatherIcon(getContext(), weatherbit.getData().get(0).getWeather().getIcon());

                            /* uv description (s) */
                            String[] uvDesciption = getResources().getStringArray(R.array.uv_description_arr),
                                    ozoneDescription = getResources().getStringArray(R.array.ozone_description_arr),
                                    windspeedDescription = getResources().getStringArray(R.array.wind_speed_description_arr);
                            int uvState = 0, ozoneState = 0, windspeedState = 0;
                            if ( uv >= 0 && uv < 3 )
                                uvState = 0;
                            else if ( uv >= 3 && uv < 6 )
                                uvState = 1;
                            else if ( uv >= 6 && uv < 8 )
                                uvState = 2;
                            else if ( uv >= 8 && uv < 11 )
                                uvState = 3;
                            else
                                uvState = 4;
                            /* uv description (e) */

                            /* ozone description (s) */
                            if ( ozone >= 0 && ozone < 0.031 )
                                ozoneState = 0;
                            else if ( ozone >= 0.031 && ozone < 0.091 )
                                ozoneState = 1;
                            else if ( ozone >= 0.091 && ozone < 0.151 )
                                ozoneState = 2;
                            else
                                ozoneState = 3;
                            /* ozone description (e) */

                            /* wind speed description (s) */
                            if ( windspeed >= 0 && windspeed < 4 )
                                windspeedState = 0;
                            else if ( windspeed >= 4 && windspeed < 9 )
                                windspeedState = 1;
                            else if ( windspeed >= 9 && windspeed < 14 )
                                windspeedState = 2;
                            else
                                windspeedState = 3;
                            /* ozone description (e) */
                            binding.uvStr.setText(uvDesciption[uvState]);
                            binding.ozoneStr.setText(ozoneDescription[ozoneState]);
                            binding.windSpeedStr.setText(windspeedDescription[windspeedState]);
                        }
                    } else {
                        binding.district.setText("위치를 파악할 수 없음");
                    }
                    setProgress(false);
                    isLocation = false;
                }
            });
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public void onRefresh ( View v ) {
        rotationState = rotationState ? VIewUtil.rotationStop(v) : VIewUtil.rotationStart(v);
        if ( rotationState ) {
            nowTime = System.currentTimeMillis();
            binding.lastRefresh.setText( getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)) );
            rotationState = VIewUtil.rotationStop(v);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivity = true;
        getWeather();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isActivity = false;
        if ( lm != null ) {
            lm.removeUpdates(this);
            lm = null;
        }

    }
    public void refreshWeather( View view ) {
        getWeather();
    }
}
