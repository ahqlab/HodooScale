package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityPetAccountsBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

import java.util.ArrayList;
import java.util.List;

public class PetAccountsActivity extends BaseActivity<PetAccountsActivity> implements PetAccounts.View {

    ActivityPetAccountsBinding binding;

    PetGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_accounts);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("펫 관리"));
        super.setToolbarColor();
        adapter = new PetGridAdapter(PetAccountsActivity.this, pets());
        binding.petGridView.setAdapter(adapter);
    }

    @Override
    protected BaseActivity<PetAccountsActivity> getActivityClass() {
        return PetAccountsActivity.this;
    }

    public List<PetAccount> pets() {
        List<PetAccount> list = new ArrayList<PetAccount>();
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        list.add(new PetAccount("1", "2"));
        return list;
    }
}
