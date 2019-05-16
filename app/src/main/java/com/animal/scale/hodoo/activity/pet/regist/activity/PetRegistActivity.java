package com.animal.scale.hodoo.activity.pet.regist.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.fragment.activity.ActivityQuestionFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.BasicInfomationFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.PetBasicInfoBaseFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.section.PetGenderFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.basic.section.PetNeuterFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.birthday.PetBirthdayFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.breed.PetBreedFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.disease.DiseaseInfomationFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.profile.PetProfileFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.type.PetTypeFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.name.PetNameFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.physique.PhysiqueInfomationRegistFragment;
import com.animal.scale.hodoo.activity.pet.regist.fragment.weight.WeightCheckFragment;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityPetRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class PetRegistActivity extends BaseActivity<PetRegistActivity> implements PetRegistIn.View, BaseActivity.OnSubBtnClickListener, PetBasicInfoBaseFragment.OnDataListener {
    public static final int BASIC_TYPE = 0;
    public static final int DISEASE_TYPE = 1;
    public static final int PHYSIQUE_TYPE = 2;
    public static final int WEIGHT_TYPE = 3;
    public static final int PET_USER_SELECT_QUESTION_TYPE = 4;

    public static final int PET_TYPE_TYPE = 0;
    public static final int PET_NAME_TYPE = 1;
    public static final int PET_PROFILE_TYPE = 2;
    public static final int PET_BIRTHDAY_TYPE = 3;
    public static final int PET_BREED_TYPE = 4;
    public static final int PET_GENDER_TYPE = 5;
    public static final int PET_NEUTER_TYPE = 6;



    public static final int PET_TYPE_INFO = 0;
    public static final int PET_BASIC_INFO = 1;


    public static final int PET_PHYSIQUE_INFO = 7;
    public static final int PET_DISEASE_INFO = 8;
    public static final int PET_WEIGHT_INFO = 9;

    private ActivityPetRegistBinding binding;
    private int petIdx;
    private boolean editModeState = false;
    private PetRegistFragment[] fragments = {
            PetTypeFragment.newInstance(),
            PetNameFragment.newInstance(),
            PetProfileFragment.newInstance(),
            PetBirthdayFragment.newInstance(),
            PetBreedFragment.newInstance(),
            PetGenderFragment.newInstance(),
            PetNeuterFragment.newInstance(),


            PhysiqueInfomationRegistFragment.newInstance(),
            DiseaseInfomationFragment.newInstance(),
            WeightCheckFragment.newInstance(),
            ActivityQuestionFragment.newInstance()
    };
    private int fragmentPosition = 0;
    private PetRegistIn.Presenter presenter;
    private String location;
    private int petType;

    /* data (s) */
    private PetBasicInfo petBasicInfo;
    private PetChronicDisease petDiseaseInfo;
    private PetPhysicalInfo petPhysicalInfo;
    private PetWeightInfo petWeightInfo;
    private PetUserSelectionQuestion petUserSelectionQuestion;
    /* data (e) */

    private CircleImageView profile;

    public static final int DOG_TYPE = 1;
    public static final int CAT_TYPE = 2;

    private boolean changeState = false;
    private boolean loginRegistState = false;

    HodooApplication app;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_regist);
        binding.setActivityInfo( new ActivityInfo(getString(R.string.basin_info_regist_title)) );
        app = (HodooApplication) getApplication();

        loginRegistState = getIntent().getBooleanExtra(HodooConstant.LOGIN_PET_REGIST, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.hodoo_pink), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonAction();
            }
        });
        presenter = new PetRegistPresenter(this);
        presenter.loadData(this);
        petIdx = getIntent().getIntExtra("petIdx", 0);
        editModeState = petIdx <= 0 ? false : true;
        location = VIewUtil.getMyLocationCode(this);


        PetRegistFragment firstFragment = fragments[fragmentPosition];

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("petIdx", petIdx);
            fragments[i].setArguments(bundle);

            ft.add(R.id.fragment_container, fragments[i]);
            ft.hide(fragments[i]);
        }
        ft.show(firstFragment).commit();

//        ft.replace(R.id.fragment_container, fragments[fragmentPosition]).commit();
    }

    @Override
    protected BaseActivity<PetRegistActivity> getActivityClass() {
        return this;
    }
    public void nextFragment() {
        fragmentPosition++;
        PetRegistFragment fragment = fragments[fragmentPosition];
        Bundle bundle = new Bundle();
        bundle.putInt("petIdx", petIdx);
//        if ( fragmentPosition == PET_BASIC_INFO )
        fragment.setPetBasicInfo( petBasicInfo );
        if ( fragmentPosition == PET_BREED_TYPE ) {
            ((PetBreedFragment) fragment).setPetType( petBasicInfo.getPetType() );
        }
        else if ( fragmentPosition == PET_WEIGHT_INFO ) {
            ((WeightCheckFragment) fragment).setPetIdx(petBasicInfo.getPetType());
        } else if ( fragmentPosition == PET_PHYSIQUE_INFO ) {
            ((PhysiqueInfomationRegistFragment) fragment).updateView();
            ((PhysiqueInfomationRegistFragment) fragment).setPetType(petBasicInfo.getPetType());
            super.setSubBtn("건너뛰기", this);
        } else if ( fragmentPosition == PET_DISEASE_INFO )  {
            ((DiseaseInfomationFragment) fragment).setPetIdx(petIdx);
        }
        if ( fragmentPosition != PET_PHYSIQUE_INFO )
            super.hideSubBtn();

        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
//            ft.add(R.id.fragment_container, fragments[i]);
            ft.hide(fragments[i]);
        }
        ft.show(fragment).commit();
//        ft.replace(R.id.fragment_container, fragment).commit();

    }

    @Override
    public void setBasicInfo(PetBasicInfo basicInfo) {

    }

    @Override
    public void getAllPetBreed(PetBasicInfo basicInfo, List<PetBreed> breeds) {

    }

    @Override
    public void nextStep(int type) {
        switch (type) {
            case DISEASE_TYPE :
                if ( petDiseaseInfo != null )
                    presenter.deleteDiseaseInformation(petIdx, petDiseaseInfo.getId());
                break;
            case PHYSIQUE_TYPE :
                if ( petPhysicalInfo != null )
                    presenter.deletePhysiqueInformation(petIdx, petPhysicalInfo.getId());
                else {
                    petPhysicalInfo = new PetPhysicalInfo();
                    petPhysicalInfo.setWeight("0");
                    petPhysicalInfo.setHeight("0");
                    petPhysicalInfo.setWidth("0");
                    presenter.registPhysiqueInformation(petIdx, petPhysicalInfo);
                }
                break;
            case WEIGHT_TYPE :
                if ( petWeightInfo != null ) {
                    presenter.deleteWeightInfo(petIdx, petWeightInfo.getId());
                }
                break;
            case PET_USER_SELECT_QUESTION_TYPE :
                petUserSelectionQuestion.setBodyFat(petWeightInfo.getBcs());
                if ( petUserSelectionQuestion != null ) {
                    if (  petUserSelectionQuestion.getQuestionIdx() == 0 ) {
                        presenter.registPetUserSelectQuestion(petIdx, petUserSelectionQuestion);
                    } else {
                        presenter.deletePetUserSelectQuestion(petIdx, petUserSelectionQuestion.getQuestionIdx());
                    }
//                    presenter.registPetType();
                }
                break;
        }
    }

    @Override
    public void setPetIdx(int petIdx) {
        if ( this.petIdx == 0 ) {
            this.petIdx = petIdx;
            nextStep(PetRegistActivity.DISEASE_TYPE);
        }
    }

    @Override
    public void registBasicInfo(int result) {
        String REQUEST_URL = "";
        petBasicInfo.setPetType(petType);
        if ( !editModeState ) {
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/regist.do";
            presenter.registBasicInfo( REQUEST_URL, petBasicInfo, profile );
        } else {
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/update.do";
            presenter.updateBasicInfo( REQUEST_URL, petBasicInfo, profile );
        }


    }

    @Override
    public void registDiseaseInfo() {
        presenter.registDiseaseInformation(petDiseaseInfo, petIdx);
    }

    @Override
    public void registPhysiqueInfo() {
        presenter.registPhysiqueInformation(petIdx, petPhysicalInfo);
    }

    @Override
    public void registWeightInfo() {
        presenter.registWeightInfo(petIdx, petWeightInfo);
    }

    @Override
    public void registPetUserSelectQuestion() {
        presenter.registPetUserSelectQuestion(petIdx, petUserSelectionQuestion);
    }

    @Override
    public void registFinish() {
        binding.setStatus(false);
        AlertDialog builder = new AlertDialog.Builder(this)
                .setMessage(editModeState ? "수정이 완료되었습니다." : "등록이 완료되었습니다")
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ( loginRegistState ) {
                            Intent intent = new Intent(PetRegistActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                        finish();
                    }
                })
                .create();
        builder.setCanceledOnTouchOutside(false);
        builder.show();
    }

    public void setPetBasicInfo ( CircleImageView profile, PetBasicInfo petBasicInfo ) {
        this.profile = profile;
        this.petBasicInfo = petBasicInfo;
    }
    public PetBasicInfo getPetBasicInfo () {
        return petBasicInfo;
    }
    public void setPetDiseaseInfo ( PetChronicDisease petDiseaseInfo ) {
        this.petDiseaseInfo = petDiseaseInfo;
    }

    public void setPetPhysicalInfo ( PetPhysicalInfo petPhysicalInfo ) {
        this.petPhysicalInfo = petPhysicalInfo;
    }
    public void setPetWeightInfo ( PetWeightInfo petWeightInfo ) {
        this.petWeightInfo = petWeightInfo;
    }
    public void setPetUserSelectionQuestion ( PetUserSelectionQuestion petUserSelectionQuestion ) {
        this.petUserSelectionQuestion = petUserSelectionQuestion;
    }
    public void setPetType ( int petType ) {
//        this.petType = petType;
        if ( petBasicInfo == null )
            petBasicInfo = new PetBasicInfo();
        petBasicInfo.setPetType( petType );
    }
    public void regist() {
        binding.setStatus(true);
        String REQUEST_URL = "";
//        if ( !editModeState ) {
//            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/regist.do";
//        } else {
//            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/update.do";
//        }
        petBasicInfo.setSelectedBfi(petWeightInfo.getSelectedBfi());
        if ( !editModeState ) {
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/regist.do";
            presenter.registBasicInfo( REQUEST_URL, petBasicInfo, profile );
        } else {
            REQUEST_URL = SharedPrefVariable.SERVER_ROOT + "/pet/basic/update.do";
            presenter.updateBasicInfo( REQUEST_URL, petBasicInfo, profile );
        }
    }

    @Override
    public void onBackPressed() {
        backButtonAction();
    }
    private void backButtonAction () {
        if ( !editModeState ) {
            if ( fragmentPosition > 0 )
                fragmentPosition--;
        } else {
            if ( fragmentPosition > 0 )
                fragmentPosition--;
        }

        if ( fragmentPosition == PET_PHYSIQUE_INFO ) {
            super.setSubBtn("건너뛰기", this);
        } else if ( fragmentPosition != PET_PHYSIQUE_INFO )
            super.hideSubBtn();




        if ( fragmentPosition == 0 ) {
//            Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//            toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, android.R.color.transparent), PorterDuff.Mode.SRC_ATOP);
//            toolbar.setNavigationOnClickListener(null);
        }

        if ( !editModeState ? fragmentPosition < 1 : fragmentPosition < 1 ) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("현재까지 작성하신 모든 데이터가 저장되지않습니다.\n그래도 취소하시겠습니까?")
                    .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            fragmentPosition = editModeState ? 1 : 1;
                        }
                    }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                            finish();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            fragmentPosition = editModeState ? 1 : 1;
                        }
                    }).create();
            dialog.show();
            return;
        }
        PetRegistFragment fragment = fragments[fragmentPosition];
        Bundle bundle = new Bundle();
        bundle.putInt("petIdx", petIdx);

        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            ft.hide(fragments[i]);
        }
        ft.show(fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setChangeState ( boolean state ) {
            changeState = state;
    }

    @Override
    public void onClick(View v) {
        nextFragment();
    }

    @Override
    public void onDataCallback(int type, Object data) {

        Log.e(TAG, String.format("pet type data : %d", (int) data));
    }
    public void setPetBasicInfoData ( int type, Object data ) {
//        if ( petBasicInfo == null )
//            petBasicInfo = new PetBasicInfo();
        switch ( type ) {
            case PET_TYPE_TYPE :
                petBasicInfo = (PetBasicInfo) data;
//                petBasicInfo.setPetType( (int) data );
                break;
            case PET_NAME_TYPE :
                petBasicInfo.setPetName( (String) data );
                break;
            case PET_PROFILE_TYPE :
                profile = (CircleImageView) data;
                break;
            case PET_BIRTHDAY_TYPE :
                petBasicInfo.setBirthday( (String) data );
                break;
            case PET_BREED_TYPE :
                petBasicInfo.setPetBreed( String.valueOf( (int) data ) );
                break;
            case PET_GENDER_TYPE :
                petBasicInfo.setSex( (String) data );
                break;
            case PET_NEUTER_TYPE :
                petBasicInfo.setNeutralization( (String) data );
                break;
        }
    }
}
