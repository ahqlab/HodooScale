package com.animal.scale.hodoo.activity.home.fragment.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.activity.user.signup.SignUpActivity;
import com.animal.scale.hodoo.databinding.FragmentWelcomeHomeBinding;
import com.animal.scale.hodoo.util.CheckConnect;

import static android.support.constraint.Constraints.TAG;


public class WelcomeHomeFragment extends Fragment {
    private FragmentWelcomeHomeBinding binding;
    private static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";
    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_home, container, false);
        binding.setFragment(this);
        binding.progressLoader.setVisibility(View.GONE);
      /*  if (!isOnline()) {
            Toast.makeText(getContext(), R.string.not_connected_to_the_Internet, Toast.LENGTH_LONG).show();
            // new ServiceCheckTask().execute();
        }*/
        return binding.getRoot();
    }

    public static Fragment newInstance() {
        return new WelcomeHomeFragment();
    }

   /* private boolean isOnline() {
        CheckConnect cc = new CheckConnect(CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try {
            cc.join();
            return cc.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    public void onButtonClick( View v ) {
        Log.e(TAG, "buttonClick");
        switch (v.getId()) {
            case R.id.signup_btn:
                intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
