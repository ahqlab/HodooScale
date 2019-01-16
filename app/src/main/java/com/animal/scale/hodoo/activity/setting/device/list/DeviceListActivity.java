package com.animal.scale.hodoo.activity.setting.device.list;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.device.bowelplate.list.BowelPlateListActivity;
import com.animal.scale.hodoo.activity.setting.device.feeder.FeederOrderActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityDeviceListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class DeviceListActivity extends BaseActivity<DeviceListActivity> {

    ActivityDeviceListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_select_device)));
        super.setToolbarColor();
        String[] values = new String[] {
                this.getString(R.string.bowel_plate),
                this.getString(R.string.pet_bed),
                this.getString(R.string.pet_feeder),
                this.getString(R.string.cushion),
                this.getString(R.string.harness),
                this.getString(R.string.pendant)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        binding.deviceListListView.setAdapter(adapter);
        binding.deviceListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String device = (String) adapterView.getItemAtPosition(i);
                if(device.matches(getString(R.string.bowel_plate))){
                    DeviceListActivity.super.moveIntent(DeviceListActivity.this, BowelPlateListActivity.class, 0,0, false);
                }else if(device.matches(getString(R.string.pet_feeder))){
                    DeviceListActivity.super.moveIntent(DeviceListActivity.this, FeederOrderActivity.class, 0,0, false);
                }
            }
        });
    }

    @Override
    protected BaseActivity<DeviceListActivity> getActivityClass() {
        return DeviceListActivity.this;
    }
}

