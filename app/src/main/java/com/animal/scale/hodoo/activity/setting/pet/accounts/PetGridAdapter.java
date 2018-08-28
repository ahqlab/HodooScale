package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.PetAccountGridBinding;

import java.util.List;

public class PetGridAdapter extends BaseAdapter{

    Activity activity;
    private LayoutInflater inflater;
    private List<PetAccount> data;

    PetAccountGridBinding binding;

    public PetGridAdapter(Activity activity, List<PetAccount> data) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.pet_account_grid, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            convertView.setTag(binding);
        } else {
            binding = (PetAccountGridBinding) convertView.getTag();
            binding.setDomain(data.get(position));
        }
        return binding.getRoot();
    }
}
