package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.FeedManagerListviewBinding;
import com.animal.scale.hodoo.databinding.SearchHistoryListviewItemBinding;
import com.animal.scale.hodoo.domain.SearchHistory;

import java.util.List;

public class AdapterOfSearchHistory extends BaseAdapter implements  View.OnClickListener{

    Context context;
    private LayoutInflater inflater;
    private List<SearchHistory> data;
    SearchHistoryListviewItemBinding binding;

    public ListBtnClickListener listBtnClickListener ;

    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    public AdapterOfSearchHistory(Context context , List<SearchHistory> data,  ListBtnClickListener clickListener){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.listBtnClickListener = clickListener ;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SearchHistory getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.search_history_listview_item, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));
            binding.deleteBtn.setTag(position);
            binding.deleteBtn.setOnClickListener(this);
            convertView.setTag(binding);
        }else{
            binding = (SearchHistoryListviewItemBinding) convertView.getTag();
            binding.setDomain(data.get(position));
            binding.deleteBtn.setTag(position);
            binding.deleteBtn.setOnClickListener(this);
        }
        return binding.getRoot();
    }



    @Override
    public void onClick(View view) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) view.getTag()) ;
        }
    }
}
