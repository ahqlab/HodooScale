package com.animal.scale.hodoo.activity.pet.regist.fragment.basic;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.BottomDialog;
import com.animal.scale.hodoo.databinding.FragmentBasicInfomationBinding;
import com.animal.scale.hodoo.databinding.LayoutPetBreedBinding;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.util.VIewUtil;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class BasicInfomationFragment extends PetRegistFragment implements BasicInfomationIn.View, CheckBox.OnCheckedChangeListener, PetBasicInfoBaseFragment.OnDataListener {
//    public interface OnSectionListener {
//        void
//    }
    private String TAG = BasicInfomationFragment.class.getSimpleName();

    private FragmentBasicInfomationBinding binding;
    private BasicInfomationPresenter presenter;

    public static String REQUEST_URL = "";
    public static boolean REQUEST_MODE = false;

    private final String GENDER_MALE = "MALE";
    private final String GENDER_FEMALE = "FEMALE";

    private boolean genderCheck = false;

    private List<PetBreed> breeds;

    private DatePickerDialog picker;

    private int breedIndex = -1;

    private BottomDialog dialog;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    private Uri photoUri;
    private String imageFilePath;

    int number;
    boolean changeState = false;
    boolean focusState = false;

    private int petType;
    private int petIdx;
    private String[] values;

    private PetBasicInfoBaseFragment[] fragments = {
//            new PetTypeFragment().newInstance(),
//            new PetNameFragment().newInstance(),
//            new PetProfileFragment().newInstance(),
//            new PetBreedFragment().newInstance(),
//            new PetBirthdayFragment().newInstance(),
//            new PetDiseaseFragment().newInstance(),
//            new PetGenderFragment().newInstance(),
//            new PetNeuterFragment().newInstance()
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_infomation, container, false);
        binding.setActivity(this);
        presenter = new BasicInfomationPresenter(this);
        presenter.loadData(getContext());

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        ft.replace(R.id.basic_info_section_wrap, PetTypeFragment.newInstance());
        for (int i = 0; i < fragments.length; i++) {
            fragments[i].setCallback(this);
//
//            ft.add(R.id.basic_info_section_wrap, fragments[i]);
//            ft.hide(fragments[i]);
        }


        ft.replace(R.id.basic_info_section_wrap, fragments[0]);
//        ft.show(fragments[0]);
        ft.commit();

//        if ( getArguments() != null ) {
//            petIdx = getArguments().getInt("petIdx");
//            presenter.getDiseaseInformation(getArguments().getInt("petIdx"));
//        } else {
//
//        }
//        validation();
//        if ( ((HodooApplication) getActivity().getApplication()).isExperienceState() )
//            setBtnEnable(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public static PetRegistFragment newInstance() {
        return new BasicInfomationFragment();
    }
    public void onClickOpenBottomDlg ( View v ) {
        dialog = BottomDialog.getInstance();
        List<IosStyleBottomAlert> btns = new ArrayList<>();
        btns.add(IosStyleBottomAlert.builder().btnName(getString(R.string.camera)).id(R.id.camera).build());
        btns.add(IosStyleBottomAlert.builder().btnName(getString(R.string.gallery)).id(R.id.gallery).build());
        dialog.setButton(btns);
        dialog.setOnclick(new BottomDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.camera:
                        int permissionCamera = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        } else {
//                            Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                            presenter.openCamera();
                        }
                        break;
                    case R.id.gallery:
                        int permissionStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permissionStorage == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        } else {
                            openGallery();
                            dialog.dismiss();
                        }
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(), TAG);
    }
    public void goDiseaseActivity ( View v ) {
        setBasicInfo();
        PetBasicInfo info = binding.getInfo();
        info.setPetBreed(String.valueOf(breedIndex));

        PetChronicDisease petChronicDisease = binding.getDomain();
        petChronicDisease.setDiseaseName(number);
        ((PetRegistActivity) getActivity()).setPetBasicInfo( binding.profile, info );
        ((PetRegistActivity) getActivity()).setPetDiseaseInfo( petChronicDisease );
        ((PetRegistActivity) getActivity()).nextFragment();
        if ( changeState )
            ((PetRegistActivity) getActivity()).setChangeState( true );
    }

    @Override
    public void setNavigation() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
    }

    @Override
    public void setView(PetBasicInfo basicInfo) {
        if ( basicInfo != null ) {
            binding.petName.editText.setText(basicInfo.getPetName());
            if ( petType == basicInfo.getPetType() )
                binding.petBreed.editText.setText(basicInfo.getPetBreed());
            else
                binding.petBreed.editText.setText("");

            binding.petBirthday.editText.setText(basicInfo.getBirthday());
            if (basicInfo.getNeutralization().matches("YES")) {
                binding.switch1.setChecked(true);
            } else {
                binding.switch1.setChecked(false);
            }
            Picasso.with(getContext())
                    .load(SharedPrefVariable.SERVER_ROOT + basicInfo.getProfileFilePath())
                    .error(R.drawable.icon_pet_profile)
                    .into(binding.profile);

            if (basicInfo.getSex().matches(GENDER_MALE)) {
                binding.maleBtn.setChecked(true);
            } else if (basicInfo.getSex().matches(GENDER_FEMALE)) {
                binding.femaleBtn.setChecked(true);
            }
            validation();
        }
        binding.linearWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(binding.petName.editText.getWindowToken(), 0);
            }
        });
        binding.petName.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                View parent = (View) view.getParent();
                parent.performClick();
                focusState = b;
//                if ( !b ) {
//                    view.setFocusableInTouchMode(false);
//                    view.setFocusable(false);
//                    view.setFocusableInTouchMode(true);
//                    view.setFocusable(true);
//                }
//                Log.e(TAG, String.format("focus : %b", b));
            }
        });
    }
    private void releaseFocus() {
        if ( focusState ) {
            InputMethodManager im = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(binding.petName.editText.getWindowToken(), 0);
        }
    }

    @Override
    public void getAllPetBreed(CommonResponce<List<PetBreed>> breeds) {
        this.breeds = breeds.domain;
        if ( binding.getInfo() != null ) {
            for (int i = 0; i < this.breeds.size(); i++) {
                if ( this.breeds.get(i).getName().equals(binding.getInfo().getPetBreed()) ) {
                    breedIndex = this.breeds.get(i).getId();
                    break;
                }
            }
        }
    }

    private void validation() {
        if (!ValidationUtil.isEmpty(binding.petName.editText.getText().toString()) &&
                !ValidationUtil.isEmpty(binding.petBreed.editText.getText().toString()) &&
                !ValidationUtil.isEmpty(binding.petBirthday.editText.getText().toString()) &&
                genderCheck) {
            setBtnEnable(true);
        } else {
            setBtnEnable(false);
        }
    }
    private void setBtnEnable(boolean state) {
        binding.nextStep.setEnabled(state);
        if (binding.nextStep.isEnabled()) {
            binding.nextStep.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        } else {
            binding.nextStep.setTextColor(ContextCompat.getColor(getContext(), R.color.mainRed));
        }
    }
    public void onClickSelectEditText(View view) {
        if ( breeds == null )
            return;
        values = new String[breeds.size()];
        for (int i = 0; i < breeds.size(); i++)
            values[i] = breeds.get(i).getName();

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
    public void onResume() {
        super.onResume();
//        validation();
//        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    binding.getInfo().setNeutralization("YES");
//                } else {
//                    binding.getInfo().setNeutralization("NO");
//                }
//                releaseFocus();
//            }
//        });
//        binding.radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                Log.e(TAG, "radioGroupSex onCheckedChanged");
//                int rd = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = binding.getRoot().findViewById(rd);
//                if (radioButton.getText().toString().matches(getResources().getString(R.string.femle))) {
//                    binding.getInfo().setSex("FEMALE");
//                } else if (radioButton.getText().toString().matches(getResources().getString(R.string.male))) {
//                    binding.getInfo().setSex("MALE");
//                }
//                genderCheck = true;
//                validation();
//                releaseFocus();
//            }
//        });
//        binding.petBreed.editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickSelectEditText(view);
//                releaseFocus();
//            }
//        });
//        binding.petBirthday.editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickCalDalog(view);
//                releaseFocus();
//            }
//        });
//        binding.petName.editText.addTextChangedListener(new CommonTextWatcher(binding.petName, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
//            @Override
//            public void onChangeState(boolean state) {
//                validation();
//            }
//        }));
//        binding.petBreed.editText.addTextChangedListener(new CommonTextWatcher(binding.petBreed, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_breed_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
//            @Override
//            public void onChangeState(boolean state) {
//                validation();
//            }
//        }));
//        binding.petBirthday.editText.addTextChangedListener(new CommonTextWatcher(binding.petBirthday, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_birthday_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
//            @Override
//            public void onChangeState(boolean state) {
//                validation();
//            }
//        }));
    }

    private void setBasicInfo() {
        binding.getInfo().setPetName(binding.petName.editText.getText().toString());
        binding.getInfo().setPetBreed(binding.petBreed.editText.getText().toString());
        binding.getInfo().setBirthday(binding.petBirthday.editText.getText().toString());
    }
    public void openGallery () {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    @Override
    public void openCamera() {
//        builder.dismiss();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if ( cameraIntent.resolveActivity(getActivity().getPackageManager()) != null ) {
            File photo = presenter.setSaveImageFile(getContext());
            if ( photo != null ) {
                imageFilePath = photo.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), photo);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.rotationImage(imageFilePath);
        } else if ( requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK ) {
            presenter.setGalleryImage(getContext(), data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void setSaveImageFile(Bitmap image) {
        binding.profile.setImageBitmap(image);
    }

    @Override
    public void setBasicInfo(PetBasicInfo basicInfo) {
        if ( basicInfo != null )
            binding.setInfo(basicInfo);
        else {
            binding.setInfo(new PetBasicInfo());
            if(binding.switch1.isChecked()){
                binding.getInfo().setNeutralization("YES");
            }else{
                binding.getInfo().setNeutralization("NO");
            }
        }
        REQUEST_MODE = true;
        REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/update.do";
        presenter.setView(basicInfo);
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
    public void setListviewAdapter(PetChronicDisease petChronicDisease){
        final List<Disease> diseases = new ArrayList<Disease>();

        /* new code 2018.12.26 (s) */
        String[] diseasesStr = getResources().getStringArray(R.array.disease);

        if ( petChronicDisease != null )
            number = petChronicDisease.getDiseaseName();


        for (int i = 0; i < diseasesStr.length; i++) {

            CheckBox radioButton = new CheckBox(getContext());
            if ( (number & (0x01<<i)) != 0 ) {
                radioButton.setChecked(true);
            }

            radioButton.setTag(i);
            radioButton.setText(diseasesStr[i]);
            radioButton.setOnCheckedChangeListener(this);


            binding.diseaseWrap.addView( radioButton );
        }
//

//        for ( String disease : diseasesStr )
//            diseases.add(new Disease(disease));
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
//        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
//        layoutManager.setFlexWrap(FlexWrap.WRAP);
//        layoutManager.setAlignItems(AlignItems.FLEX_START);
//
//        binding.recyclerview.setLayoutManager(layoutManager);
//
//        adapter = new AdapterOfDisease(diseases, petChronicDisease);
//        binding.recyclerview.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        /* new code 2018.12.26 (e) */


//        Adapter = new AdapterOfDiseaseList(DiseaseInformationRegistActivity.this, diseases, petChronicDisease);
//        binding.listview.setAdapter(Adapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int position = (int) compoundButton.getTag();
        ViewGroup parent = (ViewGroup) compoundButton.getParent();

        if ( position != 0 ) {
            if ( ((CheckBox) parent.getChildAt(0)).isChecked() ) {
                ((CheckBox) parent.getChildAt(0)).setChecked(false);
                number = 0;
            }

            if ( b )
                number += (0x01<<position);
            else {
                number -= (0x01<<position);
            }
        } else {
            for (int i = 1; i < parent.getChildCount(); i++) {
                ((CheckBox) parent.getChildAt(i)).setChecked(false);
            }
            number = 1;
        }

        Log.e(TAG, String.format("number : %d", number));

//        if ( position != 0 ) {
//            ViewGroup inLinear = (ViewGroup) parent.getChildAt(0);
//            CheckBox checkBox = (CheckBox) inLinear.getChildAt(0);
//            checkBox.setChecked(false);
//            if ( b )
//                number += (0x01<<position);
//            else {
//                boolean isChecked = false;
//                for (int i = 0; i < parent.getChildCount(); i++) {
//                    if ( parent.getChildAt(i) instanceof LinearLayout) {
//                        inLinear = (ViewGroup) parent.getChildAt(i);
//                        checkBox = (CheckBox) inLinear.getChildAt(0);
//                        if ( checkBox.isChecked() ) {
//                            isChecked = true;
//                            break;
//                        }
//                    }
//                }
//                if ( !isChecked ) {
//                    inLinear = (ViewGroup) parent.getChildAt(0);
//                    checkBox = (CheckBox) inLinear.getChildAt(0);
//                    checkBox.setChecked(true);
//                    return;
//                }
//                number -= (0x01<<position);
//            }
//
//        }
//        if ( position == 0 ) {
//            for (int i = 0; i < parent.getChildCount(); i++) {
//                if ( parent.getChildAt(i) instanceof LinearLayout ) {
//                    ViewGroup inLinear = (ViewGroup) parent.getChildAt(i);
//                    CheckBox checkBox = (CheckBox) inLinear.getChildAt(0);
//                    if ( compoundButton != checkBox ) {
//                        checkBox.setChecked(false);
//                        number = 1;
//                    }
//
//                }
//            }
//            if ( !b )
//                number -= (0x01<<position);
//        }
        compoundButton.setChecked(b);
        releaseFocus();
    }

    @Override
    protected void changeState(boolean state) {
        super.changeState(state);
        ((PetRegistActivity) getActivity()).setChangeState( state );
    }

    public void setPetType ( int petType ) {
        this.petType = petType;

        presenter.getPetBasicInformation(VIewUtil.getMyLocationCode(getContext()), petIdx);
        presenter.getAllPetBreed(VIewUtil.getMyLocationCode(getContext()), petType);
    }

    @Override
    public void onDataCallback(int type, Object data) {
        Log.e(TAG, "callback");
        switch ( type ) {
            case 0 :
                petType = (int) data;
                break;
        }

        PetBasicInfoBaseFragment fragment = fragments[type + 1];
        Bundle bundle = new Bundle();
        bundle.putInt("petType", petType);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.basic_info_section_wrap, fragment);
        ft.commit();
        

        Log.e(TAG, String.format("pet type data : %d", (int) data));
    }
}
