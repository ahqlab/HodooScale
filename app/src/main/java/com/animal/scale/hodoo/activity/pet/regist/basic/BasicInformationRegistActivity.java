package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
}
