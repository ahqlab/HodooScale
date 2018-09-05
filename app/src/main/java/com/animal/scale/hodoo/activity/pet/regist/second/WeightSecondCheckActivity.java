package com.animal.scale.hodoo.activity.pet.regist.second;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.HomeActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySecondWeightCheckBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.ViewFlipperAction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeightSecondCheckActivity extends BaseActivity<WeightSecondCheckActivity> implements ViewFlipperAction.ViewFlipperCallback {

    //뷰플리퍼
    //인덱스
    List<ImageView> indexes;

    ActivitySecondWeightCheckBinding binding;

    public int petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_weight_check);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_check_title)));
        super.setToolbarColor();
        Intent intent = getIntent();
        petId = intent.getIntExtra("petId", 0);
        setNavi();
        init();
        serViewFlipper();

    }

    @Override
    protected BaseActivity<WeightSecondCheckActivity> getActivityClass() {
        return WeightSecondCheckActivity.this;
    }

    public void init() {
        Call<PetWeightInfo> result = NetRetrofit.getInstance().getPetWeightInfoService().get(petId);
        result.enqueue(new Callback<PetWeightInfo>() {
            @Override
            public void onResponse(Call<PetWeightInfo> call, Response<PetWeightInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        binding.setDomain(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<PetWeightInfo> call, Throwable t) {
                binding.setDomain(new PetWeightInfo(petId));
            }
        });
    }


    public void onClickCompleateBtn(View view) {
        Call<Integer> result = NetRetrofit.getInstance().getPetWeightInfoService().regist(binding.getDomain());
        result.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body() == 1) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                            finish();
                        } else {
                            showToast("등록에 실패했습니다.");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                showToast("등록에 실패했습니다.");
            }
        });
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
    }

    private void setNavi() {
        binding.addPetNavigation.weightBtn.setBackgroundResource(R.drawable.add_pet_nav_reverse_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BasicSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiseaseSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightSecondCheckActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
    }

    private void serViewFlipper() {
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


}