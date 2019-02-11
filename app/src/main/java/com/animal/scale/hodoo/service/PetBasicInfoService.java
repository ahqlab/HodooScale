package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetBasicInfoService {

    @POST("pet/basic/get.do")
    Call<PetBasicInfo> getBasicInfo(@Query("userId") int userId);

    @POST("pet/basic/info/check.do")
    Call<PetBasicInfo> getBasicInfoForPetId(@Query("groupId") String groupId, @Query("id") int id);

    @POST("pet/my/list.do")
    Call<List<PetBasicInfo>> getMyPetList(@Query("groupCode") String groupCode);

    @POST("pet/my/registered/list.do")
    Call<List<PetBasicInfo>> getMyRegisteredPetList(@Query("groupId") String groupId);

    @POST("pet/about/my/pet/list.do")
    Call<List<PetAllInfos>> aboutMyPetList(@Query("groupCode") String groupCode);
}

