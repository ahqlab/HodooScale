package com.animal.scale.hodoo.activity.pet.regist.second;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivitySecondBasicInformaitonRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.ResultMessage;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.HttpUtill;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicSecondInformationRegistActivity extends BaseActivity<BasicSecondInformationRegistActivity> {

    public DatePickerDialog picker;

    ActivitySecondBasicInformaitonRegistBinding binding;

    private static final int CAMERA_REQUEST = 1888;

    private static final int CAMERA_PERMISSION_CODE = 100;

    ProgressDialog progressDialog;

    public String sex = "";

    BottomDialog builder;

    public static  String REQUEST_URL = "";

    public static boolean REQUEST_MODE = false;

    public int petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_basic_informaiton_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.basin_info_regist_title)));
        super.setToolbarColor();
        Intent intent = getIntent();
        petId = intent.getIntExtra("petId", 0);
        progressDialog = new ProgressDialog(BasicSecondInformationRegistActivity.this);
        setNavi();
        setInitView();
    }

    private void setInitView() {
        Call<PetBasicInfo> result = NetRetrofit.getInstance().getPetBasicInfoService().getBasicInfoForPetId(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID), petId);
        result.enqueue(new Callback<PetBasicInfo>() {
            @Override
            public void onResponse(Call<PetBasicInfo> call, Response<PetBasicInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //UPDATRE
                        REQUEST_URL = "http://121.183.234.14:7171/hodoo/pet/basic/update";
                        binding.setInfo(response.body());
                        REQUEST_MODE = true;
                        //기본정보 셋팅로직 나중에 정리!!
                        if(response.body().getNeutralization().matches("YES")){
                            binding.switch1.setChecked(true);
                        }else{
                            binding.switch1.setChecked(false);
                        }
                        Picasso.with(BasicSecondInformationRegistActivity.this)
                                .load("http://121.183.234.14:7171/hodoo/" + response.body().getProfileFilePath())
                                .into(binding.profile);
                        if(response.body().getSex().matches("MALE")){
                            binding.maleBtn.setPressed(true);
                            binding.femaleBtn.setPressed(false);
                        }else if(response.body().getSex().matches("FEMALE")){
                            binding.maleBtn.setPressed(false);
                            binding.femaleBtn.setPressed(true);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PetBasicInfo> call, Throwable t) {
                binding.setInfo(new PetBasicInfo());
                REQUEST_MODE = false;
                REQUEST_URL = "http://121.183.234.14:7171/hodoo/pet/basic/regist";
            }
        });

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(BasicSecondInformationRegistActivity.this, "YES", Toast.LENGTH_SHORT).show();
                    binding.getInfo().setNeutralization("YES");
                }else{
                    Toast.makeText(BasicSecondInformationRegistActivity.this, "NO", Toast.LENGTH_SHORT).show();
                    binding.getInfo().setNeutralization("NO");
                }
            }
        });
    }

    private void setNavi() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.add_pet_nav_reverse_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiseaseSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightSecondCheckActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
    }

    @Override
    protected BaseActivity<BasicSecondInformationRegistActivity> getActivityClass() {
        return BasicSecondInformationRegistActivity.this;
    }

    public void onClickMaleBtn(View view) {
        Toast.makeText(BasicSecondInformationRegistActivity.this, "MALE", Toast.LENGTH_SHORT).show();
        binding.maleBtn.setBackgroundResource(R.drawable.add_pet_middle_click_btn_blue_252_68);
        binding.femaleBtn.setBackgroundResource(R.drawable.add_pet_middle_252_68);
        binding.femaleBtn.setPressed(false);
        binding.getInfo().setSex("MALE");
    }

    public void onClickFemaleBtn(View view) {
        Toast.makeText(BasicSecondInformationRegistActivity.this, "FEMALE", Toast.LENGTH_SHORT).show();
        binding.maleBtn.setBackgroundResource(R.drawable.add_pet_middle_btn_252_68);
        binding.femaleBtn.setBackgroundResource(R.drawable.add_pet_middle_click_btn_pink_252_68);
        binding.getInfo().setSex("FEMALE");
    }

    public void onClickOpenBottomDlg(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.image_select_bottom_custom_view, null);
        builder =  new BottomDialog.Builder(this)
                .setCustomView(customView)
                .setNegativeText("Close")
                .show();

        Button camera = (Button) customView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(BasicSecondInformationRegistActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                    openCamera();
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

    private void openCamera() {
        builder.dismiss();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void onClickCalDalog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(BasicSecondInformationRegistActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.getDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
                            openCamera(); // start WIFIScan
                        } else {
                            Toast.makeText(getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
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
        new RegistAsyncTask().execute();
    }

    public class RegistAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            publishProgress("Saving...");
            if(REQUEST_MODE){
                //UPDATE
                Log.e("HJLEE", "UPDATE");
                return HttpUtill.HttpFileUpdate(REQUEST_URL , binding.getInfo(), binding.profile);
            }else{
                //INSERT
                Log.e("HJLEE", "INSERT");
                return HttpUtill.HttpFileRegist(REQUEST_URL , mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID), binding.getInfo(), binding.profile);
            }
        }
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            progressDialog.setMessage(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            ResultMessageGroup resultMessageGroup = gson.fromJson(result, ResultMessageGroup.class);
            Integer petId = gson.fromJson(String.valueOf(resultMessageGroup.getDomain()), Integer.class);
            if(resultMessageGroup.getResultMessage().toString().matches(String.valueOf(ResultMessage.SUCCESS))){
                //페이지 이동
                Intent intent = new Intent(getApplicationContext(), DiseaseSecondInformationRegistActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }else{
                Toast.makeText(BasicSecondInformationRegistActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
