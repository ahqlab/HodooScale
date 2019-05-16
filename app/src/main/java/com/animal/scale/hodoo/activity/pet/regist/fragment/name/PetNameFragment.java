package com.animal.scale.hodoo.activity.pet.regist.fragment.name;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.LayoutPetNameBinding;
import com.animal.scale.hodoo.domain.PetBasicInfo;

/**
 * Created by SongSeokwoo on 2019-05-13.
 */
public class PetNameFragment extends PetRegistFragment implements View.OnClickListener {
    private LayoutPetNameBinding binding;
    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_pet_name, container, false);
//        binding.setChecked(true);
        binding.doneBtn.setOnClickListener(this);
        binding.petName.editText.addTextChangedListener(new CommonTextWatcher(binding.petName, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                binding.setChecked( validation( binding.petName ) );
            }
        }));
        binding.petName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent();
                parent.performClick();
            }
        });
        binding.innerWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(binding.petName.editText.getWindowToken(), 0);
            }
        });

        return binding.getRoot();
    }

    public static PetRegistFragment newInstance() {
        return new PetNameFragment();
    }

    @Override
    public void onClick(View view) {
        ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_NAME_TYPE, binding.petName.editText.getText().toString() );
        ((PetRegistActivity) getActivity()).nextFragment();
//        if ( callback != null )
//            callback.onDataCallback( 1, 0 );
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {
            petBasicInfo = getPetBasicInfo();
            if ( petBasicInfo != null )
                binding.petName.editText.setText(petBasicInfo.getPetName());
        }
    }
}
