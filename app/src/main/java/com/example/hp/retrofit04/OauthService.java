package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OauthService {

    /*   @Headers({
              "Authorization: " +
                "OAuth " +
                      "oauth_nonce=\"28747818aa0f73c383351c21134178d8\"," +
                      "oauth_callback=\"http%3A%2F%2Flocalhost.com%2Fretrofit%2F\"," +
                      "oauth_signature_method=\"HMAC-SHA1\"," +
                      "oauth_timestamp=\"1465962713\"," +
                      "oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                      "oauth_signature=\"qsSRr3bYxmAH0u8jMSJQkjjCfVI%3D\"," +
                      "oauth_version=\"1.0\""
            })
    */
    @POST("/oauth/request_token")
    Call<List<AuthConvert>> getAuthToken();
}

