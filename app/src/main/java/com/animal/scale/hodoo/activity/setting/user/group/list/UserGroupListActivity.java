package com.animal.scale.hodoo.activity.setting.user.group.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfUserGroup;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityUserGroupListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

public class UserGroupListActivity extends BaseActivity<UserGroupListActivity> implements UserGroupList.View {

    public static final int ACCEPT_TYPE = 1;
    public static final int DECLINE_TYPE = 2;


    private ActivityUserGroupListBinding binding;
    private AdapterOfUserGroup adapter;
    private List<InvitationUser> users;
    private UserGroupPresenter presenter;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getInvitationList();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_group_list);
        presenter = new UserGroupPresenter(this, this);

        binding.setActivityInfo(new ActivityInfo("그룹 참여 요청"));
        super.setToolbarColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getInvitationList();
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
    }

    @Override
    protected BaseActivity<UserGroupListActivity> getActivityClass() {
        return this;
    }

    @Override
    public void setAdapterData(List<InvitationUser> users) {
        if ( adapter == null ) {
            adapter = new AdapterOfUserGroup(this, users);
            adapter.setOnBtnClickListener(new AdapterOfUserGroup.OnBtnClickListener() {
                @Override
                public void onDeclineBtn(int toIdx, int fromIdx) {
                    presenter.setInvitationState(DECLINE_TYPE, toIdx, fromIdx);
                }

                @Override
                public void onAcceptBtn(int toIdx, int fromIdx) {
                    presenter.setInvitationState(ACCEPT_TYPE, toIdx, fromIdx);
                }
            });
            binding.groupRequestList.setAdapter(adapter);
        } else {
            adapter.setData(users);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
