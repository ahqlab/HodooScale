package com.animal.scale.hodoo.activity.pet.regist;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityPhysiqueInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Groups;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.tistory.dwfox.dwrulerviewlibrary.utils.DWUtils;
import com.tistory.dwfox.dwrulerviewlibrary.view.DWRulerSeekbar;
import com.tistory.dwfox.dwrulerviewlibrary.view.ObservableHorizontalScrollView;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiqueInformationRegistActivity extends BaseActivity<PhysiqueInformationRegistActivity> {

    ActivityPhysiqueInformationRegistBinding binding;

    public int petId;

    BottomDialog builder;

    private ScrollingValuePicker myScrollingValuePicker;
    private DWRulerSeekbar dwRulerSeekbar;

    private static final float MIN_VALUE = 5;
    private static final float MAX_VALUE = 33;
    private static final float LINE_RULER_MULTIPLE_SIZE = 3.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_physique_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.physique_information_regist_title)));
        super.setToolbarColor();
        getPetId();
        setNavi();
    }
    @Override
    protected BaseActivity<PhysiqueInformationRegistActivity> getActivityClass() {
        return PhysiqueInformationRegistActivity.this;
    }

    public int getPetId(){
        Call<Groups> result = NetRetrofit.getInstance().getGroupsService().get(new Groups(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID)));
        result.enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(Call<Groups> call, Response<Groups> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        petId = response.body().getPetId();
                        Call<PetPhysicalInfo> result = NetRetrofit.getInstance().getPetPhysicalInfoService().get(response.body().getPetId());
                        result.enqueue(new Callback<PetPhysicalInfo>() {
                            @Override
                            public void onResponse(Call<PetPhysicalInfo> call, Response<PetPhysicalInfo> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        binding.setDomain(response.body());
                                    }else{
                                        binding.setDomain(new PetPhysicalInfo(petId));
                                    }
                                }else{
                                    binding.setDomain(new PetPhysicalInfo(petId));
                                }
                            }
                            @Override
                            public void onFailure(Call<PetPhysicalInfo> call, Throwable t) {
                                binding.setDomain(new PetPhysicalInfo(petId));
                            }
                        });
                    } else {
                        showToast("PET ID를 가져오지 못했습니다.");
                    }
                } else {
                    showToast("PET ID를 가져오지 못했습니다.");
                }
            }
            @Override
            public void onFailure(Call<Groups> call, Throwable t) {
                showToast("PET ID를 가져오지 못했습니다.");
            }
        });
        return petId;
    }

    public void onClickWidthEt(View view){
        showRulerBottomDlg(binding.editWidth);
    }

    public void onClickHightEt(View view){
        showRulerBottomDlg(binding.editHeight);

    }
    public void onClickWeightEt(View view){
        showRulerBottomDlg(binding.editWeight);
    }

    public void showRulerBottomDlg(final EditText editText){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_custom_ruler_popup, null);
        builder =  new BottomDialog.Builder(this)
                .setCustomView(customView)
                .setNegativeText("확인")
                .show();

        myScrollingValuePicker = (ScrollingValuePicker) customView.findViewById(R.id.myScrollingValuePicker);
        myScrollingValuePicker.setViewMultipleSize(LINE_RULER_MULTIPLE_SIZE);
        myScrollingValuePicker.setMaxValue(MIN_VALUE, MAX_VALUE);
        myScrollingValuePicker.setValueTypeMultiple(5);
        myScrollingValuePicker.getScrollView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    myScrollingValuePicker.getScrollView().startScrollerTask();
                }
                return false;
            }
        });
        myScrollingValuePicker.setOnScrollChangedListener(new ObservableHorizontalScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView view, int l, int t) {
            }
            @Override
            public void onScrollStopped(int l, int t) {
                editText.setText(String.valueOf(DWUtils.getValueAndScrollItemToCenter(myScrollingValuePicker.getScrollView() , l , t , MAX_VALUE , MIN_VALUE , myScrollingValuePicker.getViewMultipleSize())));
            }
        });
    }

    public void onClickNextBtn(View view){
        Log.e("getDomain : " ,  binding.getDomain().toString());
        Log.e("getDomain : " ,  binding.getDomain().toString());
        Log.e("getDomain : " ,  binding.getDomain().toString());
        Call<Integer> result = NetRetrofit.getInstance().getPetPhysicalInfoService().regist(binding.getDomain());
        result.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if(response.body() == 1){
                            Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                            finish();
                        }else{
                            showToast("등록에 실패했습니다.");
                        }
                    }else{
                        showToast("등록에 실패했습니다.");
                    }
                }else{
                    showToast("등록에 실패했습니다.");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                showToast("등록에 실패했습니다.");
            }
        });
    }

    private void setNavi() {
        binding.addPetNavigation.physiqueBtn.setBackgroundResource(R.drawable.add_pet_nav_reverse_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
    }
}
