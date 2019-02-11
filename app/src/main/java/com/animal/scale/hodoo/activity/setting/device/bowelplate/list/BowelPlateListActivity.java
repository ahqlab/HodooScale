package com.animal.scale.hodoo.activity.setting.device.bowelplate.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.device.regist.DeviceRegistActivity;
import com.animal.scale.hodoo.activity.wifi.WifiSearchActivity;
import com.animal.scale.hodoo.adapter.AbsractCommonAdapter;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityBowelPlateListBinding;
import com.animal.scale.hodoo.databinding.ListviewBowelPlateItemBinding;
import com.animal.scale.hodoo.databinding.SettingListviewBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.util.MathUtil;

import java.util.List;

public class BowelPlateListActivity extends BaseActivity<BowelPlateListActivity> implements ActivityBowelPlateListIn.View {

    ActivityBowelPlateListBinding binding;

    ActivityBowelPlateListIn.Presenter presenter;

    AbsractCommonAdapter<Device> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bowel_plate_list);
        binding.setActivity(BowelPlateListActivity.this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_list_bowelplate)));
        presenter = new ActivityBowelPlateListPresenter(this);
        presenter.loadData(BowelPlateListActivity.this);
        presenter.getMyBowlPlateList();

    }

    @Override
    protected BaseActivity<BowelPlateListActivity> getActivityClass() {
        return BowelPlateListActivity.this;
    }

    @BindingAdapter({"onCheckedChanged"})
    public static void onCheckedChanged(Switch aSwitch, String connect) {
        if (connect.matches("ON")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
    }

    public void onCheckedChanged(int idx) {
        Log.e("HJLEE", "IDX :" + idx);
    }

    @Override
    protected void onResume() {

        presenter.getMyBowlPlateList();
        super.onResume();
    }

    @Override
    public void MyBowlPlateList(final List<Device> list) {
        if(list.size() > 0){
            adapter = new AbsractCommonAdapter<Device>(this, list) {
                ListviewBowelPlateItemBinding adapterBinding;
                @Override
                protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = adapter.inflater.inflate(R.layout.listview_bowel_plate_item, null);
                        adapterBinding = DataBindingUtil.bind(convertView);
                        adapterBinding.setDomain(adapter.data.get(position));
                        convertView.setTag(adapterBinding);
                    } else {
                        adapterBinding = (ListviewBowelPlateItemBinding) convertView.getTag();
                        adapterBinding.setDomain(adapter.data.get(position));
                    }
                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            deleteDlalog(adapter.data.get(position).getDeviceIdx(), list.size());
                            return false;
                        }
                    });
                    adapterBinding.usedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            presenter.setChangeSwithStatus(adapter.data.get(position).getDeviceIdx(), b);
                        }
                    });
                    return adapterBinding.getRoot();
                }
            };
            binding.bowelPlateListView.setAdapter(adapter);

        }else{
            Intent i = new Intent(this, DeviceRegistActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void setChangeSwithStatusResult(Integer integer) {
        // adapter.notifyDataSetChanged();

    }

    @Override
    public void setChangeDeviceRegistedResult(Integer integer) {
        presenter.getMyBowlPlateList();
    }

    public void onClickBowelPlateRegistBtn(View view) {
        BowelPlateListActivity.super.moveIntent(this, WifiSearchActivity.class, 0, 0, false);
    }

    public void deleteDlalog(final int deviceIdx, final int size) {
        final String[] values = new String[]{
                getResources().getString(R.string.delete)
        };
        super.showBasicOneBtnPopup(null, null)
                .setItems(values, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setChangeDeviceRegisted(deviceIdx, false);
                        dialog.dismiss();
                    }
                }).show();
    }
}
