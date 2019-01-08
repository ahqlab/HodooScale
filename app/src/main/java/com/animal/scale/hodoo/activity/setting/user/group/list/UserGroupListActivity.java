package com.animal.scale.hodoo.activity.setting.user.group.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfUserGroup;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityUserGroupListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

public class UserGroupListActivity extends BaseActivity<UserGroupListActivity> implements UserGroupList.View {
    private ActivityUserGroupListBinding binding;
    private AdapterOfUserGroup adapter;
    private List<InvitationUser> users;
    private UserGroupPresenter presenter;
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
    }

    @Override
    protected BaseActivity<UserGroupListActivity> getActivityClass() {
        return this;
    }

    @Override
    public void setAdapterData(List<InvitationUser> users) {
        adapter = new AdapterOfUserGroup(this, users);
        binding.groupRequestList.setAdapter(adapter);
    }
}
