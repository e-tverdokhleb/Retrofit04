package com.example.hp.retrofit04.Auth;

import com.example.hp.retrofit04.Auth.AuthConvert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OauthService {
    @Headers({
            "Authorization: " +
                    "OAuth " +
                    "oauth_callback=\"http%3A%2F%2Flocalhost.com%2Fretrofit\"," +
                    "oauth_consumer_key=\"OewqCxpycFUv0SD2ia1dqFWA1\"," +
                    "oauth_nonce=\"tw9lh74tbf6ozzjptps0wq3s1rzfutn2\"," +
                    "oauth_signature=\"NGArpAy1yByMINQxblIBg6p2ZkM%3D\"," +
                    "oauth_signature_method=\"HMAC-SHA1\"," +
                    "oauth_timestamp=\"1466493269\"," +
                    "oauth_version=\"1.0\""
    })

    @POST("oauth/request_token")
    Call<List<AuthConvert>> getAuthToken();   //@FieldMap Map<String,String> map
}