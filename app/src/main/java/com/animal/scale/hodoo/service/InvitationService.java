package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Groups;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvitationService {
    @POST("fcm/mobile/send/invitation")
    Call<Integer> sendInvitation(@Query("toUserEmail") String toUserEmail, @Query("fromUserEmail") String fromUserEmail);
}
