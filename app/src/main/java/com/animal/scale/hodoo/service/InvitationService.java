package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvitationService {
    @POST("fcm/mobile/send/invitation")
    Call<Integer> sendInvitation(@Query("toUserEmail") String toUserEmail, @Query("fromUserEmail") String fromUserEmail);

    @POST("user/invitation/getInvitationUser")
    Call<List<InvitationUser>> getInvitationUser(@Query("userIdx") int userIdx );
}
