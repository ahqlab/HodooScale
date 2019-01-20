package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.PetAccountGridBinding;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PetGridAdapter extends BaseAdapter{

    Activity activity;
    private LayoutInflater inflater;
    private List<PetAllInfos> data;

    PetAccountGridBinding binding;
    SharedPrefManager mSharedPrefManager;
    private int currentPetIdx;

    public PetGridAdapter(Activity activity, List<PetAllInfos> data) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        mSharedPrefManager = SharedPrefManager.getInstance(activity.getApplicationContext());
        currentPetIdx = mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX);
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
            binding.setDomain(data.get(position).getPetBasicInfo());
            convertView.setTag(binding);
        } else {
            binding = (PetAccountGridBinding) convertView.getTag();
            binding.setDomain(data.get(position).getPetBasicInfo());
        }
        if ( binding.selectedIcon.getVisibility() == View.VISIBLE )
            binding.selectedIcon.setVisibility(View.GONE);
        if ( position != 0 && data.get(position).getPet().getPetIdx() == currentPetIdx ) {
            binding.selectedIcon.setColorFilter(Color.WHITE);
            binding.selectedIcon.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
    public void setPetIdx ( int idx ) {
        currentPetIdx = idx;
    }
}
