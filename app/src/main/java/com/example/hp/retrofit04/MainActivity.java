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

    static TwitterAuth twitterAuth = new TwitterAuth(UserData.cKey, UserData.cSecret, UserData.aToken, UserData.aTokenSecret);

    boolean isAuthenicated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Authorized  Sign in with Twitter
        if (!isAuthenicated) {
        /*        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserData.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            OauthService messages = retrofit.create(OauthService.class);
            Log.d(TAG, "TwitterAuth getToken: ");

            Call<List<AuthConvert>> call = messages.getAuthToken();
            AsyncTask authnetworkCall = new AuthNetworkCall().execute(call);
        */
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
                Log.d(TAG, "TwitterAuth getHeader: "+ twitterAuth.getHeader());

                Call<List<TweetConvert>> call = messages.listMessages("HromadskeUA");
                AsyncTask networkCall = new NetworkCall().execute(call);
            }
        });
    }


    private class NetworkCall extends AsyncTask<Call, Void, String> {
        Button btnFetch = (Button) findViewById(R.id.btnFetch);

        @Override
        protected void onPreExecute(){
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
    protected void onPreExecute(){
        btnFetch.setText("sing...");
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