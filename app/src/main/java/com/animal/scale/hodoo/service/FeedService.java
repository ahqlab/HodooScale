package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.SearchParam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FeedService {

    @POST("feed/all/list")
    Call<List<AutoCompleateFeed>> getAllFeedList();

    @POST("feed/search/list")
    Call<List<AutoCompleateFeed>> getSearchFeedList(@Body SearchParam searchParam);

    @POST("feed/get/info")
    Call<Feed> getFeedInfo(@Query("feedId") int feedId);

    @POST("feed/insert")
    Call<Integer> insertFeed(@Body MealHistory mealHistory);


    @POST("feed/get/radar/chart/data")
    Call<Feed> getRadarChartData(@Query("date") String date, @Query("petIdx") int petIdx);
}
