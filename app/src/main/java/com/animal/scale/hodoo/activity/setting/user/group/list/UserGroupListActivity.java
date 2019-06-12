package com.animal.scale.hodoo.activity.setting.user.group.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfUserGroup;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityUserGroupListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.BadgeUtils;

import java.util.List;

/**
 * 그룹참여 요청 히스토리 로그
 */
public class UserGroupListActivity extends BaseActivity<UserGroupListActivity> implements UserGroupList.View {

    public static final int ACCEPT_TYPE = 1;
    public static final int DECLINE_TYPE = 2;


    private ActivityUserGroupListBinding binding;
    private AdapterOfUserGroup adapter;
    private List<InvitationUser> users;
    private UserGroupList.Presenter presenter;

    //새로운 아이템 추가를 위한 broadcast
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

        binding.setActivityInfo(new ActivityInfo(getString(R.string.common__invitation_title)));
        super.setToolbarColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //그룹 요청 히스토리 리승트 서버 요청
        presenter.getInvitationList();
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
    }

    @Override
    protected BaseActivity<UserGroupListActivity> getActivityClass() {
        return this;
    }

    /**
     * 그룹 요청 서버 Callback
     * @param users
     * @Description 리스르 셋팅하며 승인 및 거절 버튼을 제공한다.
     */
    @Override
    public void setAdapterData(List<InvitationUser> users) {
        if ( adapter == null ) {
            adapter = new AdapterOfUserGroup(this, users);
            adapter.setOnBtnClickListener(new AdapterOfUserGroup.OnBtnClickListener() {
                @Override
                public void onDeclineBtn(int toIdx, int fromIdx) {
                    //상태 승인으로 변경
                    presenter.setInvitationState(DECLINE_TYPE, toIdx, fromIdx);
                }

                @Override
                public void onAcceptBtn(int toIdx, int fromIdx) {
                    //거절
                    presenter.setInvitationState(ACCEPT_TYPE, toIdx, fromIdx);
                }
            });
            binding.groupRequestList.setAdapter(adapter);
        } else {
            adapter.setData(users);
        }

    }
    //벳지 상태 클리어
    @Override
    public void setPushCount(int count) {

        if ( count <= 0 ) {
            BadgeUtils.clearBadge(this);
        } else {
            BadgeUtils.setBadge(this, Math.min(count, 99));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
