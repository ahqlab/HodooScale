package com.animal.scale.hodoo.activity.pet.regist.fragment.birthday;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.FragmentPetBirthdayBinding;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.Calendar;

/**
 * Created by SongSeokwoo on 2019-05-13.
 */
public class PetBirthdayFragment extends PetRegistFragment  {
    private FragmentPetBirthdayBinding binding;

    private DatePickerDialog picker;
    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_birthday, container, false);
        binding.petBirthday.editText.addTextChangedListener(new CommonTextWatcher(binding.petBirthday, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_birthday_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                binding.setChecked( validation( binding.petBirthday ) );
            }
        }));
        binding.petBirthday.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCalDalog(view);
            }
        });
        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetRegistActivity) getActivity()).nextFragment();
                ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_BIRTHDAY_TYPE, binding.petBirthday.editText.getText().toString() );
            }
        });

        return binding.getRoot();
    }
    public static PetRegistFragment newInstance() {
        return new PetBirthdayFragment();
    }
    public void onClickCalDalog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.petBirthday.editText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        binding.getDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {
            petBasicInfo = getPetBasicInfo();
            if ( petBasicInfo != null )
                binding.petBirthday.editText.setText(petBasicInfo.getBirthday());
        }
    }
}
