package com.example.hp.retrofit04;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private final static String TAG ="MainActivityTAG";
    public static final String BASE_URL = "https://api.twitter.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFetch = (Button) findViewById(R.id.btnFetch);

        btnFetch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request.Builder ongoing = chain.request().newBuilder();
                                    ongoing.addHeader("Accept", "application/json")
                                            .addHeader("Authorization", "Bearer 725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB");
                                    return chain.proceed(ongoing.build());
                                }
                            }).build();

                Log.d(TAG, "httpClient: " + httpClient.authenticator().toString());


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Log.d(TAG, "retrofit: " + String.valueOf(retrofit));


                TwitterService messages = retrofit.create(TwitterService.class);
                Log.d(TAG, "messages: " + messages.toString());

                Call<List<TweetConvert>> call = messages.listMessages();
                Log.d(TAG, "call: " + call.toString());

                new NetworkCall().execute(call);
            }
        });
    }


    private class NetworkCall extends AsyncTask<Call, Void, String> {
        @Override
        protected String doInBackground(Call... params) {
            try {
                return String.valueOf(params[0].execute().body());
            } catch (IOException e) {
                e.printStackTrace();
           }

            return "No data has Fetched";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView;
            textView = (TextView) findViewById(R.id.tvMessages);
            textView.setText(result);
        }
    }
}