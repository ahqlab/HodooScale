package com.animal.scale.hodoo.activity.setting.user.group.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountActivity;
import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListActivity;
import com.animal.scale.hodoo.adapter.AdapterOfSetting;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityUserGroupManagerBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public class UserGroupManagerActivity extends BaseActivity<UserGroupManagerActivity> implements UserGropManager.View {
    private ActivityUserGroupManagerBinding binding;
    private UserGropManager.Presenter presenter;
    private AdapterOfSetting adapter;
    private int count;
    private List<SettingMenu> mMenu;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getBadgeCount(UserGroupPresenter.UPDATE_TYPE);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_group_manager);
        binding.setActivityInfo(new ActivityInfo("그룹 관리"));
        super.setToolbarColor();
        presenter = new UserGroupPresenter(this,this);

    }

    @Override
    protected BaseActivity<UserGroupManagerActivity> getActivityClass() {
        return this;
    }

    @Override
    public void setBadgeCount(int type, int count) {
        this.count = count;
        switch (type) {
            case UserGroupPresenter.INIT_TYPE :
                presenter.getSttingListMenu();
                break;
            case UserGroupPresenter.UPDATE_TYPE:
                mMenu.get(1).setBadgeCount(count);
                adapter.setData(mMenu);
                break;
        }

    }

    @Override
    public void setListviewAdapter(List<SettingMenu> menu) {
        mMenu = menu;
        for (int i = 0; i < menu.size(); i++) {
            if ( i == 1 ) {
                menu.get(i).setBadgeCount(count);
            } else {
                menu.get(i).setBadgeCount(0);
            }
        }
        adapter = new AdapterOfSetting(this, menu);
        binding.userGroupListView.setAdapter(adapter);
        binding.userGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch ( position ) {
                    case 0 :
                        UserGroupManagerActivity.super.moveIntent(UserGroupManagerActivity.this, UserAccountActivity.class, 0,0, false);
                        break;
                    case 1:
                        UserGroupManagerActivity.super.moveIntent(UserGroupManagerActivity.this, UserGroupListActivity.class, 0,0, false);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBadgeCount( UserGroupPresenter.INIT_TYPE );
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
