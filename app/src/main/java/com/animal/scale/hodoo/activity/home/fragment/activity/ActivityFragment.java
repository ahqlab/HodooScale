package com.animal.scale.hodoo.activity.home.fragment.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
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

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.FragmentActivityLayoutBinding;
import com.animal.scale.hodoo.domain.Weatherbit;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ActivityFragment extends Fragment implements ActivityFragmentIn.View, LocationListener {
    FragmentActivityLayoutBinding binding;
    ActivityFragmentIn.Presenter presenter;
    private LocationManager lm;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

    private final int REQUEST_LOCATION = 100;
    private long nowTime;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_layout, container, false);

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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                100,
                1,
                this);

        binding.kcalView.setNumber(236);
        presenter = new ActivityFragmentPresenter(getContext(), this);

    }

    @Override
    public void onLocationChanged(Location location) {
        double lon = location.getLongitude(); //경도
        double lat= location.getLatitude();   //위도
        float acc = location.getAccuracy();    //정확도
        String provider = location.getProvider();
        presenter.getWeather(lat, lon, new ActivityFragmentPresenter.WeatherCallback() {
            @Override
            public <T> void onResponse(Response<T> response) {
                Weatherbit weatherbit = (Weatherbit) response.body();
                binding.temp.setText(String.format("%.0f˚", weatherbit.getData().get(0).getTemp()));
                binding.cityName.setText(weatherbit.getCity_name());
                binding.district.setText(weatherbit.getDistrict());
                binding.windSpeed.setText(String.format("%.1fm/s", weatherbit.getData().get(0).getWind_spd()));
                binding.uv.setText(String.format("%.0f", weatherbit.getData().get(0).getUv()));
                binding.ozon.setText(String.format("%.3fppm", weatherbit.getData().get(0).getOzone()));
                presenter.getWeatherIcon(getContext(), weatherbit.getData().get(0).getWeather().getIcon());

            }
        });

        Log.e(TAG, String.format("lon : %f, lat : %f", lon, lat));
        lm.removeUpdates(this);
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
}
