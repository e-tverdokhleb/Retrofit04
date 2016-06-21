package com.example.hp.retrofit04.Auth;
/**
 * Class purpose to create get/token - request
 * ( https://dev.twitter.com/web/sign-in/implementing)
 * 1 step - obtaining a requesst token
 */

import com.example.hp.retrofit04.ServiceAPI.UserData;

import java.util.Random;

import oauth.signpost.OAuth;

import static com.example.hp.retrofit04.ServiceAPI.SignatureGenarator.generateSignature;

public class TwitterConnectorAuth {
    private String consumer_key = "OewqCxpycFUv0SD2ia1dqFWA1";
    private String nonce = updateNonce();
    private String signature = updateSignature(true);
    private String HMAC_SHA1 = "HMAC-SHA1";
    private String timeStamp = updateTimeStamp();
    private String version = "1.0";

    private String authHeader = "";


    public TwitterConnectorAuth() {
        updateAllParams();
    }

    private void updateAllParams() {
        this.nonce = updateNonce();
        this.signature = updateSignature(true);
        this.timeStamp = updateTimeStamp();
        this.authHeader = "OAuth " +
                "oauth_callback=\"http%3A%2F%2Flocalhost.com%2Fretrofit\"," +
                "oauth_consumer_key=\"" + consumer_key + "\"," +
                "oauth_nonce=\"" + nonce + "\"," +
                "oauth_signature=\"" + signature + "\"," +
                "oauth_signature_method=\"" + HMAC_SHA1 + "\"," +
                "oauth_timestamp=\"" + timeStamp + "\"," +
                "oauth_version=\"" + version + "\"";
    }

    public String getHeader() {

        return authHeader;
    }

    public String updateTimeStamp() {
        timeStamp = String.valueOf(Long.valueOf(System.currentTimeMillis() / 1000));
        return timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String updateNonce() {
        char abc[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String baseNonce = "00000000000000000000000000000000";

        Random rnd = new Random();
        char[] newKey = baseNonce.toCharArray();
        int num;
        for (int i = 0; i < newKey.length; i++) {
            newKey[i] = abc[rnd.nextInt(abc.length)];
        }

        // nonce = String.valueOf(newKey);
        // update();
        return String.valueOf(newKey);
    }

    public String getNonce() {
        return nonce;
    }

    private String updateSignature(boolean isEncoded) {
        String result;
        String singatureBaseUrl = "POST" + "&" +
                OAuth.percentEncode(UserData.BASE_URL + "oauth/request_token") + "&" +
                "oauth_callback" + OAuth.percentEncode("=" + "http://localhost.com/retrofit" + "&") +
                "oauth_consumer_key" + OAuth.percentEncode("=" + UserData.cKey + "&") +
                "oauth_nonce" + OAuth.percentEncode("=" + nonce + "&") +
                "oauth_signature_method%3DHMAC-SHA1%26o" +
                "oauth_timestamp" + OAuth.percentEncode("=" + timeStamp + "&") +
                "oauth_version%3D1.0";
        if (isEncoded) {
            result = OAuth.percentEncode(generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret));
        } else {
            result = generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret);
        }
        return result;
    }
}
