package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Groups;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupsService {

    @POST("groups/get")
    Call<Groups> get(@Body Groups groups);

    @POST("groups/is/regist")
    Call<Groups> isRegist(@Body Groups groups);


}
