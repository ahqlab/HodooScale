package com.animal.scale.hodoo.activity.setting.list;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.MyAccountActivity;
import com.animal.scale.hodoo.activity.setting.device.setting.DeviceSettingActivity;
import com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountActivity;
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
    public final static int DEVICE_SETTING = 4;
    public final static int USER_MANAGEMENT = 5;
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
                }else if(position == DEVICE_SETTING){
                    Intent intent = new Intent(getApplicationContext(), DeviceSettingActivity.class);
                    startActivity(intent);
                }else if(position == PET_MANAGEMENT){
                    Intent intent = new Intent(getApplicationContext(), PetAccountsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                }else if(position == USER_MANAGEMENT){
                    Intent intent = new Intent(getApplicationContext(), UserAccountActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                }
            }
        });
    }
}
