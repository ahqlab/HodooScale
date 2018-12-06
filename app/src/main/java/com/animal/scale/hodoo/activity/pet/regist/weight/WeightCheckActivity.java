package com.animal.scale.hodoo.activity.pet.regist.weight;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityWeightCheckBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.util.ViewFlipperAction;

import java.util.ArrayList;
import java.util.List;

public class WeightCheckActivity extends BaseActivity<WeightCheckActivity> implements WeightCheckIn.View {

    //뷰플리퍼
    //인덱스
    List<ImageView> indexes;

    ActivityWeightCheckBinding binding;

    WeightCheckIn.Presenter presenter;

    private int petIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weight_check);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_check_title)));
        super.setToolbarColor();
        presenter = new WeightCheckPresenter(this);
        presenter.loadData(WeightCheckActivity.this);
        presenter.setNavigation();
        //
        //presenter.setViewFlipper();
        Intent intent = getIntent();
        petIdx = intent.getIntExtra("petIdx", 0);
        presenter.getWeightInformation(petIdx);
    }

    @Override
    protected BaseActivity<WeightCheckActivity> getActivityClass() {
        return WeightCheckActivity.this;
    }

    @Override
    public void setDomain(PetWeightInfo petWeightInfo) {
        if (petWeightInfo != null) {
            binding.setDomain(petWeightInfo);
            setNumberPicker(binding.bcs, petWeightInfo);
        } else {
            binding.setDomain(new PetWeightInfo());
            setNumberPicker(binding.bcs);
        }
    }

    private void setNumberPicker(NumberPicker numberPicker, PetWeightInfo petWeightInfo) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(petWeightInfo.getBcs());
        numberPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.getDomain().setBcs(newVal);
            }
        });
    }

    private void setNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.getDomain().setBcs(newVal);
            }
        });
    }

    @Override
    public void registWeightInformation() {
        presenter.registWeightInfo(petIdx, binding.getDomain());
    }

    @Override
    public void registResult(Integer integer) {
        if (integer != 0) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            finish();
        } else {
            Log.e("HJLEE", "registResult ERROR");
        }
    }

    public void onClickCompleateBtn(View view) {
        if (binding.getDomain().getBcs() > 0) {
            presenter.deleteWeightInfo(petIdx, binding.getDomain().getId());
        } else {
            super.showBasicOneBtnPopup(null, getResources().getString(R.string.istyle_required_select_bcs_message))
                    .setPositiveButton(getResources().getString(R.string.istyle_required_select_bcs_message), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    /*@Override
    public void setViewFlipper() {
        //인덱스리스트
        indexes = new ArrayList<>();
        indexes.add(binding.depth1);
        indexes.add(binding.depth2);
        indexes.add(binding.depth3);
        indexes.add(binding.depth4);
        indexes.add(binding.depth5);
        //inflate
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.viewflipper1, binding.flipper, false);
        View view2 = inflater.inflate(R.layout.viewflipper2, binding.flipper, false);
        View view3 = inflater.inflate(R.layout.viewflipper3, binding.flipper, false);
        View view4 = inflater.inflate(R.layout.viewflipper4, binding.flipper, false);
        View view5 = inflater.inflate(R.layout.viewflipper5, binding.flipper, false);
        //inflate 한 view 추가
        binding.flipper.addView(view1);
        binding.flipper.addView(view2);
        binding.flipper.addView(view3);
        binding.flipper.addView(view4);
        binding.flipper.addView(view5);
        //리스너설정 - 좌우 터치시 화면넘어가기
        binding.flipper.setOnTouchListener(new ViewFlipperAction(this, binding.flipper));
    }



    public void setDisplayFirst(View view) {
        binding.flipper.setDisplayedChild(0);
        binding.getDomain().setBcs(0);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplaySecond(View view) {
        binding.flipper.setDisplayedChild(1);
        binding.getDomain().setBcs(1);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayThird(View view) {
        binding.flipper.setDisplayedChild(2);
        binding.getDomain().setBcs(2);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayFour(View view) {
        binding.flipper.setDisplayedChild(3);
        binding.getDomain().setBcs(3);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayFive(View view) {
        binding.flipper.setDisplayedChild(4);
        binding.getDomain().setBcs(4);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
    }


    @Override
    public void onFlipperActionCallback(int position) {
        if (position == 0) {
            //binding.getDomain().setBcs(0);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 1) {
           // binding.getDomain().setBcs(1);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 2) {
            //binding.getDomain().setBcs(2);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 3) {
           //binding.getDomain().setBcs(3);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 4) {
            //binding.getDomain().setBcs(4);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
        }
    }*/


    @Override
    public void setNavigation() {
        binding.addPetNavigation.weightBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        /*binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });*/
    }


}