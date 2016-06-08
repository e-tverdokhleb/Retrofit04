package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TwitterService {
    @GET("1.1/statuses/home_timeline.json")
    // @GET("repos/square/retrofit/contributors")
    Call<List<TweetConvert>> listMessages();
}
