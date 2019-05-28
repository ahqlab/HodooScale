package com.animal.scale.hodoo.activity.pet.regist.fragment.breed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.FragmentPetBreedBinding;
import com.animal.scale.hodoo.databinding.LayoutPetBreedBinding;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-05-13.
 */
public class PetBreedFragment extends PetRegistFragment implements PetBreedIn.View {

    private FragmentPetBreedBinding binding;
//    private OnDataListener callback;
    private PetBreedIn.Presenter presenter;

    private List<PetBreed> breeds;

    private int petType = 0;
    private int breedIndex = 0;

    private String[] values;

    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_breed, container, false);
//        binding.setChecked(true);
        presenter = new PetBreedPresenter(this);
        presenter.loadData(getContext());
//        callback = getCallback();
//        if ( getArguments() != null ) {
//            petType = getArguments().getInt("petType");
//        }
//        if ( petType >= 0 )
//            petType = 1;

        binding.petBreed.editText.addTextChangedListener(new CommonTextWatcher(binding.petBreed, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                binding.setChecked( validation( binding.petBreed ) );
            }
        }));
        binding.petBreed.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSelectEditText(view);
            }
        });
        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_BREED_TYPE, breedIndex );
                ((PetRegistActivity) getActivity()).nextFragment();
            }
        });
        return binding.getRoot();
    }
    public static PetRegistFragment newInstance() {
        return new PetBreedFragment();
    }

    /**
     * 펫의 품종의 에디트 텍스트를 터치했을 경우 이벤트 처리
     *
     * @param
     * @return
    */
    public void onClickSelectEditText(View view) {
        if ( breeds == null )
            return;
        values = new String[breeds.size()];
        for (int i = 0; i < breeds.size(); i++)
            values[i] = breeds.get(i).getName() != null ? breeds.get(i).getName() : "";

        final LayoutPetBreedBinding petBreedBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_pet_breed, null, false);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, values);

        petBreedBinding.breedEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputText = charSequence.toString();
                ArrayList<String> temp = new ArrayList<>();


                int count = 0;
                for (int j = 0; j < breeds.size(); j++) {
                    if ( breeds.get(j).getName().contains(charSequence) ) {
                        temp.add(breeds.get(j).getName());
                    }
                }
                values = new String[ temp.size() ];
                for (int j = 0; j < temp.size(); j++) {
                    values[j] = temp.get(j);
                }
                if ( temp.size() > 0 ) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, values);
                    petBreedBinding.breedList.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        petBreedBinding.breedList.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.basic_infomation_regist__pet_breed_title)
                .setView(petBreedBinding.getRoot());
        final AlertDialog dialog = builder.create();
        dialog.show();
        petBreedBinding.breedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = values[i];
                for (int j = 0; j < breeds.size(); j++) {
                    if ( name.equals(breeds.get(j).getName()) ) {
                        binding.petBreed.editText.setText(breeds.get(j).getName());
                        breedIndex = breeds.get(j).getId();
                        break;
                    }
                }
                dialog.dismiss();
            }
        });

//        super.showBasicOneBtnPopup(getResources().getString(R.string.basic_infomation_regist__pet_breed_title), null)
//                .setItems(values, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        binding.petBreed.editText.setText(breeds.get(which).getName());
//                        breedIndex = breeds.get(which).getId();
//                        dialog.dismiss();
//                    }
//                }).show();
    }

    /**
     * 데이터 베이스에 있는 품종의 값을 가져온다
     *
     * @param breeds   품종이 들어있는 리스트 값
     * @return
     * @description     breeds를 먼저 로드 후 등록된 값이 있으면 그 품종의 값을 에디트 텍스트에 넣는다.
    */
    @Override
    public void getAllPetBreed(CommonResponce<List<PetBreed>> breeds) {
        this.breeds = breeds.domain;
        if ( petBasicInfo.getPetBreed() == null )
            return;
        for (int i = 0; i < this.breeds.size(); i++) {
            if ( this.breeds.get(i).getName().equals(petBasicInfo.getPetBreed()) ) {
                breedIndex = this.breeds.get(i).getId();
                binding.petBreed.editText.setText( this.breeds.get(i).getName() );
                break;
            }
        }

    }
    /**
     * 펫의 종류에 따라 품종 리스트를 달리 가져와서 펫의 타입을 먼저 등록한다.
     *
     * @param petType   펫의 타입
     * @return
     * @description
    */
    public void setPetType ( int petType ) {
        petBasicInfo = getPetBasicInfo();
//        this.petType = petType;

        presenter.getAllPetBreed(VIewUtil.getMyLocationCode(getContext()), petBasicInfo.getPetType());
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {

//                binding.petBirthday.editText.setText(petBasicInfo.getBirthday());
        }
    }
}
