package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.FeedListviewBinding;
import com.animal.scale.hodoo.databinding.FeedManagerListviewBinding;
import com.animal.scale.hodoo.domain.MealHistoryContent;

import java.util.List;

public class AdapterOfMealManager extends BaseAdapter {

    Context context;

    private LayoutInflater inflater;

    private List<MealHistoryContent> data;

    FeedManagerListviewBinding binding;

    SharedPrefManager sharedPreferences;

    public AdapterOfMealManager(Context context, List<MealHistoryContent> data) {
        this.context = context;
        sharedPreferences = SharedPrefManager.getInstance(context);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MealHistoryContent getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.feed_manager_listview, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            convertView.setTag(binding);
        }else{
            binding = (FeedManagerListviewBinding) convertView.getTag();
            binding.setDomain(data.get(position));
        }

        if(sharedPreferences.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID) == data.get(position).getUser().getUserIdx()){
            binding.userCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_pink));
            binding.userLastName.setText(data.get(position).getUser().getNickname().substring(0, 1));
        }else{
            binding.userCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_gray));
            binding.userLastName.setText(data.get(position).getUser().getNickname().substring(0, 1));
        }
        return binding.getRoot();
    }
}
