package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService{

    @POST("user/regist")
    Call<ResultMessageGroup> registUser(@Body User user);

    @POST("user/login2")
    Call<CommonResponce<User>> login(@Body User user);

    @POST("user/get")
    Call<User> get(@Query("userIdx") int userIdx);

    @POST("user/update/basic/info")
    Call<Integer> updateBasicInfo(@Body User user);

    @POST("user/get/group/member")
    Call<List<User>> getGroupMemner(@Query("groupCode") String groupCode);

    @POST("user/update/user/password")
    Call<Integer> updateUsetPassword(@Body User user);

    @POST("mail/user/certified")
    Call<Integer> userCertifiedMailSend(@Query("toMailAddr") String toMailAddr);

    @POST("user/find/user/password")
    Call<CommonResponce<User>> findUserPassword(@Query("email") String groupCode);

    @POST("user/update/fcmToken")
    Call<Integer> saveFCMToken(@Body User user);

    @POST("user/invitation/approval")
    Call<Integer> invitationApproval(@Query("toUserIdx") int toUserIdx, @Query("fromUserIdx") int fromUserIdx);

    @POST("user/invitation/refusal")
    Call<Integer> invitationRefusal(@Query("toUserIdx") int toUserIdx, @Query("fromUserIdx") int fromUserIdx);

    @POST("user/setUserCode")
    Call<Integer> withdraw( @Query("idx") int idx, @Query("type") int type );

    @POST("user/withdrawGroup")
    Call<Integer> withdrawGroup( @Query("to") int to, @Query("from") int from );
}
