package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface TwitterService {
    @GET("1.1/statuses/user_timeline.json")
    Call<List<TweetConvert>> listMessages();

}
