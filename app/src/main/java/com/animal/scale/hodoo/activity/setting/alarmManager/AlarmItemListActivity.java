package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AbsractCommonAdapter;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityNotificationManagerBinding;
import com.animal.scale.hodoo.databinding.NotificationListviewItemBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.AlarmItem;

import java.util.List;

public class AlarmItemListActivity extends BaseActivity<AlarmItemListActivity> implements AlarmItemListIn.View {

    ActivityNotificationManagerBinding binding;

    AlarmItemListIn.Presenter presenter;

    AbsractCommonAdapter<AlarmItem> adapterOfNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_manager);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_manager);
        binding.setActivity(AlarmItemListActivity.this);
        binding.setActivityInfo(new ActivityInfo("알람 설정"));
        super.setToolbarColor();
        presenter = new AlarmItemListPresenter(AlarmItemListActivity.this);
        presenter.loadData(AlarmItemListActivity.this);
        presenter.getAlarmItems();
    }

    @Override
    protected BaseActivity<AlarmItemListActivity> getActivityClass() {
        return AlarmItemListActivity.this;
    }

    public void setAdapter(List<AlarmItem> alarmItems) {
    }

    @Override
    public void setAlarmItem(List<AlarmItem> alarmItems) {

        adapterOfNotification = new AbsractCommonAdapter<AlarmItem>(AlarmItemListActivity.this, alarmItems) {

            NotificationListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = adapterOfNotification.inflater.inflate(R.layout.notification_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (NotificationListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(data.get(position));
                }
                adapterBinding.notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        //presenter.setChangeSwithStatus(adapter.data.get(position).getDeviceIdx(), b);
                    }
                });
                return adapterBinding.getRoot();
            }

            @Override
            protected void setUsetEditConstructor() {

            }
        };
        binding.notificationListview.setAdapter(adapterOfNotification);
    }
}
