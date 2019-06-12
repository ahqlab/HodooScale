package com.animal.scale.hodoo.activity.setting.device.setting;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityDeviceSettingBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;


/**
 * 삭제
 */
public class DeviceSettingActivity extends BaseActivity<DeviceSettingActivity> {

    ActivityDeviceSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_device_setting);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_setting);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.device_setting_activity_title)));
        super.setToolbarColor();
    }

    /*@Override
    public void setToolbarColor() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(null);
    }*/

    @Override
    protected BaseActivity<DeviceSettingActivity> getActivityClass() {
        return DeviceSettingActivity.this;
    }
}
