package com.animal.scale.hodoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.PetsListviewItemBinding;
import com.animal.scale.hodoo.databinding.SettingListviewBinding;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.ArrayList;
import java.util.List;

public class AdapterOfPets extends BaseAdapter {

    private final String TAG = AdapterOfPets.class.getSimpleName();

    Activity activity;
    Context context;
    private LayoutInflater inflater;
    private List<PetAllInfos> data;
    PetsListviewItemBinding binding;

    public int listViewHeight = 0;

    public AdapterOfPets(Activity activity, List<PetAllInfos> data, int currentPetIdx) {
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.context = context;
        this.data = data;
        View view = LayoutInflater.from(activity).inflate(R.layout.pets_listview_item, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if ( data.size() >= 3 )
            listViewHeight = view.getMeasuredHeight() * 3;
        Log.e(TAG, String.format("height : %d", view.getMeasuredHeight()));
    }

    public List<PetAllInfos> getList(){
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PetAllInfos getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.pets_listview_item, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            //binding.setCurrentPetIdx(currentPetIdx);
            convertView.setTag(binding);
        } else {
            binding = (PetsListviewItemBinding) convertView.getTag();
            binding.setDomain(data.get(position));
            //binding.setCurrentPetIdx(currentPetIdx);
        }
        return binding.getRoot();
    }
}
