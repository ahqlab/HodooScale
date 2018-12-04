package com.animal.scale.hodoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.list.FeedListActivity;
import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.databinding.FeedListviewBinding;
import com.animal.scale.hodoo.domain.Feed;

import java.util.List;

public class AdapterOfSearchFeed extends BaseAdapter {

    Context context;

    private LayoutInflater inflater;

    private List<AutoCompleateFeed> data;

    FeedListviewBinding binding;

    public AdapterOfSearchFeed(Context context, List<AutoCompleateFeed> data) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.feed_listview, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            convertView.setTag(binding);
        }else{
            binding = (FeedListviewBinding) convertView.getTag();
            binding.setDomain(data.get(position));
        }
        return binding.getRoot();
    }
}
