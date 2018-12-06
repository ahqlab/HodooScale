package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.weight.WeightCheckActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityBasicInformaitonRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class BasicInformationRegistActivity extends BaseActivity<BasicInformationRegistActivity> implements BasicInformationRegistIn.View {

    ActivityBasicInformaitonRegistBinding binding;

    private static final int CAMERA_REQUEST = 1888;

    public static final int CAMERA_PERMISSION_CODE = 100;

    ProgressDialog progressDialog;

    BottomDialog builder;

    public static String REQUEST_URL = "";

    public static boolean REQUEST_MODE = false;

    private DatePickerDialog picker;

    BasicInformationRegistIn.Presenter presenter;

    private int petIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_informaiton_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.basin_info_regist_title)));
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
                if (radioButton.getText().toString().matches("암컷")) {
                    binding.getInfo().setSex("FEMALE");
                } else if (radioButton.getText().toString().matches("수컷")) {
                    binding.getInfo().setSex("MALE");
                }
            }
        });
    }

    @Override
    public void setBasicInfo(PetBasicInfo basicInfo) {
        if (basicInfo != null) {
            binding.setInfo(basicInfo);
            REQUEST_MODE = true;
            REQUEST_URL = "http://121.183.234.14:7171/hodoo/pet/basic/update";
            presenter.setView(basicInfo);
        } else {
            binding.setInfo(new PetBasicInfo());
            REQUEST_MODE = false;
            REQUEST_URL = "http://121.183.234.14:7171/hodoo/pet/basic/regist";
        }
    }

    @Override
    public void showErrorToast() {
        showToast("ERROR");
    }

    @Override
    public void setView(PetBasicInfo basicInfo) {
        if (basicInfo.getNeutralization().matches("YES")) {
            binding.switch1.setChecked(true);
        } else {
            binding.switch1.setChecked(false);
        }
        Picasso.with(BasicInformationRegistActivity.this)
                .load("http://121.183.234.14:7171/hodoo/" + basicInfo.getProfileFilePath())
                .into(binding.profile);
        if (basicInfo.getSex().matches("MALE")) {
            binding.maleBtn.setChecked(true);
        } else if (basicInfo.getSex().matches("FEMALE")) {
            binding.femaleBtn.setChecked(true);
        }
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
        finish();
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
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.image_select_bottom_custom_view, null);
        builder = new BottomDialog.Builder(BasicInformationRegistActivity.this)
                .setCustomView(customView)
                .setNegativeText("Close")
                .show();
        Button camera = (Button) customView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(BasicInformationRegistActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                    presenter.openCamera();
                }
            }
        });

        Button gallery = (Button) customView.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "gallery", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void openCamera() {
        builder.dismiss();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
                        binding.getDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
        }
    }

    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.profile.setImageBitmap(photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void goDiseaseActivity(View view) {
        if (REQUEST_MODE) {
            presenter.updateBasicInfo(REQUEST_URL, binding.getInfo(), binding.profile);
        } else {
            presenter.registBasicInfo(REQUEST_URL, binding.getInfo(), binding.profile);
        }
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

    public void onClickSelectEditText(View view){
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
        AlertDialog.Builder builder = super.showBasicOneBtnPopup(getResources().getString(R.string.choice_country), null);
        builder.setTitle(getResources().getString(R.string.pet_breed));
        // add a radio button list
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.petBreed.setText(values[which]);
                dialog.dismiss();
            }
        });
    }
}
