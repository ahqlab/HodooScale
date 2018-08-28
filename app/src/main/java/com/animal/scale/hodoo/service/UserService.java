package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService{

    @POST("user/regist")
    Call<ResultMessageGroup> registUser(@Body User user);


    @POST("user/login")
    Call<User> login(@Body User user);
}
