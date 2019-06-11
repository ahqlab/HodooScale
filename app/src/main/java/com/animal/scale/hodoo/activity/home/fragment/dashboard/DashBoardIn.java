package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.WeightGoalChart;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public interface DashBoardIn {
    interface View {
        void setSelectPet(PetAllInfos selectPet);
        void setMealHistory(MealHistory mealHistoryCommonResponce);
        void physicalUpdateDone( PetPhysicalInfo result );
        void setDevice(List<Device> devices);
        void setPetAllInfos( PetAllInfos petAllInfos );
        void setWeightGoal(WeightGoalChart weightGoal);
    }
    interface Presenter {
        void initData(Context context);
        void getTodaySumCalorie( String date );
        void updatePhysical(PetPhysicalInfo info);
        void getDevice();
        void getGoalWeight( float currentWeight, int bodyFat, int petType );

        void setAverageWeight( String weight );
        void getPetAllInfos( int petIdx );
        String getTodayAverageWeight();
    }
}
