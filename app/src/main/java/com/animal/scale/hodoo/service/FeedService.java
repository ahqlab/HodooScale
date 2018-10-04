package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Feed;
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
}
