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

    @POST("history/meal/insert.do")
    Call<Integer> insert(@Body MealHistory mealHistory);


    @POST("history/meal/get/list.do")
    Call<List<MealHistoryContent>> getList(@Query("date") String date, @Query("petIdx") int petIdx);


    @POST("history/meal/get/today/sum/calorie.do")
    Call<MealHistory> getTodaySumCalorie(@Query("petIdx") int petIdx, @Query("date") String date,  @Query("language") String language);


    @POST("history/meal/get/this/history.do")
    Call<MealHistory> getThisHistory(@Query("historyIdx") int historyIdx);

    @POST("history/meal/update.do")
    Call<Integer> update(@Body MealHistory mealHistory);


    @POST("history/meal/delete.do")
    Call<Integer> deleteMealHistory(@Query("historyIdx") int historyIdx);
}
