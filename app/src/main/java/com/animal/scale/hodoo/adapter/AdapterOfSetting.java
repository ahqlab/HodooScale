package com.animal.scale.hodoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.SettingListviewBinding;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public class AdapterOfSetting extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    private List<SettingMenu> data;

    SettingListviewBinding binding;

    public AdapterOfSetting(Activity activity, List<SettingMenu> data) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.setting_listview, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setSetting(data.get(position));
            binding.setPosition(position);
            convertView.setTag(binding);
        } else {
            binding = (SettingListviewBinding) convertView.getTag();
            binding.setSetting(data.get(position));
            binding.setPosition(position);
        }
        return binding.getRoot();
    }
}
