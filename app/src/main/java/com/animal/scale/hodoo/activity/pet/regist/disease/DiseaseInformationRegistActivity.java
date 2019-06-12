package com.animal.scale.hodoo.activity.pet.regist.disease;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.weight.WeightCheckActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.adapter.AdapterOfDisease;
import com.animal.scale.hodoo.adapter.AdapterOfDiseaseList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityDiseaseInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 징별등록 Activity (현재 사용안함.)
 */
public class DiseaseInformationRegistActivity extends BaseActivity<DiseaseInformationRegistActivity> implements DiseaseInformationIn.View{

    public static Context mContext;

    public int petId = 0;

    ActivityDiseaseInformationRegistBinding binding;

    AdapterOfDisease adapter;

    DiseaseInformationIn.Presenter presenter;

    private int petIdx;

    private boolean deleteAllPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disease_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.disease_information_regist_title)));
        mContext = this;
        super.setToolbarColor();
        presenter = new DiseaseInformationPresenter(this);
        presenter.loadData(DiseaseInformationRegistActivity.this);
        presenter.setNavigation();


        Intent intent = getIntent();
        petIdx = intent.getIntExtra("petIdx", 0);
        deleteAllPet = intent.getBooleanExtra("deleteAllPet", false);
        Log.e("HJLEE", "diseases deleteAllPet : " + deleteAllPet);
        presenter.getDiseaseInformation(petIdx);

    }

    /**
     * 질병정보 셋팅
     *
     * @param petChronicDisease
     */
    @Override
    public void setDiseaseInfo(PetChronicDisease petChronicDisease) {
        List<PetChronicDisease> list;
        if(petChronicDisease != null){
            binding.setDomain(petChronicDisease);
//            list = presenter.stringToListConversion(petChronicDisease.getDiseaseNameStr());
            setListviewAdapter(petChronicDisease);
        }else{
            binding.setDomain(new PetChronicDisease());
//            list = new ArrayList<PetChronicDisease>();
            setListviewAdapter(petChronicDisease);
        }
    }

    /**
     * 질별 리스트 아답터 셋팅
     * @param petChronicDisease
     */
    public void setListviewAdapter(PetChronicDisease petChronicDisease){
        final List<Disease> diseases = new ArrayList<Disease>();

        /* new code 2018.12.26 (s) */
        String[] diseasesStr = getResources().getStringArray(R.array.disease);
        for ( String disease : diseasesStr )
            diseases.add(new Disease(disease));
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        binding.recyclerview.setLayoutManager(layoutManager);

        adapter = new AdapterOfDisease(diseases, petChronicDisease);
        binding.recyclerview.setAdapter(adapter);
        /* new code 2018.12.26 (e) */


//        Adapter = new AdapterOfDiseaseList(DiseaseInformationRegistActivity.this, diseases, petChronicDisease);
//        binding.listview.setAdapter(Adapter);
    }

    @Override
    protected BaseActivity<DiseaseInformationRegistActivity> getActivityClass() {
        return DiseaseInformationRegistActivity.this;
    }

    /**
     * 다음 버튼 클릭
     * @param view
     */
    public void onClickNextBtn(View view){
        if ( binding.getDomain() != null )
            presenter.deleteDiseaseInformation(petIdx, binding.getDomain().getId());
    }

    /**
     * ?????
     * @return
     */
    public String getDiseaName(){
        StringBuilder sb = new StringBuilder();
//        if (Adapter.getCheckedCount() > 0) {
//            for (int i = 0; i < Adapter.getCount(); i++) {
//                Disease disease = (Disease) Adapter.getItem(i);
//                if (disease.isChecked()) {
//                    sb.append(disease.getName());
//                    sb.append(",");
//                }
//            }
//        }
        int checkNumber = adapter.getCheckNumber();
        if ( checkNumber > 0 ) {
            for (int i = 0; i < adapter.mItems.size(); i++) {
                if ( (checkNumber & (0x01<<i)) != 0 ) {
                    Disease disease = adapter.getItem(i);
                    sb.append(disease.getName());
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 질병 등록
     */
    @Override
    public void registDiseaseInformation() {
        PetChronicDisease petChronicDisease =  binding.getDomain();
        petChronicDisease.setDiseaseName(adapter.getCheckNumber());
        presenter.registDiseaseInformation(petChronicDisease,  petIdx);
    }

    /**
     * 다음페이지 이동
     *
     * @param result
     */
    @Override
    public void nextStep(int result) {
        Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        intent.putExtra("deleteAllPet", deleteAllPet);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//        finish();
    }

    /**
     * 사용안함.
     */
    @Override
    public void setNavigation() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();*/
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
