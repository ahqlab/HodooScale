package com.animal.scale.hodoo.activity.pet.regist.fragment.disease;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.BasicInfomationFragment;
import com.animal.scale.hodoo.adapter.AdapterOfDisease;
import com.animal.scale.hodoo.base.BaseFragment;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.databinding.FragmentDiseaseInfomationBinding;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import static com.animal.scale.hodoo.activity.setting.list.SettingListActivity.TAG;

/**
 * Created by SongSeokwoo on 2019-04-02.
 */
public class DiseaseInfomationFragment extends PetRegistFragment implements DiseaseInfomationIn.View {
    private FragmentDiseaseInfomationBinding binding;
    private DiseaseInfomationIn.Presenter presenter;
    private int petIdx = 0;
    private AdapterOfDisease adapter;
    private String TAG = DiseaseInfomationFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_disease_infomation, container, false);
        binding.setActivity(this);
        if ( getArguments() != null )
            petIdx = getArguments().getInt("petIdx");

        presenter = new DiseaseInfomationPresenter(this);
        presenter.initData(getContext());
        presenter.getDiseaseInformation(petIdx);

        return binding.getRoot();
    }
    public static PetRegistFragment newInstance() {
        return new DiseaseInfomationFragment();
    }

    public void onClickNextBtn ( View v ) {
        registDiseaseInformation();
    }

    @Override
    public void setDiseaseInfo(PetChronicDisease petChronicDisease) {
        Log.e(TAG, "setDiseaseInfo");
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

    @Override
    public void setNavigation() {
//        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
    }

    public void setListviewAdapter(PetChronicDisease petChronicDisease){
        final List<Disease> diseases = new ArrayList<Disease>();

        /* new code 2018.12.26 (s) */
        String[] diseasesStr = getResources().getStringArray(R.array.disease);
        for ( String disease : diseasesStr )
            diseases.add(new Disease(disease));
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        binding.recyclerview.setLayoutManager(layoutManager);

        adapter = new AdapterOfDisease(diseases, petChronicDisease);
        if ( petChronicDisease != null )
            binding.setChecked( petChronicDisease.getDiseaseName() > 0 );
        adapter.setOnCheckListener(new AdapterOfDisease.OnCheckListener() {
            @Override
            public void setOnChecked(int number) {
                if ( number == 0 )
                    binding.setChecked( false );
                else
                    binding.setChecked( true );
            }
        });
        binding.recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /* new code 2018.12.26 (e) */


//        Adapter = new AdapterOfDiseaseList(DiseaseInformationRegistActivity.this, diseases, petChronicDisease);
//        binding.listview.setAdapter(Adapter);
    }
    @Override
    public void registDiseaseInformation() {
        if ( adapter != null ) {
            PetChronicDisease petChronicDisease =  binding.getDomain();
            petChronicDisease.setDiseaseName(adapter.getCheckNumber());
            ((PetRegistActivity) getActivity()).setPetDiseaseInfo( petChronicDisease );
            ((PetRegistActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void nextStep(int result) {
//        ((PetRegistActivity) getActivity()).nextFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setPetIdx ( int petIdx ) {

    }
}
