package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public interface DashBoardIn {
    interface View {
        void setSelectPet(PetAllInfos selectPet);
        void setMealHistory(MealHistory mealHistoryCommonResponce);
    }
    interface Presenter {
        void initData(Context context);
        void getTodaySumCalorie( String date );
    }
}
