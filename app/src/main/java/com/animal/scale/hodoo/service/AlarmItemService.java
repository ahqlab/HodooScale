package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.AlarmItem;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AlarmItemService {

    @POST("android/alarm/list.do")
    Call<CommonResponce<List<AlarmItem>>> getAlarmList(@Query("language") String language);

    @POST("android/alarm/get/alarm.do")
    Call<CommonResponce<Integer>> getAlarm(@Query("userIdx") int userIdx);

    @POST("android/alarm/save/alarm.do")
    Call<CommonResponce<Integer>> saveAlarm(@Query("userIdx") int userIdx, @Query("number") int number);
}
