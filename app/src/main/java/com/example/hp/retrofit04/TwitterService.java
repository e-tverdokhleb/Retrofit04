package com.example.hp.retrofit04;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TwitterService {
    @Headers({
            "Authorization: OAuth oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                    "oauth_nonce=\"c06b8f006192142416d66c5f4bfe549e\","+
                    "oauth_signature=\"q5hRzxRd7ZeZMPAK%2BWNypkk0pIU%3D\"," +
                    "oauth_signature_method=\"HMAC-SHA1\"," +
                    "oauth_timestamp=\"1465486496\","+
                    "oauth_token=\"725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB\","+
                    "oauth_version=\"1.0\""

    })

    @GET("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name={screen_name}")   //?screen_name={screen_name}

    Call<List<TweetConvert>> listMessages(@Path("screen_name") String screen_name);
}
