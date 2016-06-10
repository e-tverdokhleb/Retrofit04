package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitterService {
    @Headers({
            "Authorization: OAuth oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                    "oauth_nonce=\"cb934095898754fd0a5534f2a40ed16b\","+
                    "oauth_signature=\"Z9vCa8aF3LCi0I2JROPyT2D98tA%3D\"," +
                    "oauth_signature_method=\"HMAC-SHA1\"," +
                    "oauth_timestamp=\"1465567750\","+
                    "oauth_token=\"725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB\","+
                    "oauth_version=\"1.0\""
    })
    @GET("1.1/statuses/user_timeline.json")
    Call<List<TweetConvert>> listMessages(@Query("screen_name") String screen_name);  //("screen_name") String screen_name
}
