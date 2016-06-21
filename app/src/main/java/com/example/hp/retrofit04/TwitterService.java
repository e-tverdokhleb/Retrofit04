package com.example.hp.retrofit04;

import com.example.hp.retrofit04.ServiceAPI.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TwitterService {
    @GET(UserData.REQUEST_USER_TIMELINE)
    Call<List<TweetConvert>> listMessages(@Query("screen_name") String screen_name);

    @FormUrlEncoded
    @POST("1.1/statuses/update.json")
    Call<List<TweetConvert>> updateStatus(@Field("status") String status);

}
