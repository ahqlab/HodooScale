package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Groups;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupsService {

    @POST("groups/get.do")
    Call<Groups> get(@Body Groups groups);

    @POST("groups/get/list.do")
    Call<List<Groups>> getGroupLists(@Body Groups groups);

    @POST("groups/is/regist.do")
    Call<Groups> isRegist(@Body Groups groups);


}
