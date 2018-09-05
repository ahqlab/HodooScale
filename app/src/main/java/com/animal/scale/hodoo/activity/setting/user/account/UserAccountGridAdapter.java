package com.animal.scale.hodoo.activity.setting.user.account;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.UserAccountGridBinding;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

public class UserAccountGridAdapter extends BaseAdapter{
    Activity activity;
    private LayoutInflater inflater;
    private List<User> data;

    UserAccountGridBinding binding;

    public UserAccountGridAdapter(Activity activity, List<User> data) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.user_account_grid, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            convertView.setTag(binding);
        } else {
            binding = (UserAccountGridBinding) convertView.getTag();
            binding.setDomain(data.get(position));
        }
        return binding.getRoot();
    }
}
