package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService{

    @POST("android/user/regist.do")
    Call<CommonResponce<User>> registUser(@Body User user);

    @POST("user/login2.do")
    Call<CommonResponce<User>> login(@Body User user);

    @POST("user/get.do")
    Call<User> get(@Query("userIdx") int userIdx);

    @POST("user/update/basic/info.do")
    Call<Integer> updateBasicInfo(@Body User user);

    @POST("user/get/group/member.do")
    Call<List<User>> getGroupMemner(@Query("groupCode") String groupCode);

    @POST("user/update/user/password.do")
    Call<Integer> updateUsetPassword(@Body User user);

    @POST("mail/user/certified.do")
    Call<Integer> userCertifiedMailSend(@Query("toMailAddr") String toMailAddr);

    @POST("user/find/user/password.do")
    Call<CommonResponce<User>> findUserPassword(@Query("email") String groupCode);

    @POST("user/update/fcmToken.do")
    Call<Integer> saveFCMToken(@Body User user);

    @POST("user/invitation/approval.do")
    Call<Integer> invitationApproval(@Query("toUserIdx") int toUserIdx, @Query("fromUserIdx") int fromUserIdx);

    @POST("user/invitation/refusal.do")
    Call<Integer> invitationRefusal(@Query("toUserIdx") int toUserIdx, @Query("fromUserIdx") int fromUserIdx);

    @POST("user/setUserCode.do")
    Call<Integer> withdraw( @Query("idx") int idx, @Query("type") int type );

    @POST("user/withdrawGroup.do")
    Call<Integer> withdrawGroup( @Query("to") int to, @Query("from") int from );

    @POST("user/checkGroupCount.do")
    Call<Integer> checkGroupCount( @Query("idx") int idx );

    @POST("android/user/sns/regist.do")
    Call<CommonResponce<User>> doSnsRegist(@Body User user);

    @POST("android/user/sns/login.do")
    Call<CommonResponce<User>> snsLogin(@Body User user);
}
