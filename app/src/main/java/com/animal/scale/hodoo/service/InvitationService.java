package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvitationService {
    @POST("user/invitation/getInvitationUser")
    Call<List<InvitationUser>> getInvitationUser(@Query("userIdx") int userIdx );

    @POST("user/invitation/setInvitationType")
    Call<Integer> setInvitationType(@Query("type") int type, @Query("toUserIdx") int toUserIdx, @Query("fromUserIdx") int fromUserIdx );
}
