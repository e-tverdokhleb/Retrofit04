package com.example.hp.retrofit04;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivityTAG";

    static TwitterAuth twitterAuth = new TwitterAuth(UserData.cKey, UserData.cSecret, UserData.aToken, UserData.aTokenSecret);

    boolean isAuthenicated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Authorized  Sign in with Twitter
        if (!isAuthenicated) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder()
                                    .header("Authorization", twitterAuth.getGetTokenHeader());
                            return chain.proceed(ongoing.build());
                        }
                    }).build();

            Log.d(TAG, "getTokenHeader: " + twitterAuth.getGetTokenHeader());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UserData.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                           /* Map<String, String> map = new HashMap<String, String>();
                            map.put("oauth_callback","http%3A%2F%2Flocalhost.com%2Fretrofit");
                            map.put("oauth_consumer_key","OewqCxpycFUv0SD2ia1dqFWA1");
                            map.put("oauth_nonce","08127e81208b7ceeea754ed1114a1bd4");
                            map.put("oauth_signature",String.valueOf(twitterAuth.getGetTokenHeader()));
                                 Log.d(TAG,"TwitterAuth getToken" +String.valueOf(twitterAuth.getGetTokenHeader()));
                            map.put("oauth_signature_method","HMAC_SHA1");
                            map.put("oauth_timestamp",twitterAuth.getTimeStamp());
                            map.put("oauth_version","1.0"); */

            OauthService messages = retrofit.create(OauthService.class);
            Log.d(TAG, "TwitterAuth getToken: " + twitterAuth.getGetTokenHeader());
            Call<List<AuthConvert>> call = messages.getAuthToken();

            AsyncTask authnetworkCall = new AuthNetworkCall().execute(call);
            Log.d(TAG, "Response:" + authnetworkCall.getStatus().toString());
        }

        Button btnFetch = (Button) findViewById(R.id.btnFetch);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request().newBuilder()
                                        .header("Authorization", twitterAuth.getHeader());
                                return chain.proceed(ongoing.build());
                            }
                        }).build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserData.BASE_URL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TwitterService messages = retrofit.create(TwitterService.class);
                Log.d(TAG, "TwitterAuth getHeader: " + twitterAuth.getHeader());

                Call<List<TweetConvert>> call = messages.listMessages("HromadskeUA");
                //Call<List<TweetConvert>> call = messages.updateStatus("HelloTwitter");
                    /*  call.enqueue(new Callback<List<TweetConvert>>() {
                            @Override
                            public void onResponse(Call<List<TweetConvert>> call, retrofit2.Response<List<TweetConvert>> response) {
                              //  Log.d(TAG, "Responce: " + response.message());
                                Log.d(TAG, "Responce: ");
                            }
                            @Override
                            public void onFailure(Call<List<TweetConvert>> call, Throwable t) {
                                Log.d(TAG, "Responce: ");
                            }
                        });  */

                AsyncTask networkCall = new NetworkCall().execute(call);

                /*
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request().newBuilder()
                                        .header("Authorization", twitterAuth.getHeader());
                                return chain.proceed(ongoing.build());
                            }
                        }).build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        //   .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostTesting messages = retrofit.create(PostTesting.class);

                Call<List<GitHubConvertor>> call = messages.getData();
                AsyncTask networkCall = new NetworkCall().execute(call);
        */
            }
        });
    }


    private class NetworkCall extends AsyncTask<Call, Void, String> {
        Button btnFetch = (Button) findViewById(R.id.btnFetch);

        @Override
        protected void onPreExecute() {
            btnFetch.setText("rereshing...");
        }

        @Override
        protected String doInBackground(Call... params) {
            try {
                String result = String.valueOf(params[0].execute().body());
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "No data has Fetched";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.tvMessages);
            textView.setText(result);
            btnFetch.setText("reresh");
        }
    }

    private class AuthNetworkCall extends AsyncTask<Call, Void, String> {
        Button btnFetch = (Button) findViewById(R.id.btnFetch);

        @Override
        protected void onPreExecute() {
            btnFetch.setText("sign...");
        }

        @Override
        protected String doInBackground(Call... params) {
            try {
                String result = String.valueOf(params[0].execute().body());
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "no result";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.tvMessages);
            textView.setText(result);
            btnFetch.setText("reresh");
        }
    }
}