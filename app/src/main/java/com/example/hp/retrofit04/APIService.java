package com.example.hp.retrofit04;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.example.hp.retrofit04.RefreshTokenResult;

public interface APIService {
    @FormUrlEncoded
    @POST("token")
    public Call<RefreshTokenResult> refreshUserToken(@Header("Accept") String accept, @Header("Content-Type") String contentType, @Field("grant_type") String grantType,
                                                     @Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("refresh_token") String refreshToken);
}
