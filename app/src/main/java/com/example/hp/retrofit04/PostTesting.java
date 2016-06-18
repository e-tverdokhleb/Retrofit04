package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostTesting {

    @GET("http://api.github.com/repos/square/retrofit/contributors")
    Call<List<GitHubConvertor>> getData();
}
