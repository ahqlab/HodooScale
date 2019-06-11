package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.WeightGoalChart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SongSeokwoo on 2019-04-23.
 */
public interface BodyFatRiskService {
    @POST("android/body/fat/chart/get/weight/goal.do")
    Call<CommonResponce<WeightGoalChart>> getWeightGoal(@Query("currentWeight") float currentWeight, @Query("bodyFat") int bodyFat, @Query("petType") int petType);
}
