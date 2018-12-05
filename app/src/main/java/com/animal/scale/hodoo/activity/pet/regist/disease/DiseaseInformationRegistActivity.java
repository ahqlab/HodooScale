package com.animal.scale.hodoo.activity.pet.regist.disease;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.weight.WeightCheckActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.adapter.AdapterOfDiseaseList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityDiseaseInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.ArrayList;
import java.util.List;

public class DiseaseInformationRegistActivity extends BaseActivity<DiseaseInformationRegistActivity> implements DiseaseInformationIn.View{

    public int petId = 0;

    ActivityDiseaseInformationRegistBinding binding;

    AdapterOfDiseaseList Adapter;

    DiseaseInformationIn.Presenter presenter;

    private int petIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disease_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.disease_information_regist_title)));
        super.setToolbarColor();
        presenter = new DiseaseInformationPresenter(this);
        presenter.loadData(DiseaseInformationRegistActivity.this);
        presenter.setNavigation();

        Intent intent = getIntent();
        petIdx = intent.getIntExtra("petIdx", 0);
        presenter.getDiseaseInformation(petIdx);

    }

    @Override
    public void setDiseaseInfo(PetChronicDisease petChronicDisease) {
        List<PetChronicDisease> list;
        if(petChronicDisease != null){
            binding.setDomain(petChronicDisease);
            list = presenter.stringToListConversion(petChronicDisease.getDiseaseName());
            setListviewAdapter(list);
        }else{
            binding.setDomain(new PetChronicDisease());
            list = new ArrayList<PetChronicDisease>();
            setListviewAdapter(list);
        }
    }

    public void setListviewAdapter(List<PetChronicDisease> petChronicDisease){
        final List<Disease> diseases = new ArrayList<Disease>();
        diseases.add(new Disease(getString(R.string.no_disease)));
        diseases.add(new Disease(getString(R.string.diabetes)));
        diseases.add(new Disease(getString(R.string.allergy)));
        diseases.add(new Disease(getString(R.string.dementia)));
        diseases.add(new Disease(getString(R.string.arthritis)));
        diseases.add(new Disease(getString(R.string.parboxylitis)));
        diseases.add(new Disease(getString(R.string.obesity)));
        diseases.add(new Disease(getString(R.string.cancer)));

        Adapter = new AdapterOfDiseaseList(DiseaseInformationRegistActivity.this, diseases, petChronicDisease);
        binding.listview.setAdapter(Adapter);
    }

    @Override
    protected BaseActivity<DiseaseInformationRegistActivity> getActivityClass() {
        return DiseaseInformationRegistActivity.this;
    }

    public void onClickNextBtn(View view){
        StringBuilder sb = new StringBuilder();
        if (Adapter.getCheckedCount() > 0) {
            for (int i = 0; i < Adapter.getCount(); i++) {
                Disease disease = (Disease) Adapter.getItem(i);
                if (disease.isChecked()) {
                    sb.append(disease.getName());
                    sb.append(",");
                }
            }
        }
        presenter.deleteDiseaseInformation(petIdx, binding.getDomain().getId());
    }

    public String getDiseaName(){
        StringBuilder sb = new StringBuilder();
        if (Adapter.getCheckedCount() > 0) {
            for (int i = 0; i < Adapter.getCount(); i++) {
                Disease disease = (Disease) Adapter.getItem(i);
                if (disease.isChecked()) {
                    sb.append(disease.getName());
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void registDiseaseInformation() {
        PetChronicDisease petChronicDisease =  binding.getDomain();
        petChronicDisease.setDiseaseName(getDiseaName());
        presenter.registDiseaseInformation(petChronicDisease,  petIdx);
    }

    @Override
    public void nextStep(int result) {
        Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void setNavigation() {
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
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
        /*binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
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
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
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
