package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.MealHistory;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public class DashBoardPresenter implements DashBoardIn.Presenter {
    private DashBoardIn.View view;
    private DashBoardModel model;
    DashBoardPresenter ( DashBoardIn.View view ) {
        this.view = view;
    }

    @Override
    public void initData( Context context ) {
        model = new DashBoardModel();
        model.loadData( context );
    }

    @Override
    public void getTodaySumCalorie(String date) {
        model.getTodaySumCalorie(date, new CommonModel.ObjectCallBackListner<CommonResponce<MealHistory>>() {
            @Override
            public void doPostExecute(CommonResponce<MealHistory> mealHistoryCommonResponce) {
                view.setMealHistory(mealHistoryCommonResponce.domain);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
