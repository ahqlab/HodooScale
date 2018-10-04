package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.animal.scale.hodoo.adapter.AdapterOfFeed;

import java.util.List;

public class CustomAutoCompleateTextChageListner implements TextWatcher , MealSearchIn.AdapterView{

    Context context;

    MealSearchIn.AdapterPresenter presenter;

    MealSearchActivity activity;


    public CustomAutoCompleateTextChageListner(Context context) {
        this.context = context;
        presenter = new MeaAdapterlSearchPresenter(this);
        presenter.loadData(context);
        activity = ((MealSearchActivity) context);
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
            Log.e("HJLEE", "1");
        }else {
            presenter.getSearchFeed(charSequence.toString());
        }
    }

    @Override
    public void setFeedList(List<AutoCompleateFeed> d) {
        activity.adapter.notifyDataSetChanged();
        AdapterOfFeed Adapter = new AdapterOfFeed(context, d);
        activity.binding.feedListview.setAdapter(Adapter);
    }
}
