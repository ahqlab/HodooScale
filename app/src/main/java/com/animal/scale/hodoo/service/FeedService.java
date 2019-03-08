package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.SearchParam;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FeedService {

    @POST("feed/all/list.do")
    Call<List<AutoCompleateFeed>> getAllFeedList();

    @POST("feed/search/list.do")
    Call<List<AutoCompleateFeed>> getSearchFeedList(@Body SearchParam searchParam, @Query("language") String language);

    @POST("feed/get/info.do")
    Call<Feed> getFeedInfo(@Query("feedId") int feedId);

    @POST("feed/insert.do")
    Call<Integer> insertFeed(@Body MealHistory mealHistory);


    @POST("feed/get/radar/chart/data.do")
    Call<Feed> getRadarChartData(@Query("date") String date, @Query("petIdx") int petIdx);


    @GET("feed/tetest.do")
    Call<User> testSubmit();
}
