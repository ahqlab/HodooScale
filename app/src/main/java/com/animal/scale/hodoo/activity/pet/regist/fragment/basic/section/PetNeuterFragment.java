package com.animal.scale.hodoo.activity.pet.regist.fragment.basic.section;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.PetBasicInfoBaseFragment;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.databinding.FragmentPetNatuerBinding;
import com.animal.scale.hodoo.domain.PetBasicInfo;

/**
 * Created by SongSeokwoo on 2019-05-13.
 */
public class PetNeuterFragment extends PetRegistFragment {
    private FragmentPetNatuerBinding binding;
    private String selectNatuer = "";
    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_natuer, container, false);
        binding.setChecked(true);
        binding.petNatuerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                switch ( selectedId ) {
                    case R.id.yes :
                        selectNatuer = "YES";
                        break;
                    case R.id.no :
                        selectNatuer = "NO";
                        break;
                }
            }
        });
        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_NEUTER_TYPE, selectNatuer );
                ((PetRegistActivity) getActivity()).nextFragment();
            }
        });
        return binding.getRoot();
    }

    public static PetRegistFragment newInstance() {
        return new PetNeuterFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {
            petBasicInfo = getPetBasicInfo();
            if (petBasicInfo != null) {
                if ( petBasicInfo.getSex().equals("YES") ) {
                    selectNatuer = "YES";
                    binding.yes.setChecked(true);
                } else {
                    selectNatuer = "NO";
                    binding.no.setChecked(true);
                }
            }
        }
    }
}
