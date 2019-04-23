package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.FragmentDashBoardBinding;
import com.animal.scale.hodoo.databinding.LayoutNumberKeyboardBinding;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.util.DateUtil;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public class DashBoardFragment extends Fragment implements DashBoardIn.View, NavigationView.OnNavigationItemSelectedListener {
    private FragmentDashBoardBinding binding;
    private PetAllInfos selectPet;
    private DashBoardIn.Presenter presenter;

    private boolean state;
    private final String suffixStr = "kg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);
        presenter = new DashBoardPresenter(this);
        presenter.initData(getContext());

        binding.weightModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = false;

                final LayoutNumberKeyboardBinding keyboardBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_number_keyboard, null, false);
                keyboardBinding.setActivity(DashBoardFragment.this);
                keyboardBinding.setState(false);
                keyboardBinding.setWeight("0");
                Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialog.setContentView(keyboardBinding.getRoot());
                dialog.create();
                dialog.show();

                for (int i = 0; i < keyboardBinding.numericKeybord.getChildCount(); i++) {
                    final int position = i;
                    keyboardBinding.numericKeybord.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String kilogram = keyboardBinding.kilogram.getText().toString();
                            kilogram = kilogram.replaceAll(suffixStr, "");

                            float kilogramInt = Float.parseFloat(kilogram);

                            if ( view instanceof TextView) {
                                TextView textView = (TextView) view;
                                if ( kilogramInt == 0 ) {
                                    kilogram = textView.getText().toString();
                                } else {
                                    kilogram += textView.getText().toString();
                                }
                                state = true;
                            } else if ( view instanceof ImageView) {
                                kilogram = kilogram.substring(0, kilogram.length() - 1);
                                if ( kilogram.indexOf(".") > -1 ) {
                                    String[] splitStr = kilogram.split("\\.");
                                    if ( splitStr.length == 1 ) {
                                        kilogram = splitStr[0];
                                    }
                                }
                                if ( kilogram.equals("") ) {
                                    kilogram = "0";
                                    state = false;
                                }
                            }
                            keyboardBinding.setState(state);
                            keyboardBinding.kilogram.setText(kilogram + suffixStr);
                        }
                    });
                }

            }
        });

        if ( getArguments() != null ) {
            setSelectPet((PetAllInfos) getArguments().getSerializable("selectPet"));
        }
        return binding.getRoot();
    }
    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setSelectPet(PetAllInfos selectPet) {
        if ( selectPet != null ) {
            this.selectPet = selectPet;
            binding.nowWeight.setText( selectPet.petPhysicalInfo.getWeight() + "kg");
            presenter.getTodaySumCalorie(DateUtil.getCurrentDatetime());
        }
    }

    @Override
    public void setMealHistory(MealHistory mealHistoryCommonResponce) {
        if ( mealHistoryCommonResponce != null ) {
            /* 소수점 두자리까지만 표기한다. */
            binding.nowCalorie.setText( String.format("%.2fkcal", mealHistoryCommonResponce.getCalorie()) ) ;
        }
//        binding.nowCalorie.setText( String.format("%fkcal", mealHistory.getCalorie()) ) ;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    public void setData() {
        if ( HomeActivity.selectPet != null )
            setSelectPet(HomeActivity.selectPet);
    }
    public void onClickDone ( View view ) {

    }
}
