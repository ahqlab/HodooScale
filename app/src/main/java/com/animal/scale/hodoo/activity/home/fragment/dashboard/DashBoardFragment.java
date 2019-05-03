package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.animal.scale.hodoo.activity.meal.list.FeedListActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.FragmentDashBoardBinding;
import com.animal.scale.hodoo.databinding.LayoutNumberKeyboardBinding;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.RER;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public class DashBoardFragment extends Fragment implements DashBoardIn.View, NavigationView.OnNavigationItemSelectedListener {
    private FragmentDashBoardBinding binding;
    private PetAllInfos selectPet;
    private DashBoardIn.Presenter presenter;

    private boolean state;
    private final String suffixStr = "kg";

    private PetPhysicalInfo info;
    private Dialog dialog;

    private PetAllInfos petAllInfos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);
        presenter = new DashBoardPresenter(this);
        presenter.initData(getContext());

        binding.mealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedListActivity.class);
                intent.putExtra("selectPet", selectPet);
                startActivity(intent);
            }
        });
        binding.weightModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = false;

                final LayoutNumberKeyboardBinding keyboardBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_number_keyboard, null, false);
                keyboardBinding.setActivity(DashBoardFragment.this);
                keyboardBinding.setState(false);
                keyboardBinding.setWeight(petAllInfos.petPhysicalInfo.getWeight());

                dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialog.setContentView(keyboardBinding.getRoot());
                dialog.create();
                dialog.show();

                for (int i = 0; i < keyboardBinding.numericKeybord.getChildCount(); i++) {
                    final int position = i;
                    keyboardBinding.numericKeybord.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if ( info == null )
                                info = new PetPhysicalInfo();
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
                                } else
                                    state = true;
                            }
                            info.setWeight(kilogram);
                            keyboardBinding.setState(state);
                            keyboardBinding.kilogram.setText(kilogram + suffixStr);
                        }
                    });
                }
                if ( Float.parseFloat( petAllInfos.petPhysicalInfo.getWeight() ) > 0 )
                    keyboardBinding.setState(true);
//                keyboardBinding.ㅇ.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.e("test", "ㅇ 클릭");
//                    }
//                });

                keyboardBinding.doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( info == null ) {
                            info = new PetPhysicalInfo();
                            info.setWeight("0");
                        }
                        info.setId(selectPet.pet.getPhysical());
                        presenter.updatePhysical(info);

                    }
                });

                /* 상단 닫기 버튼 이벤트 */
                keyboardBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

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
            presenter.getPetAllInfos( selectPet.getPet().getPetIdx() );
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
    public void physicalUpdateDone(PetPhysicalInfo result) {
        if ( result != null ) {
            dialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("변경완료")
                    .setMessage("현재 체중이 변경완료되었습니다.")
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();
            HomeActivity.selectPet.setPetPhysicalInfo(result);
            selectPet = HomeActivity.selectPet;
            binding.setDomain( selectPet );
//            binding.nowWeight.setText( selectPet.petPhysicalInfo.getWeight() + "kg");
            presenter.getGoalWeight(
                    Float.parseFloat(selectPet.petPhysicalInfo.getWeight()),
                    petAllInfos.petUserSelectionQuestion != null ? petAllInfos.petUserSelectionQuestion.getBodyFat() : 20,
                    petAllInfos.getPetBasicInfo().getPetType() );
        }
    }

    @Override
    public void setDevice(List<Device> devices) {
//        Device connectDevice = null;
//        for (int i = 0; i < devices.size(); i++) {
//            if ( devices.get(i).getConnect())
//        }
    }

    @Override
    public void setPetAllInfos(PetAllInfos petAllInfos) {
        this.petAllInfos = petAllInfos;
        binding.nowWeight.setText( petAllInfos.petPhysicalInfo.getWeight() + "kg");
        presenter.setAverageWeight(petAllInfos.petPhysicalInfo.getWeight());
        presenter.getGoalWeight(
                Float.parseFloat(petAllInfos.petPhysicalInfo.getWeight()),
                petAllInfos.petUserSelectionQuestion != null ? petAllInfos.petUserSelectionQuestion.getBodyFat() : 20,
                petAllInfos.getPetBasicInfo().getPetType() );
        String[] reskLevelArr = getContext().getResources().getStringArray(R.array.risk_level_item);
        int number = ((petAllInfos.petUserSelectionQuestion != null ? petAllInfos.petUserSelectionQuestion.getBodyFat() : 20) - 10) / 10 - 1;
        binding.riskLevel.setText( reskLevelArr[((petAllInfos.petUserSelectionQuestion != null ? petAllInfos.petUserSelectionQuestion.getBodyFat() : 20) - 10) / 10 - 1] );
        binding.goalMeal.setText( String.valueOf( (int) new RER(Float.parseFloat(presenter.getTodayAverageWeight()), selectPet.getFactor()).getRER() ) + "kcal" );
    }

    @Override
    public void setWeightGoal(WeightGoalChart weightGoal) {
        binding.goalWeight.setText( String.format("%dkg", (int) weightGoal.getWeightGoal()) );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    public void setData() {
        if ( HomeActivity.selectPet != null )
            setSelectPet(HomeActivity.selectPet);
    }
}
