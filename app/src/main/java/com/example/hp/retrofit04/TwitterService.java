package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TwitterService {
    @GET("1.1/statuses/user_timeline.json")
    Call<List<TweetConvert>> listMessages(@Query("screen_name") String screen_name);
}
