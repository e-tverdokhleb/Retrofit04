package com.example.hp.retrofit04;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.retrofit04.Auth.OAuthDataConvertor;
import com.example.hp.retrofit04.Auth.OAuthServiceInterface;
import com.example.hp.retrofit04.Auth.ServiceAuthenticate;
import com.example.hp.retrofit04.Requests.GetUserTimeLine;
import com.example.hp.retrofit04.Requests.TweetConvert;
import com.example.hp.retrofit04.Requests.TwitterService;
import com.example.hp.retrofit04.ServiceAPI.UserData;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivityTAG";

    ServiceAuthenticate twitterAuth = new ServiceAuthenticate();
    GetUserTimeLine twitterGetPosts = new GetUserTimeLine("Nashkiev");

    boolean isAuthenicated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Authorized  Sign in with Twitter
        if (!isAuthenicated) {
        }

        Button btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request().newBuilder()
                                        .addHeader("Authorization", twitterAuth.getHeader());
                                return chain.proceed(ongoing.build());
                            }
                        }).build();

                Log.d(TAG, "getHeader: " + twitterAuth.getHeader());

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(UserData.BASE_URL)
                        .client(httpClient)
                        .build();

                OAuthServiceInterface messages = retrofit.create(OAuthServiceInterface.class);
                Call<List<OAuthDataConvertor>> call = messages.getAuthToken();

                AsyncTask authnetworkCall = new AuthNetworkCall().execute(call);
            }
        });


        Button btnFetch = (Button) findViewById(R.id.btnFetch);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request().newBuilder()
                                        .header("Authorization", twitterGetPosts.getHeader());
                                return chain.proceed(ongoing.build());
                            }
                        }).build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserData.BASE_URL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TwitterService messages = retrofit.create(TwitterService.class);
                Log.d(TAG, "GetUserTimeLine getHeader: " + twitterGetPosts.getHeader());
                Call<List<TweetConvert>> call = messages.listMessages("Nashkiev");

                //   Call<List<TweetConvert>> call = messages.updateStatus("HelloTwitter");
                AsyncTask networkCall = new NetworkCall().execute(call);

            }
        });
    }

    private class AuthNetworkCall extends AsyncTask<Call, Void, String> {
        Button btnSign = (Button) findViewById(R.id.btnSign);

        @Override
        protected void onPreExecute() {
            btnSign.setText("sign...");
        }

        @Override
        protected String doInBackground(Call... params) {
            try {
                Log.d(TAG, "Server request         : " + (params[0].request().toString()));
                Log.d(TAG, "Server request headers : " + (params[0].request().headers().toString()));
                Log.d(TAG, "Server request isHttps : " + (params[0].request().isHttps()));
                Log.d(TAG, "Server request body    : " + String.valueOf(params[0].request().body()));
                retrofit2.Response response = params[0].execute();
                Log.d(TAG, "Server response: " + String.valueOf(response.code()));
                Log.d(TAG, "Server response: " + String.valueOf(response.headers().toString()));
                Log.d(TAG, "Server response: " + String.valueOf(response.errorBody().string()));
                return String.valueOf(response.body());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage());
            }
            return "check Internet connection";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.tvMessages);
            textView.setText(result);
            btnSign.setText("sing");
        }
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
                Log.d(TAG, "Server request: " + (params[0].request().toString()));
                //  Log.d(TAG, "Server request headers: "+ (params[0].request().headers().toString()));
                // Log.d(TAG, "Server request body length: "+ (params[0].request().body().contentLength()));
                retrofit2.Response response = params[0].execute();
                Log.d(TAG, "Server response: " + String.valueOf(response.code()));
                Log.d(TAG, "Server response: " + String.valueOf(response.headers().toString()));
                Log.d(TAG, "Server response: " + String.valueOf(response.body()));
                return String.valueOf(response.body());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage());
            }
            return "check Internet connection";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.tvMessages);
            textView.setText(result);
            btnFetch.setText("reresh");
        }
    }


}