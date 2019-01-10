package com.animal.scale.hodoo.activity.setting.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.MyAccountActivity;
import com.animal.scale.hodoo.activity.setting.device.list.DeviceListActivity;
import com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity;
import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGroupManagerActivity;
import com.animal.scale.hodoo.adapter.AdapterOfSetting;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
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

    SharedPrefManager sharedPrefManager;
    List<SettingMenu> menus;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.e(TAG, "messageeeeeeeeeeeeeeeeeeeeeeeeee : " + message);
            int count = sharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT);
            menus.get(5).setBadgeCount(count);
            Adapter.setData(menus);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_setting)));
        super.setToolbarColor();
        sharedPrefManager = SharedPrefManager.getInstance(this);
        presenter = new SettingListPresenter(this);
        presenter.loadData(SettingListActivity.this);

    }

    @Override
    protected BaseActivity<SettingListActivity> getActivityClass() {
        return SettingListActivity.this;
    }

    @Override
    public void setListviewAdapter(int badgeCount, List<SettingMenu> menus) {
        this.menus = menus;
        for (int i = 0; i < menus.size(); i++) {
            if ( i == 5 ) {
                menus.get(i).setBadgeCount(badgeCount);
            } else {
                menus.get(i).setBadgeCount(0);
            }
        }
        Adapter = new AdapterOfSetting(SettingListActivity.this, menus);
        binding.settingListview.setAdapter(Adapter);
        binding.settingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == MY_CAAOUNT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, MyAccountActivity.class, 0,0, false);
                }else if(position == DEVICE_SETTING){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, DeviceListActivity.class, 0,0, false);
                }else if(position == PET_MANAGEMENT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, PetAccountsActivity.class, 0,0, false);
                }else if(position == USER_MANAGEMENT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, UserGroupManagerActivity.class, 0,0, false);
                }
            }
        });
    }

    @Override
    public void goLoginPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);

        finishAffinity();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSttingListMenu();
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    public void logout ( View v ) {
        presenter.logout();
    }
}
