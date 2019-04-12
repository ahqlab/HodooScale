package com.animal.scale.hodoo.activity.pet.regist.fragment.basic;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.adapter.AdapterOfDisease;
import com.animal.scale.hodoo.base.BaseFragment;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.custom.view.BottomDialog;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.FragmentBasicInfomationBinding;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.util.VIewUtil;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class BasicInfomationFragment extends BaseFragment implements BasicInfomationIn.View, CheckBox.OnCheckedChangeListener {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_infomation, container, false);
        binding.setActivity(this);
        presenter = new BasicInfomationPresenter(this);
        presenter.loadData(getContext());


        if ( getArguments() != null ) {
            presenter.getDiseaseInformation(getArguments().getInt("petIdx"));
            presenter.getPetBasicInformation(VIewUtil.getMyLocationCode(getContext()), getArguments().getInt("petIdx"));
        } else {

        }
        validation();
        return binding.getRoot();
    }
    public static BaseFragment newInstance() {
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
            binding.petBreed.editText.setText(basicInfo.getPetBreed());

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
    }

    @Override
    public void getAllPetBreed(List<PetBreed> breeds) {
        this.breeds = breeds;
        if ( binding.getInfo() != null ) {
            for (int i = 0; i < breeds.size(); i++) {
                if ( breeds.get(i).getName().equals(binding.getInfo().getPetBreed()) ) {
                    breedIndex = breeds.get(i).getId();
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
        final String[] values = new String[breeds.size()];
        for (int i = 0; i < breeds.size(); i++)
            values[i] = breeds.get(i).getName();

        super.showBasicOneBtnPopup(getResources().getString(R.string.basic_infomation_regist__pet_breed_title), null)
                .setItems(values, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.petBreed.editText.setText(breeds.get(which).getName());
                        breedIndex = breeds.get(which).getId();
                        dialog.dismiss();
                    }
                }).show();
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
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.getInfo().setNeutralization("YES");
                } else {
                    binding.getInfo().setNeutralization("NO");
                }
            }
        });
        binding.radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.e(TAG, "radioGroupSex onCheckedChanged");
                int rd = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = binding.getRoot().findViewById(rd);
                if (radioButton.getText().toString().matches(getResources().getString(R.string.femle))) {
                    binding.getInfo().setSex("FEMALE");
                } else if (radioButton.getText().toString().matches(getResources().getString(R.string.male))) {
                    binding.getInfo().setSex("MALE");
                }
                genderCheck = true;
                validation();
            }
        });
        binding.petBreed.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSelectEditText(view);
            }
        });
        binding.petBirthday.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCalDalog(view);
            }
        });

        binding.petBreed.editText.addTextChangedListener(new CommonTextWatcher(binding.petBreed, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                validation();
            }
        }));
        binding.petBirthday.editText.addTextChangedListener(new CommonTextWatcher(binding.petBirthday, getContext(), CommonTextWatcher.EMPTY_TYPE, R.string.pet_birthday_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                validation();
            }
        }));
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
        presenter.getAllPetBreed(VIewUtil.getMyLocationCode(getContext()));
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
    }
}
