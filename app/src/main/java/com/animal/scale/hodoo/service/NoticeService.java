package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Notice;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NoticeService {

    @POST("pet/basic/get.do")
    Call<PetBasicInfo> getBasicInfo(@Query("userId") int userId);

    @POST("android/notice/list.do")
    Call<CommonResponce<List<Notice>>> getNoticeList(@Query("startRow") int startRow, @Query("pageSize") int pageSize , @Query("language") String language);
}
