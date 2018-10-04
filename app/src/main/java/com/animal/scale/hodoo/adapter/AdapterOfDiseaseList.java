package com.animal.scale.hodoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.DiseaseListviewBinding;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AdapterOfDiseaseList extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    private List<Disease> data;
    private List<PetChronicDisease> chronic;
    List<Boolean> itemCheckList = new ArrayList<Boolean>();

    @Getter
    @Setter
    private int checkedCount = 0;

    DiseaseListviewBinding binding;

    public AdapterOfDiseaseList(Activity activity, List<Disease> data, List<PetChronicDisease> chronic) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.chronic = chronic;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.disease_listview, null);
            binding = DataBindingUtil.bind(convertView);
            convertView.setTag(binding);
        } else {
            binding = (DiseaseListviewBinding) convertView.getTag();
        }
        binding.setDisease(data.get(position));
        for (PetChronicDisease petChronicDisease: chronic) {
            if(petChronicDisease.getDiseaseName().matches(data.get(position).getName())){
                    Log.e("HJLEE", "checked : " + data.get(position).getName());
                    binding.choiceDisease.setChecked(true);
                    setCheckedCount(getCheckedCount() + 1);
                    data.get(position).setChecked(true);
            }
        }

        binding.choiceDisease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    data.get(position).setChecked(true);
                    setCheckedCount(getCheckedCount() + 1);
                }else{
                    data.get(position).setChecked(false);
                    setCheckedCount(getCheckedCount() - 1);
                }
            }
        });
        return binding.getRoot();
    }
}
