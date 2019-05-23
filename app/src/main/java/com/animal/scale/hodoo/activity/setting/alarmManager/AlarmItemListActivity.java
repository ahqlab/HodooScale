package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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

    private int mNumber = 0;
    private boolean allCheckState = false;
    private boolean selectState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_manager);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_manager);
        binding.setActivity(AlarmItemListActivity.this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.alarm_setting)));
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
    public void setAlarmItem(final List<AlarmItem> alarmItems) {

        adapterOfNotification = new AbsractCommonAdapter<AlarmItem>(AlarmItemListActivity.this, alarmItems) {

            NotificationListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = adapterOfNotification.inflater.inflate(R.layout.notification_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (NotificationListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(data.get(position));
                }
                return adapterBinding.getRoot();
            }

            @Override
            protected void setUsetEditConstructor() {

            }
        };
        binding.notificationListview.setAdapter(adapterOfNotification);
        presenter.getAlarm();
    }

    @Override
    public void setAlarm(int number) {
        mNumber = number;

        if ( mNumber == 1 ) {
            for (int i = 0; i < binding.notificationListview.getChildCount(); i++) {
                LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(i);

                TextView text = (TextView) wrap.getChildAt(0);
                text.setTextColor(Color.parseColor("#200000"));

                Switch target = (Switch) wrap.getChildAt(1);
                target.setChecked(true);
            }
        } else {
            for (int i = 0; i < binding.notificationListview.getChildCount(); i++) {
                if ( (mNumber & (0x01 << i)) != 0 ) {
                    LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(i);

                    TextView text = (TextView) wrap.getChildAt(0);
                    text.setTextColor(Color.parseColor("#200000"));

                    Switch target = (Switch) wrap.getChildAt(1);
                    target.setChecked(true);
                }
            }
            int count = 0;
            for (int i = 1; i < binding.notificationListview.getChildCount(); i++) {
                LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(i);
                Switch target = (Switch) wrap.getChildAt(1);
                if ( target.isChecked() )
                    count++;
            }

            if ( count == binding.notificationListview.getChildCount() - 1 ) {
                LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(0);
                Switch target = (Switch) wrap.getChildAt(1);
                TextView text = (TextView) wrap.getChildAt(0);
                text.setTextColor(Color.parseColor("#200000"));
                target.setChecked(true);
            }
        }


        for (int i = 0; i < binding.notificationListview.getChildCount(); i++) {
            LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(i);
            Switch target = (Switch) wrap.getChildAt(1);
            final int position = i;
            target.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if ( position == 0 ) {
                        if ( !allCheckState )
                            allCheckState = true;
                        if ( selectState ) {
                            selectState = false;
                            return;
                        }
                        for (int j = 0; j < binding.notificationListview.getChildCount(); j++) {
                            LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(j);
                            Switch target = (Switch) wrap.getChildAt(1);
                            TextView text = (TextView) wrap.getChildAt(0);
                            text.setTextColor(b ? Color.parseColor("#200000") : ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_light_gray));
                            target.setChecked(b);
                        }
                        mNumber = b ? 1 : 0;
                    }

                    if ( position != 0 ) {
                        if ( !allCheckState ) {
                            selectState = true;
                            if (!b) {
                                LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(0);
                                Switch target = (Switch) wrap.getChildAt(1);
                                TextView text = (TextView) wrap.getChildAt(0);

                                if (target.isChecked()) {
                                    text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_light_gray));
                                    target.setChecked(false);
                                    mNumber = 0;
                                    for (int i = 0; i < binding.notificationListview.getChildCount(); i++) {
                                        LinearLayout tempWrap = (LinearLayout) binding.notificationListview.getChildAt(i);
                                        Switch tempTarget = (Switch) tempWrap.getChildAt(1);
                                        if ( tempTarget.isChecked() )
                                            mNumber += (0x01 << i);
                                    }
                                } else
                                    mNumber -= (0x01 << position);

                            } else {
                                int count = 0;
                                for (int i = 1; i < binding.notificationListview.getChildCount(); i++) {
                                    LinearLayout tempWrap = (LinearLayout) binding.notificationListview.getChildAt(i);
                                    Switch tempTarget = (Switch) tempWrap.getChildAt(1);
                                    if ( tempTarget.isChecked() )
                                        count++;
                                }

                                mNumber += (0x01 << position);
                                if ( count == binding.notificationListview.getChildCount() - 1 ) {
                                    LinearLayout wrap = (LinearLayout) binding.notificationListview.getChildAt(0);
                                    Switch target = (Switch) wrap.getChildAt(1);
                                    TextView text = (TextView) wrap.getChildAt(0);
                                    text.setTextColor(Color.parseColor("#200000"));
                                    target.setChecked(true);
                                    mNumber = 1;
                                }


                            }
                            LinearLayout wrap = (LinearLayout) compoundButton.getParent();
                            TextView text = (TextView) wrap.getChildAt(0);
                            text.setTextColor(b ? Color.parseColor("#200000") : ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_light_gray));
                        }
                    }
                    if ( position == binding.notificationListview.getChildCount() - 1 ) {
                        allCheckState = false;
                        selectState = false;
                    }
                    if ( allCheckState )
                        allCheckState = false;
                    if ( !allCheckState && selectState )
                        selectState = false;
                }
            });
        }
    }

    @Override
    public void saveNotiListner(View view) {
        presenter.saveAlarm(mNumber);
    }

    @Override
    public void done( int result ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("저장되었습니다")
                .setMessage("알림설정이 저장되었습니다.")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        dialog.show();
    }
}
