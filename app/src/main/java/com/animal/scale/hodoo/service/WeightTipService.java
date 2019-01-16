package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.domain.WeightTip;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  WeightTipService {

    @POST("tip/get/county/message")
    Call<WeightTip> getWeightTipOfCountry(@Body WeightTip weightTip);
}
