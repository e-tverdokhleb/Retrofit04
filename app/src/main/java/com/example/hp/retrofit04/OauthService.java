package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OauthService {

    @Headers({
              "Authorization: " +
                "OAuth " +
                      "oauth_nonce=\"9fa6a89237b57afb72da535dfe46e8d9\"," +
                      "oauth_callback=\"http%3A%2F%2Flocalhost.com%2Fretrofit%2F\"," +
                      "oauth_signature_method=\"HMAC-SHA1\"," +
                      "oauth_timestamp=\"1465961584\"," +
                      "oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                      "oauth_signature=\"ngCF14RkdhD%2Fj2PoYJA9c%2FdGI50%3D\"," +
                      "oauth_version=\"1.0\""
            })

    @POST("oauth/request_token")
    Call<List<AuthConvert>> getAuthToken();
}

