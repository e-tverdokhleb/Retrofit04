package com.example.hp.retrofit04.Auth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface OAuthServiceInterface {
    @POST("oauth/request_token")
    Call<List<OAuthDataConvertor>> getAuthToken();

}

