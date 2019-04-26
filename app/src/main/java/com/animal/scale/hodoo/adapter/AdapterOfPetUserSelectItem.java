package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.ItemActivityQuestionContentBinding;
import com.animal.scale.hodoo.domain.PetUserSelectItem;

import java.util.ArrayList;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public class AdapterOfPetUserSelectItem extends BaseAdapter {
    public interface ItemClickListener {
        void OnClickListener( int position, View view );
    }
    private ItemActivityQuestionContentBinding binding;
    private Context context;
    private ArrayList<PetUserSelectItem> title;
    private LayoutInflater inflater;
    private ItemClickListener callback;
    public AdapterOfPetUserSelectItem(Context context, ArrayList<PetUserSelectItem> title, ItemClickListener callback) {
        this.context = context;
        this.title = title;
        this.callback = callback;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int i) {
        return title.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            binding = DataBindingUtil.inflate(inflater, R.layout.item_activity_question_content, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (ItemActivityQuestionContentBinding) view.getTag();
        }
        binding.activityQuestionContent.setText(title.get(i).getTitle());
        binding.activityQuestionEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnClickListener(i, view);
            }
        });
        if ( title.get(i).getSelectNum() > -1 ) {
            binding.activityQuestionEdt.setText( title.get(i).getChildItem().get( title.get(i).getSelectNum() ) );
        }
        return view;
    }
}
