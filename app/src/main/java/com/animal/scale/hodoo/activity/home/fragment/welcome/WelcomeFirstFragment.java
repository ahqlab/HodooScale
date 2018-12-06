package com.animal.scale.hodoo.activity.home.fragment.welcome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.FragmentWelcomeFirstBinding;


public class WelcomeFirstFragment extends Fragment {
    private FragmentWelcomeFirstBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_first, container, false);
        return binding.getRoot();
    }

    public static Fragment newInstance() {
        return new WelcomeFirstFragment();
    }
}
