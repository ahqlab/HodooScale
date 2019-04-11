package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.BaseFragment;
import com.animal.scale.hodoo.databinding.FragmentPetTypeBinding;

/**
 * Created by SongSeokwoo on 2019-04-04.
 */
public class PetTypeFragment extends BaseFragment<PetTypeFragment> implements PetTypeIn.View {
    private String TAG = PetTypeFragment.class.getSimpleName();
    private FragmentPetTypeBinding binding;
    private PetTypePresenter presenter;
    private int petType = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_type, container, false);
        binding.setFragment(this);
        presenter = new PetTypePresenter(this);
        presenter.loadData(getContext());
        presenter.setNavigation();

        if ( getArguments() != null ) {
            if ( getArguments().getInt("petIdx") > 0 )
                presenter.getType( getArguments().getInt("petIdx") );
        } else {

        }
        binding.petTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch ( checkedId ) {
                    case R.id.cat_type :
                        petType = PetRegistActivity.CAT_TYPE;
                        break;
                    case R.id.dog_type :
                        petType = PetRegistActivity.DOG_TYPE;
                        break;
                }
                binding.setChecked(true);
            }
        });
        return binding.getRoot();
    }
    public static BaseFragment newInstance() {
        return new PetTypeFragment();
    }
    public void nextStep ( View v ) {
        ((PetRegistActivity) getActivity()).setPetType(petType);
        ((PetRegistActivity) getActivity()).nextFragment();
    }

    @Override
    public void setType(int petType) {
        switch ( petType ) {
            case PetRegistActivity.CAT_TYPE :
                binding.petTypeRadioGroup.check(R.id.cat_type);
                break;
            case PetRegistActivity.DOG_TYPE :
                binding.petTypeRadioGroup.check(R.id.dog_type);
                break;
        }

    }

    @Override
    public void setNavigation() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
    }
}
