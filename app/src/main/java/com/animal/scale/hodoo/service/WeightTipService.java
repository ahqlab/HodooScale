package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.domain.WeightTip;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  WeightTipService {

    @POST("tip/get/county/message.do")
    Call<WeightTip> getWeightTipOfCountry(@Body WeightTip weightTip);

    /*사람의 경우 한번 과체중이 되어 본 적이 있으면 다이어트를 하더라도 다시 살이 찔 가능성이 높은 것처럼 반려동물 역시 다시 살이 찔 가능성이 큽니다.
    다이어트에 성공한 후 바로 자유 급식을 시작하면 비만이 다시 찾아올 가능성이 크기 때문에 평소에 체중을 자주 확인해주는 것이 좋습니다.*/
}
