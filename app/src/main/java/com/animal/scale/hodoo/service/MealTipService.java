package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.MealTip;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MealTipService {

    @POST("tip/meal/get/county/message.do")
    Call<MealTip> getTipOfCountry(@Body MealTip mealTip);
}
