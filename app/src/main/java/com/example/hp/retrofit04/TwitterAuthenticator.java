package com.example.hp.retrofit04;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


import com.example.hp.retrofit04.UserData;
import com.google.gson.Gson;



public class TwitterAuthenticator implements okhttp3.Authenticator {

    final static String TAG= "TweetAuth class";

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        String userRefreshToken="725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
        String cid=UserData.CID;
        String csecret=UserData.CSECRET;
        String baseUrl=UserData.BASE_URL;

        boolean refreshResult = refreshToken(baseUrl,userRefreshToken,cid,csecret);
        if(refreshResult)
        {
            //refresh is successful
            String newaccess="your new access";
            return response.request().newBuilder()
                    .header("Authorization", newaccess)
                    .build();
        }
        else {
            Log.d(TAG, "");
            return null;
        }
    }

    public boolean refreshToken(String url, String refresh, String cid, String csecret) throws IOException
    {
        URL refreshUrl=new URL(url+"token");
        HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setUseCaches(false);
        String urlParameters  = "grant_type=refresh_token&client_id="+cid+"&client_secret="+csecret+"&refresh_token="+refresh;
        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = urlConnection.getResponseCode();
        Log.d(TAG, "Post parameters : " + urlParameters);
        Log.d(TAG, "Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // handle response like retrofit, put response to POJO class by using Gson
        // you can find RefreshTokenResult class in my question

        Gson gson = new Gson();
        RefreshTokenResult refreshTokenResult=gson.fromJson(response.toString(),RefreshTokenResult.class);
        if(responseCode==200)
        {
            //handle new token ...
            return true;
        }
        //cannot refresh
        Log.v("refreshtoken", "cannot refresh , response code : "+responseCode);
        return false;
    }
}
