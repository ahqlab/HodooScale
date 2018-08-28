package com.animal.scale.hodoo.activity.pet.regist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfDiseaseList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityDiseaseInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.Groups;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseaseInformationRegistActivity extends BaseActivity<DiseaseInformationRegistActivity> {

    public int petId = 0;

    ActivityDiseaseInformationRegistBinding binding;

    AdapterOfDiseaseList Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disease_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.disease_information_regist_title)));
        super.setToolbarColor();
        setNavi();
        //PET ID 를 가져오기 위해 서버에 요청!!
        init();
    }

    public void init(){
        Call<Groups> result = NetRetrofit.getInstance().getGroupsService().get(new Groups(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID)));
        result.enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(Call<Groups> call, Response<Groups> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        petId = response.body().getPetId();
                        setListviewAdapter(petId);
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
    }

    public void setListviewAdapter(int petId){
        final List<Disease> diseases = new ArrayList<Disease>();
        diseases.add(new Disease("질병없음"));
        diseases.add(new Disease("당뇨"));
        diseases.add(new Disease("알러지"));
        diseases.add(new Disease("치매"));
        diseases.add(new Disease("관절염"));
        diseases.add(new Disease("파보 장염"));
        diseases.add(new Disease("비만"));
        diseases.add(new Disease("암"));

        Call<List<PetChronicDisease>> resultList = NetRetrofit.getInstance().getPetChronicDiseaseService().list(petId);
        resultList.enqueue(new Callback<List<PetChronicDisease>>() {
            @Override
            public void onResponse(Call<List<PetChronicDisease>> call, Response<List<PetChronicDisease>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Adapter = new AdapterOfDiseaseList(DiseaseInformationRegistActivity.this, diseases, response.body());
                        binding.listview.setAdapter(Adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PetChronicDisease>> call, Throwable t) {
            }
        });
    }

    @Override
    protected BaseActivity<DiseaseInformationRegistActivity> getActivityClass() {
        return DiseaseInformationRegistActivity.this;
    }

    public void onClickNextBtn(View view){
        Call<Integer> result = NetRetrofit.getInstance().getPetChronicDiseaseService().delete(petId);
        //result.execute().body().toString();
        result.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //if (Adapter.getCheckedCount() > 0) {
                            for (int i = 0; i < Adapter.getCount(); i++) {
                                Disease disease = (Disease) Adapter.getItem(i);
                                if (disease.isChecked()) {
                                    Call<Integer> result = NetRetrofit.getInstance().getPetChronicDiseaseService().insert(new PetChronicDisease(petId, disease.getName()));
                                    result.enqueue(new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body() != null) {
                                                    //showToast("성공");
                                                    //init();
                                                    Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                                                    finish();
                                                    //showToast("Call init");
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Integer> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                showToast("삭제에 실패했습니다.");
            }
        });
        /**/
       /* */
    }

    private void setNavi() {
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.add_pet_nav_reverse_btn);
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
