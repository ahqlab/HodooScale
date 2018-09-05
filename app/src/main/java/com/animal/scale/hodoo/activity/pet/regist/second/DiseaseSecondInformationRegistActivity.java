package com.animal.scale.hodoo.activity.pet.regist.second;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfDiseaseList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySecondDiseaseInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseaseSecondInformationRegistActivity extends BaseActivity<DiseaseSecondInformationRegistActivity> {

    public int petId = 0;

    ActivitySecondDiseaseInformationRegistBinding binding;

    AdapterOfDiseaseList Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_disease_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.disease_information_regist_title)));
        super.setToolbarColor();
        Intent intent = getIntent();
        petId = intent.getIntExtra("petId", 0);
        setListviewAdapter(petId);
        setNavi(); //PET ID 를 가져오기 위해 서버에 요청!!
    }

    public void setListviewAdapter(int petId) {
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
                        Adapter = new AdapterOfDiseaseList(DiseaseSecondInformationRegistActivity.this, diseases, response.body());
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
    protected BaseActivity<DiseaseSecondInformationRegistActivity> getActivityClass() {
        return DiseaseSecondInformationRegistActivity.this;
    }

    public void onClickNextBtn(View view) {
        Call<Integer> result = NetRetrofit.getInstance().getPetChronicDiseaseService().delete(petId);
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
                                                Intent intent = new Intent(getApplicationContext(), PhysiqueSecondInformationRegistActivity.class);
                                                intent.putExtra("petId", petId);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                                                finish();
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
    }

    private void setNavi() {
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.add_pet_nav_reverse_btn);
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
}
