package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.Statistics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RealTimeWeightService {

    @POST("pet/basic/get.do")
    Call<PetBasicInfo> getBasicInfo(@Query("userId") int userId);

    @POST("pet/my/list.do")
    Call<List<String>> getMyPetList(@Query("groupId") String groupId);

    @POST("weight/get/lately/data.do")
    Call<Float> getLatelyData(@Query("mac") String mac);

    @POST("weight/get/last/collection/data.do")
    Call<RealTimeWeight> getLastCollectionData(@Query("date") String date, @Query("groupCode") String groupCode, @Query("type") int type, @Query("petIdx") int petIdx);

    @POST("weight/get/statistics/list/of/time.do")
    Call<List<Statistics>> getStatisticsOfTime(@Query("groupCode") String groupCode, @Query("today") String today, @Query("type") int type, @Query("petIdx") int petIdx);

    //USE
    @POST("weight/get/statistics/list/of/day.do")
    Call<List<Statistics>> getStatisticsOfDay(@Query("groupCode") String groupCode, @Query("type") int type, @Query("date") String date, @Query("petIdx") int petIdx);

    //USE
    @POST("weight/get/statistics/list/of/week.do")
    Call<List<Statistics>> getStatisticsOfWeek(@Query("groupCode") String groupCode, @Query("year") String year ,@Query("month") String month, @Query("type") int type, @Query("petIdx") int petIdx);

    //USE
    @POST("weight/get/statistics/list/of/month.do")
    Call<List<Statistics>> getStatisticsOfMonth(@Query("groupCode") String groupCode, @Query("year") String year, @Query("month") String month, @Query("type") int type, @Query("petIdx") int petIdx);

    /*@POST("weight/get/statistics/list/of/year.do")
    Call<List<Statistics>> getStatisticsOfYear(@Query("groupCode") String groupCode, @Query("type") int type, @Query("petIdx") int petIdx);*/
}
