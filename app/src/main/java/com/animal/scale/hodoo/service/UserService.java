package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService{

    @POST("user/regist")
    Call<ResultMessageGroup> registUser(@Body User user);

    @POST("user/login2")
    Call<ResultMessageGroup> login(@Body User user);

    @POST("user/get")
    Call<User> get(@Query("userIdx") int userIdx);

    @POST("user/update/basic/info")
    Call<Integer> updateBasicInfo(@Body User user);

    @POST("user/get/group/member")
    Call<List<User>> getGroupMemner(@Query("groupCode") String groupCode);

}
