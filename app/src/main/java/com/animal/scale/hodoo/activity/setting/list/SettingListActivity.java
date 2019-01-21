package com.animal.scale.hodoo.activity.setting.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.MyAccountActivity;
import com.animal.scale.hodoo.activity.setting.device.bowelplate.list.BowelPlateListActivity;
import com.animal.scale.hodoo.activity.setting.device.feeder.FeederOrderActivity;
import com.animal.scale.hodoo.activity.setting.device.list.DeviceListActivity;
import com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountActivity;
import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListActivity;
import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGroupManagerActivity;
import com.animal.scale.hodoo.adapter.AdapterOfExpandable;
import com.animal.scale.hodoo.adapter.AdapterOfSetting;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivitySettingListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class SettingListActivity extends BaseActivity<SettingListActivity> implements SettingList.View{

    ActivitySettingListBinding binding;

    AdapterOfSetting oldAdapter;

    AdapterOfExpandable adapter;

    SettingList.Presenter presenter;

    public final static int MY_CAAOUNT = 0;
    public final static int DEVICE_SETTING = 4;
    public final static int USER_MANAGEMENT = 5;
    public final static int PET_MANAGEMENT = 6;

    public final static int GENERAL = 0;
    public final static int USER = 1;
    public final static int DEVICE= 2;
    public final static int HODOO_LINK = 3;
    public final static int PET = 4;
    public final static int SUPPORT = 5;


    //general
    public final static int ACCOUNT = 0;
    public final static int NOTIFICATION = 1;
    public final static int LOGOUT = 2;

    //device
    public final static int POTTY = 0;
    public final static int FEEDER = 1;
    public final static int BED = 2;
    public final static int CUSHION = 3;
    public final static int HARNESS = 4;
    public final static int TAG = 5;

    //link
    public final static int GROUP_LIST = 0;
    public final static int REQUEST = 1;

    SharedPrefManager sharedPrefManager;
    List<SettingMenu> menus;

    ArrayList<List<SettingMenu>> settingMenus;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getInvitationCount();
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
        presenter.getStringSettingList(this);
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
        oldAdapter = new AdapterOfSetting(SettingListActivity.this, menus);
        binding.settingListview.setAdapter(oldAdapter);
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
    public void setExpandableListAdapter(final ArrayList<String> title, ArrayList<List<SettingMenu>> content) {
        settingMenus = content;
        adapter = new AdapterOfExpandable(this, title, content);

        binding.settingList.setAdapter( adapter );
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.settingList.expandGroup(i);
        }
        binding.settingList.setGroupIndicator(null);
        binding.settingList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        binding.settingList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int titlePosition, int contentPosition, long l) {
                if ( titlePosition == USER ) {
                    if ( contentPosition == ACCOUNT)
                        SettingListActivity.super.moveIntent(SettingListActivity.this, MyAccountActivity.class, 0,0, false);
                    else if ( contentPosition == LOGOUT ) {
                        presenter.logout();
                        return false;
                    }
                } else if ( titlePosition == DEVICE ) {
                    if ( contentPosition == POTTY )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, BowelPlateListActivity.class, 0,0, false);
                    else if ( contentPosition == FEEDER )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, FeederOrderActivity.class, 0,0, false);
                } else if ( titlePosition == HODOO_LINK ) {
                    if ( contentPosition == GROUP_LIST )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, UserAccountActivity.class, 0,0, false);
                    else if ( contentPosition == REQUEST )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, UserGroupListActivity.class, 0,0, false);
                } else if ( titlePosition == PET ) {
                    SettingListActivity.super.moveIntent(SettingListActivity.this, PetAccountsActivity.class, 0,0, false);
                }
                return true;
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
    public void updateBadgeCount( int count ) {
        //null 처리
        adapter.setBadge(SettingListActivity.HODOO_LINK, SettingListActivity.REQUEST, count);
//        settingMenus.get(SettingListActivity.HODOO_LINK).get(SettingListActivity.REQUEST).setBadgeCount(count);
//        menus.get(5).setBadgeCount(count);
//        oldAdapter.setData(menus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
        presenter.getInvitationCount();
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
