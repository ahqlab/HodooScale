package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RealTimeWeightService {

    @POST("pet/basic/get")
    Call<PetBasicInfo> getBasicInfo(@Query("userId") int userId);

    @POST("pet/my/list")
    Call<List<String>> getMyPetList(@Query("groupId") String groupId);

    @POST("weight/get/lately/data")
    Call<Float> getLatelyData(@Query("mac") String mac);

    @POST("weight/get/list/of/group")
    Call<List<Float>> getRealTimeList(@Query("mac") String mac);
}
