package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.setting.list.SettingListActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.custom.view.BottomDialog;
import com.animal.scale.hodoo.databinding.ActivityPetAccountsBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PetAccountsActivity extends BaseActivity<PetAccountsActivity> implements PetAccounts.View {

    public static final String TAG = PetAccountsActivity.class.getSimpleName();

    ActivityPetAccountsBinding binding;

    PetGridAdapter adapter;

    PetAccounts.Presenter presenter;

    HomeActivity homeActivity;

    SettingListActivity settingListActivity;

    public static final int ADD_PET = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_accounts);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_management_pet)));
        super.setToolbarColor();
        presenter = new PetAccountPresenter(this);
        presenter.initUserData(getApplicationContext());

        homeActivity = (HomeActivity)HomeActivity.hActivity;
        settingListActivity = (SettingListActivity) SettingListActivity.settingListActivity;


    }

    @Override
    protected BaseActivity<PetAccountsActivity> getActivityClass() {
        return PetAccountsActivity.this;
    }

    @Override
    public void setAdapter(final List<PetAllInfos> data) {
        adapter = new PetGridAdapter(PetAccountsActivity.this, data);
        binding.petGridView.setAdapter(adapter);
        binding.petGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                if(position == ADD_PET){
                    Intent intent = new Intent(getApplicationContext(), PetRegistActivity.class);
                    intent.putExtra("petIdx", ADD_PET);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                    return;
//                    finish();
                }
                final BottomDialog dialog = BottomDialog.getInstance();
                List<IosStyleBottomAlert> btns = new ArrayList<>();
                btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.pet_account__main_pet_btn)).id(R.id.main_pet).build() );
                btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.pet_account__view_profile_btn)).id(R.id.profile_image).build() );
                btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.pet_account__edit_btn)).id(R.id.pet_info).build() );
                btns.add( IosStyleBottomAlert.builder().btnName(getString(R.string.pet_account__delete_btn)).id(R.id.pet_delete).build() );


                dialog.setOnclick(new BottomDialog.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PetAllInfos petAllInfos = (PetAllInfos) adapterView.getAdapter().getItem(position);
                        switch (v.getId()) {
                            case R.id.main_pet :
                                presenter.saveCurrentIdx( petAllInfos.getPet().getPetIdx() );
                                break;
                            case R.id.profile_image :
                                dialog.dismiss();
                                final Dialog fullDialog = new Dialog(PetAccountsActivity.this, android.R.style.Theme_Material_NoActionBar_Fullscreen);
                                fullDialog.setContentView(R.layout.dialog_profile);
                                fullDialog.show();
                                fullDialog.getWindow().setBackgroundDrawableResource(R.color.overlay_color);
                                ImageView profile = fullDialog.findViewById(R.id.pet_profile);
                                ImageButton close = fullDialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        fullDialog.dismiss();
                                    }
                                });
                                Picasso.with(PetAccountsActivity.this)
                                        .load(SharedPrefVariable.SERVER_ROOT + petAllInfos.getPetBasicInfo().getProfileFilePath())
                                        .error(R.drawable.icon_pet_profile)
                                        .into(profile);

                                break;
                            case R.id.pet_info :
//                                Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                                Intent intent = new Intent(getApplicationContext(), PetRegistActivity.class);
//                                intent.putExtra(HodooConstant.PET_EDIT_MODE_KEY, true);
                                intent.putExtra("petIdx", petAllInfos.getPet().getPetIdx());
                                startActivity(intent);
                                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                                break;
                            case R.id.pet_delete :
                                presenter.deletePet(petAllInfos.getPet().getPetIdx());
                                break;
                            case R.id.cancel :
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setButton(btns);
                dialog.show(getSupportFragmentManager(), TAG);

//                PetAllInfos petAllInfos = (PetAllInfos) adapterView.getAdapter().getItem(position);
//                if(position == ADD_PET){
//                    Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
//                    intent.putExtra("petIdx", ADD_PET);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
////                    finish();
//                }else{
//                    Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
//                    intent.putExtra("petIdx", petAllInfos.getPet().getPetIdx());
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
////                    finish();
//                }
            }
        });
    }

    @Override
    public void reFreshData( int idx ) {
        adapter.setPetIdx(idx);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void petDeleteResult(Integer result) {
        if(result == 1){
            presenter.getData();
        }
    }

    @Override
    public void goToPetRegistActivity() {
        homeActivity.finish();
        settingListActivity.finish();
        Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
        intent.putExtra("petIdx", 0);
        intent.putExtra("deleteAllPet", true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getData();
    }
}
