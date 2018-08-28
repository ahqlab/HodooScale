package com.animal.scale.hodoo.activity.setting.list;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.MyAccountActivity;
import com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity;
import com.animal.scale.hodoo.adapter.AdapterOfSetting;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySettingListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public class SettingListActivity extends BaseActivity<SettingListActivity> implements SettingList.View{

    ActivitySettingListBinding binding;

    AdapterOfSetting Adapter;

    SettingList.Presenter presenter;

    public final static int MY_CAAOUNT = 0;
    public final static int PET_MANAGEMENT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("설정"));
        super.setToolbarColor();
        presenter = new SettingListPresenter(this);
        presenter.getSttingListMenu();
    }

    @Override
    protected BaseActivity<SettingListActivity> getActivityClass() {
        return SettingListActivity.this;
    }


    @Override
    public void setListviewAdapter(List<SettingMenu> menus) {
        Adapter = new AdapterOfSetting(SettingListActivity.this, menus);
        binding.settingListview.setAdapter(Adapter);
        binding.settingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == MY_CAAOUNT){
                    Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                    finish();
                }else if(position == PET_MANAGEMENT){
                    Intent intent = new Intent(getApplicationContext(), PetAccountsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                    finish();
                }
            }
        });
    }
}
