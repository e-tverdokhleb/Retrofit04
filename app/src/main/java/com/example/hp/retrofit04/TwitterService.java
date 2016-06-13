package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TwitterService {
/*
    @Headers({
              "Authorization: OAuth oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                    "oauth_nonce=\"ecadfe0f8b12b6cea334c624af2c0f30\","+
                    "oauth_signature=\"TaOEAdBfz0E8GcDZWhD3zAiqHkk%3D\"," +
                    "oauth_signature_method=\"HMAC-SHA1\"," +
                    "oauth_timestamp=\"1465770455\","+
                    "oauth_token=\"725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB\","+
                    "oauth_version=\"1.0\""
    })
  */

    @GET("1.1/statuses/user_timeline.json")
    Call<List<TweetConvert>> listMessages(@Query("screen_name") String screen_name);  // @Header("Authorization") String Authorization
}
