package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.search.MealSearchActivity;
import com.animal.scale.hodoo.databinding.FragmentMealBinding;

import noman.weekcalendar.WeekCalendar;

public class MealFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private WeekCalendar weekCalendar;

    FragmentMealBinding binding;

    public MealFragment() {
    }

    public static MealFragment newInstance() {
        return new MealFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal, container, false);
        binding.setActivity(this);
        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

   public void onClickRegistBtn(View view){
        Intent intent = new Intent(getActivity(), MealSearchActivity.class);
        startActivity(intent);
    }
}
