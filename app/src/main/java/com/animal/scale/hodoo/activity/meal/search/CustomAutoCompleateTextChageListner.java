package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationActivity;
import com.animal.scale.hodoo.adapter.AdapterOfSearchFeed;
import com.animal.scale.hodoo.adapter.AdapterOfSearchHistory;
import com.animal.scale.hodoo.domain.SearchHistory;

import java.util.List;

public class CustomAutoCompleateTextChageListner implements TextWatcher , MealSearchIn.AdapterView, AdapterOfSearchHistory.ListBtnClickListener{

    Context context;

    MealSearchIn.AdapterPresenter presenter;

    MealSearchActivity activity;

    AdapterOfSearchFeed Adapter;

    AdapterOfSearchHistory sAdapter;


    public CustomAutoCompleateTextChageListner(Context context) {
        this.context = context;
        presenter = new MeaAdapterlSearchPresenter(this);
        presenter.loadData(context);
        activity = ((MealSearchActivity) context);
        presenter.setSearchHistory();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().matches("")){
            presenter.setSearchHistory();
        }else {
            presenter.getSearchFeed(charSequence.toString());
        }
    }

    @Override
    public void setFeedList(List<AutoCompleateFeed> d) {
        Adapter = new AdapterOfSearchFeed(context, d);
        activity.binding.feedListview.setAdapter(Adapter);
        activity.binding.listTitle.setText(context.getString(R.string.istyle_search_list));
    }

    @Override
    public void setSearchHistory(List<SearchHistory> searchHistory) {
        sAdapter = new AdapterOfSearchHistory(context, searchHistory, this);
        activity.binding.feedListview.setAdapter(sAdapter);
        activity.binding.listTitle.setText(context.getString(R.string.istyle_recent_search_word));
    }

    @Override
    public void onListBtnClick(int position) {
        activity.onListBtnClick(sAdapter.getItem(position));
        sAdapter.notifyDataSetChanged();
        activity.binding.feedListview.invalidateViews();
        presenter.setSearchHistory();
    }
}
