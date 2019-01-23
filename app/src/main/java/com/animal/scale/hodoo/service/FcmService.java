package com.animal.scale.hodoo.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FcmService {
    @POST("fcm/mobile/send/invitation")
    Call<Integer> sendInvitation(@Query("toUserEmail") String toUserEmail, @Query("fromUserEmail") String fromUserEmail);

    @POST("fcm/mobile/send/normal")
    Call<Integer> normalPush(@Query("title") String title, @Query("content") String content, @Query("toUserEmail") String toUserEmail);

    @POST("user/invitation/cancelInvitation")
    Call<Integer> cancelInvitation(@Query("toUserEmail") String toUserEmail, @Query("from") int id);
}
