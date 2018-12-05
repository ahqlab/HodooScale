package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.Statistics;

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

    @POST("weight/get/last/collection/data")
    Call<RealTimeWeight> getLastCollectionData(@Query("date") String date, @Query("groupCode") String groupCode);


    @POST("weight/get/statistics/list/of/time")
    Call<List<Statistics>> getStatisticsOfTime(@Query("groupCode") String groupCode, @Query("today") String today);

    @POST("weight/get/statistics/list/of/day")
    Call<List<Statistics>> getStatisticsOfDay(@Query("groupCode") String groupCode);

    @POST("weight/get/statistics/list/of/week")
    Call<List<Statistics>> getStatisticsOfWeek(@Query("groupCode") String groupCode, @Query("month") String month);

    @POST("weight/get/statistics/list/of/month")
    Call<List<Statistics>> getStatisticsOfMonth(@Query("groupCode") String groupCode, @Query("year") String month);

    @POST("weight/get/statistics/list/of/year")
    Call<List<Statistics>> getStatisticsOfYear(@Query("groupCode") String groupCode);
}
