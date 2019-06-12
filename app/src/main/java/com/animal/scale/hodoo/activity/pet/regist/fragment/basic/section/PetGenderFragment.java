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
import com.animal.scale.hodoo.databinding.FragmentPetGenderBinding;
import com.animal.scale.hodoo.domain.PetBasicInfo;

/**
 * Created by SongSeokwoo on 2019-05-13.
 * 성별 입력 Fragment
 */
public class PetGenderFragment extends PetRegistFragment {
    private FragmentPetGenderBinding binding;

    private final String GENDER_MALE = "MALE";
    private final String GENDER_FEMALE = "FEMALE";

    private String selectGender = GENDER_MALE;
    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_gender, container, false);
        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_GENDER_TYPE, selectGender );
                ((PetRegistActivity) getActivity()).nextFragment();
            }
        });
        binding.genderTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkId = radioGroup.getCheckedRadioButtonId();
                switch ( checkId ) {
                    case R.id.male_type :
                        selectGender = GENDER_MALE;
                        break;
                    case R.id.female_type :
                        selectGender = GENDER_FEMALE;
                        break;
                }
                binding.setChecked(true);
            }
        });
        return binding.getRoot();
    }

    public static PetRegistFragment newInstance() {
        return new PetGenderFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {
            petBasicInfo = getPetBasicInfo();
            if ( petBasicInfo != null && petBasicInfo.getSex() != null ) {
                if ( petBasicInfo.getSex().equals( GENDER_MALE ) ) {
                    selectGender = GENDER_MALE;
                    binding.maleType.setChecked(true);
                } else {
                    selectGender = GENDER_FEMALE;
                    binding.femaleType.setChecked(true);
                }
            }
//                binding.petName.editText.setText(petBasicInfo.getPetName());
        }
    }
}
