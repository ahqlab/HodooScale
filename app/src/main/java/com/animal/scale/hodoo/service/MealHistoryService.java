package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Groups;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MealHistoryService {

    @POST("history/meal/insert")
    Call<Integer> insert(@Body MealHistory mealHistory);


    @POST("history/meal/get/list")
    Call<List<MealHistoryContent>> getList(@Query("date") String date, @Query("petIdx") int petIdx);


    @POST("history/meal/get/today/sum/calorie")
    Call<MealHistory> getTodaySumCalorie(@Query("petIdx") int petIdx, @Query("date") String date);


    @POST("history/meal/get/this/history")
    Call<MealHistory> getThisHistory(@Query("historyIdx") int historyIdx);

    @POST("history/meal/update")
    Call<Integer> update(@Body MealHistory mealHistory);


    @POST("history/meal/delete")
    Call<Integer> deleteMealHistory(@Query("historyIdx") int historyIdx);
}
