package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.databinding.FragmentPetTypeBinding;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.util.VIewUtil;

/**
 * Created by SongSeokwoo on 2019-04-04.
 */
public class PetTypeFragment extends PetRegistFragment implements View.OnClickListener, PetTypeIn.View {
    private String TAG = PetTypeFragment.class.getSimpleName();
    private FragmentPetTypeBinding binding;
    private PetTypePresenter presenter;
    private int petType = -1;
    private int dbPetType = -1;
    private PetBasicInfo basicInfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_type, container, false);
//        callback = getCallback();
//        binding.setFragment(this);
        presenter = new PetTypePresenter(this);

        presenter.loadData(getContext());
//        presenter.setNavigation();
//
        if ( getArguments() != null ) {
            if ( getArguments().getInt("petIdx") > 0 )
                presenter.getPetBasicInformation( VIewUtil.getMyLocationCode(getContext()), getArguments().getInt("petIdx") );
        }
        if ( ((HodooApplication) getActivity().getApplication()).isExperienceState() ) {
            binding.setChecked(true);
            binding.setExperienceMode(true);
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
//                if ( dbPetType > 0 && dbPetType != petType )
//                    changeState(true);
//                else if ( dbPetType > 0 && dbPetType == petType )
//                    changeState(false);
                binding.setChecked(true);
            }
        });
        binding.doneBtn.setOnClickListener(this);
        return binding.getRoot();
    }
    public static PetRegistFragment newInstance() {
        return new PetTypeFragment();
    }
//    public static PetRegistFragment newInstance() {
//        return new PetTypeFragment();
//    }


//    @Override
//    protected void changeState( boolean state ) {
//        super.changeState(state);
//        if ( state )
//            Log.e(TAG, "changeState" );
//        else
//            Log.e(TAG, "recovery" );
//    }

    @Override
    public void onClick(View view) {
        if ( basicInfo == null )
            basicInfo = new PetBasicInfo();
        basicInfo.setPetType( petType );
        ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_TYPE_TYPE, basicInfo );
        ((PetRegistActivity) getActivity()).nextFragment();
//        if ( callback != null )
//            callback.onDataCallback( 0, petType );
    }

    @Override
    public void setType(int petType) {

    }

    @Override
    public void setNavigation() {

    }

    @Override
    public void setBasicInfo(PetBasicInfo basicInfo) {
        if ( basicInfo != null ) {
            this.basicInfo = basicInfo;
            petType = basicInfo.getPetType();
            if ( petType == PetRegistActivity.DOG_TYPE )
                binding.dogType.setChecked(true);
            else
                binding.catType.setChecked(true);

        }
    }
}
