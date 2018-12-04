package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityPetAccountsBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class PetAccountsActivity extends BaseActivity<PetAccountsActivity> implements PetAccounts.View {

    ActivityPetAccountsBinding binding;

    PetGridAdapter adapter;

    PetAccounts.Presenter presenter;

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
        presenter.getData();
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PetAllInfos petAllInfos = (PetAllInfos) adapterView.getAdapter().getItem(position);
                if(position == ADD_PET){
                    Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                    intent.putExtra("petIdx", ADD_PET);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                    intent.putExtra("petIdx", petAllInfos.getPet().getPetIdx());
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                    finish();
                }
            }
        });
    }
}
