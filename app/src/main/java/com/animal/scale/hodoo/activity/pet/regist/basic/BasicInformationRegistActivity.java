package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.BottomDialog;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivityBasicInformaitonRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BasicInformationRegistActivity extends BaseActivity<BasicInformationRegistActivity> implements BasicInformationRegistIn.View {

    public static Context mContext;

    ActivityBasicInformaitonRegistBinding binding;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    ProgressDialog progressDialog;

    public static String REQUEST_URL = "";

    public static boolean REQUEST_MODE = false;

    private DatePickerDialog picker;

    BasicInformationRegistIn.Presenter presenter;

    private int petIdx;

    private final String GENDER_MALE = "MALE";
    private final String GENDER_FEMALE = "FEMALE";
    private boolean genderCheck = false;

    private Uri photoUri;
    private String imageFilePath;

    private BottomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_informaiton_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.basin_info_regist_title)));
        mContext = this;
        progressDialog = new ProgressDialog(BasicInformationRegistActivity.this);
        super.setToolbarColor();
        presenter = new BasicInformationRegistPresenter(this);
        presenter.loadData(BasicInformationRegistActivity.this);
        presenter.setNavigation();
        Intent intent = getIntent();
        petIdx = intent.getIntExtra("petIdx", 0);
        presenter.getPetBasicInformation(petIdx);

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(BasicInformationRegistActivity.this, "YES", Toast.LENGTH_SHORT).show();
                    binding.getInfo().setNeutralization("YES");
                } else {
                    Toast.makeText(BasicInformationRegistActivity.this, "NO", Toast.LENGTH_SHORT).show();
                    binding.getInfo().setNeutralization("NO");
                }
            }
        });
        binding.radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rd = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(rd);
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
        validation();
//        binding.nextStep.setEnabled(validation());
        binding.petName.editText.addTextChangedListener(new CommonTextWatcher(binding.petName, this, CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                validation();
            }
        }));
        binding.petBreed.editText.addTextChangedListener(new CommonTextWatcher(binding.petBreed, this, CommonTextWatcher.EMPTY_TYPE, R.string.pet_name_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                validation();
            }
        }));
        binding.petBirthday.editText.addTextChangedListener(new CommonTextWatcher(binding.petBirthday, this, CommonTextWatcher.EMPTY_TYPE, R.string.pet_birthday_empty_msg, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                validation();
            }
        }));
    }

    @Override
    public void setBasicInfo(PetBasicInfo basicInfo) {
        if (basicInfo != null) {
            binding.setInfo(basicInfo);
            REQUEST_MODE = true;
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/update";
            presenter.setView(basicInfo);
        } else {
            binding.setInfo(new PetBasicInfo());
            if(binding.switch1.isChecked()){
                binding.getInfo().setNeutralization("YES");
            }else{
                binding.getInfo().setNeutralization("NO");
            }
            REQUEST_MODE = false;
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/regist";
        }
    }

    @Override
    public void showErrorToast() {
        showToast("ERROR");
    }

    @Override
    public void setView(PetBasicInfo basicInfo) {

        binding.petName.editText.setText(basicInfo.getPetName());
        binding.petBreed.editText.setText(basicInfo.getPetBreed());

        binding.petBirthday.editText.setText(basicInfo.getBirthday());
        if (basicInfo.getNeutralization().matches("YES")) {
            binding.switch1.setChecked(true);
        } else {
            binding.switch1.setChecked(false);
        }
        Picasso.with(BasicInformationRegistActivity.this)
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

    @Override
    public void goNextPage(Pet pet) {
        Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
        intent.putExtra("petIdx", pet.getPetIdx());
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void successUpdate() {
        Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//        finish();
    }

    @Override
    public void setSaveImageFile(Bitmap image) {
        binding.profile.setImageBitmap(image);
    }

    @Override
    protected BaseActivity<BasicInformationRegistActivity> getActivityClass() {
        return BasicInformationRegistActivity.this;
    }

    public void onClickMaleBtn(View view) {
       /* Toast.makeText(BasicInformationRegistActivity.this, "MALE", Toast.LENGTH_SHORT).show();
        binding.maleBtn.setBackgroundResource(R.drawable.add_pet_middle_click_btn_blue_252_68);
        binding.femaleBtn.setBackgroundResource(R.drawable.add_pet_middle_252_68);
        binding.femaleBtn.setPressed(false);*/
        binding.getInfo().setSex("MALE");
    }

    public void onClickFemaleBtn(View view) {
       /* Toast.makeText(BasicInformationRegistActivity.this, "FEMALE", Toast.LENGTH_SHORT).show();
        binding.maleBtn.setBackgroundResource(R.drawable.add_pet_middle_btn_252_68);
        binding.femaleBtn.setBackgroundResource(R.drawable.add_pet_middle_click_btn_pink_252_68);*/
        binding.getInfo().setSex("FEMALE");
    }

    public void onClickOpenBottomDlg(View view) {
        dialog = BottomDialog.getInstance();
        List<IosStyleBottomAlert> btns = new ArrayList<>();
        btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.camera)).id(R.id.camera).build() );
        btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.gallery)).id(R.id.gallery).build() );
        dialog.setButton(btns);
        dialog.setOnclick(new BottomDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.camera :
                        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(BasicInformationRegistActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        } else {
                            Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                            presenter.openCamera();
                        }
                        break;
                    case R.id.gallery :
                        int permissionStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permissionStorage == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(BasicInformationRegistActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        } else {
                            openGallery();
                            dialog.dismiss();
                        }
                        break;
                }
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);

//        Button camera = (Button) dialog.findViewById(R.id.camera);
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
//                if (permissionCamera == PackageManager.PERMISSION_DENIED) {
//                    ActivityCompat.requestPermissions(BasicInformationRegistActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
//                } else {
//                    Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
//                    presenter.openCamera();
//                }
//            }
//        });
//
//        Button gallery = (Button) dialog.findViewById(R.id.gallery);
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int permissionStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//                if (permissionStorage == PackageManager.PERMISSION_DENIED) {
//                    ActivityCompat.requestPermissions(BasicInformationRegistActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//                } else {
//                    openGallery();
//                    dialog.dismiss();
//                }
//            }
//        });
    }

    @Override
    public void openCamera() {
//        builder.dismiss();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if ( cameraIntent.resolveActivity(getPackageManager()) != null ) {
            File photo = presenter.setSaveImageFile(this);
            if ( photo != null ) {
                imageFilePath = photo.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photo);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        }
    }
    public void openGallery () {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public void setProgress(Boolean play) {
        if (play) {
            binding.loginProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loginProgress.setVisibility(View.GONE);
            //goNextPage();
        }
    }

    public void onClickCalDalog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(BasicInformationRegistActivity.this,
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            presenter.openCamera(); // start WIFIScan
                        } else {
                            Toast.makeText(getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case STORAGE_PERMISSION_CODE :
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            openGallery();
                        } else {
//                            Toast.makeText(getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.rotationImage(imageFilePath);
        } else if ( requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK ) {
            presenter.setGalleryImage(this, data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void goDiseaseActivity(View view) {
        setBasicInfo();
        if (REQUEST_MODE) {
            presenter.updateBasicInfo(REQUEST_URL, binding.getInfo(), binding.profile);
        } else {
            presenter.registBasicInfo(REQUEST_URL, binding.getInfo(), binding.profile);
        }
    }

    private void setBasicInfo() {
        binding.getInfo().setPetName(binding.petName.editText.getText().toString());
        binding.getInfo().setPetBreed(binding.petBreed.editText.getText().toString());
        binding.getInfo().setBirthday(binding.petBirthday.editText.getText().toString());
    }

    @Override
    public void setNavigation() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        /*binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });*/
    }

    public void onClickSelectEditText(View view) {
        final String[] values = new String[]{
                "마스티프",
                "보르도 마스티프",
                "로트바일러",
                "복서",
                "자이언트 슈나우저",
                "스탠더드 슈나우저",
                "도베르만 핀셔",
                "그레이트 데인",
                "불 마스티프",
                "저먼 셰퍼드",
                "코몬도르",
                "그레이트 피레니즈",
                "콜리",
                "캉갈",
                "올드 잉글리시 시프도그",
                "셔틀랜드 시프도그",
                "비어디드 콜리",
                "웰시 코기",
                "오스트레일리안 켈피",
                "벨지안 시프도그",
                "피레니안 마스티프",
                "보스롱",
                "티베탄 마스티프",
                "사모예드",
                "시베리안 허스키",
                "알래스칸 맬러뮤트",
                "아메리칸 에스키모 도그",
                "캐나디안 에스키모 도그",
                "아프간 하운드",
                "그레이하운드",
                "아이리시 울프하운드",
                "휘핏",
                "보르조이",
                "살루키",
                "아자와크",
                "닥스훈트",
                "미니어처 닥스훈트",
                "쿤 하운드",
                "오터 하운드",
                "노르위전 엘크하운드",
                "잉글리시 폭스하운드",
                "바셋하운드",
                "아메리칸 폭스하운드",
                "비글",
                "던커",
                "해리어",
                "슈바이처 라우프훈트",
                "스위스 하운드",
                "아르투아 하운드",
                "오가르 폴스키",
                "포르셀렌",
                "바센지",
                "로디지안 리지백",
                "치르네코 델레트나",
                "이비전 하운드",
                "파라오 하운드",
                "포덴코 카나리오",
                "저먼 와이어 헤어드 포인터",
                "바이마라너",
                "비즐라",
                "저먼 쇼트 헤어드 포인터",
                "잉글리시 포인터",
                "골든 리트리버",
                "래브라도 리트리버",
                "체서피크 베이 리트리버",
                "컬리 코티드 리트리버",
                "플랫 코티드 리트리버",
                "아이리시 세터",
                "고든 세터",
                "잉글리시 세터",
                "서섹스 스패니얼",
                "필드 스패니얼",
                "아이리시 워터 스패니얼",
                "클럼버 스패니얼",
                "아메리칸 코커 스패니얼",
                "아메리칸 워터 스패니얼",
                "웰시 스프링어 스패니얼",
                "잉글리시 스프링어 스패니얼",
                "잉글리시 코커 스패니얼",
                "폭스 테리어",
                "케리 블루 테리어",
                "맨체스터 테리어",
                "베들링턴 테리어",
                "스카이 테리어",
                "실리햄 테리어",
                "케언 테리어",
                "불 테리어",
                "웰시 테리어",
                "아이리시 테리어",
                "웨스트 하일랜드 화이트 테리어",
                "스태퍼드셔 불 테리어",
                "소프트 코티드 휘튼 테리어",
                "댄디 딘몬트 테리어",
                "미니어처 슈나우저",
                "스코티시 테리어",
                "오스트레일리안 테리어",
                "노리치 테리어",
                "에어데일 테리어",
                "잭 러셀 테리어",
                "핏불 테리어",
                "샤페이",
                "티베탄 테리어",
                "티베탄 스패니얼",
                "라사 압소",
                "프렌치 불도그",
                "보스턴 테리어",
                "스키퍼키",
                "키스혼드",
                "불도그",
                "비숑 프리제",
                "일본 스피츠",
                "차우차우",
                "달마티안",
                "스탠더드 푸들",
                "미니어처 푸들",
                "시바견",
                "아키타견",
                "삽살개",
                "제주개",
                "진돗개",
                "풍산개",
                "친",
                "말티즈",
                "일본 테리어",
                "잉글리시 토이 스패니얼",
                "파피용",
                "차이니스 크레스티드",
                "토이 맨체스터 테리어",
                "이탈리안 그레이하운드",
                "퍼그",
                "미니어처 핀셔",
                "페키니즈",
                "포메라니안",
                "아펜핀셔",
                "토이 푸들",
                "시추",
                "치와와",
                "요크셔 테리어",
                "실키 테리어",
                "브뤼셀 그리폰",
                "독일 스피츠",
                "미니어처 핀셔",
                "카발리에 킹 찰스 스패니얼"

        };
        super.showBasicOneBtnPopup(getResources().getString(R.string.choice_country), null)
                .setItems(values, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.petBreed.editText.setText(values[which]);
                        dialog.dismiss();
                    }
                }).show();
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
            binding.nextStep.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.nextStep.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }
}
