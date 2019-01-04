package com.animal.scale.hodoo.activity.setting.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.info.ChangeUserInfoActivity;
import com.animal.scale.hodoo.adapter.AdapterOfMyAccountList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityMyAccountBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public class MyAccountActivity extends BaseActivity<MyAccountActivity> implements MyAccount.View {

    ActivityMyAccountBinding binding;

    MyAccount.Presenter presenter;

    AdapterOfMyAccountList adapter;

    public SharedPrefManager mSharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_account);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_my_account)));
        super.setToolbarColor();
        presenter = new MyAccountPresenter(this);
        presenter.initLoadData(getApplicationContext());
        presenter.getSttingListMenu();
    }

    @Override
    protected BaseActivity<MyAccountActivity> getActivityClass() {
        return MyAccountActivity.this;
    }

    @Override
    public void setListviewAdapter(List<SettingMenu> menus) {
        adapter = new AdapterOfMyAccountList(MyAccountActivity.this, menus);
        binding.settingListview.setAdapter(adapter);
        binding.settingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == MyAccount.LOGOUT){
                    presenter.initUserData();
                }else if(position == MyAccount.CHANGE_USER_INFO){
                    presenter.changePassword();
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
    public void goChangePasswordPage() {
        Intent intent = new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }
}
