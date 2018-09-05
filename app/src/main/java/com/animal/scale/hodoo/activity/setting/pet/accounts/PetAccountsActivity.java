package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.second.BasicSecondInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityPetAccountsBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

public class PetAccountsActivity extends BaseActivity<PetAccountsActivity> implements PetAccounts.View {

    ActivityPetAccountsBinding binding;

    PetGridAdapter adapter;

    PetAccounts.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_accounts);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("펫 관리"));
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
    public void setAdapter(final List<PetBasicInfo> data) {
        adapter = new PetGridAdapter(PetAccountsActivity.this, data);
        binding.petGridView.setAdapter(adapter);
        binding.petGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), BasicSecondInformationRegistActivity.class);
                intent.putExtra("petId", data.get(i).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
    }
}
