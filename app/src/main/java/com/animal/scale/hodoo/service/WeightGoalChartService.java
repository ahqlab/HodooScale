package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.HodooIndex;
import com.animal.scale.hodoo.domain.Weatherbit;
import com.animal.scale.hodoo.domain.WeightGoalChart;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeightGoalChartService {

    @POST("/android/pet/get/weight/standard/and/hodoo/index.do")
    Call<CommonResponce<HodooIndex>> getWeightGoal(@Query("petIdx") int petIdx, @Query("location") String location);
}
