package com.animal.scale.hodoo.activity.setting.device.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityDeviceSettingBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

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

    @Override
    protected BaseActivity<DeviceSettingActivity> getActivityClass() {
        return DeviceSettingActivity.this;
    }
}
