package com.animal.scale.hodoo.activity.home.temp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.FragmentTempBinding;

public class TempFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {


    FragmentTempBinding binding;

    public TempFragment() {
    }

    public static TempFragment newInstance() {
        return new TempFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_temp, container, false);
        binding.setActivity(this);
        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e("HJLEE", "TF " + item.getItemId());
        return false;
    }


}
