package com.example.hp.retrofit04;
/**
 * Class purpose to user_timeline - request
 * (https://dev.twitter.com/rest/reference/get/statuses/user_timeline)
 * for user HromadskeUA
 */

import com.example.hp.retrofit04.ServiceAPI.UserData;

import java.util.Random;

import oauth.signpost.OAuth;

import static com.example.hp.retrofit04.ServiceAPI.SignatureGenarator.generateSignature;


public class TwitterConnector {
    private String token = "725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
    private String nonce = updateNonce();
    private String signature = updateSignature(true);
    private String HMAC_SHA1 = "HMAC-SHA1";
    private String timeStamp = updateTimeStamp();
    private String version = "1.0";

    private String userName = "HromadskeUA";

    private String authHeader = "";


    public TwitterConnector(String userName) {
        if ((userName != "") || (userName != null)) {
            this.userName = userName;
        }
        updateAllParams();
    }


    public void updateAllParams() {
        this.nonce = updateNonce();
        this.signature = updateSignature(true);
        this.timeStamp = updateTimeStamp();
        this.authHeader = "OAuth " +
                "oauth_consumer_key=\"" + UserData.cKey + "\"," +
                "oauth_nonce=\"" + nonce + "\"," +
                "oauth_signature=\"" + signature + "\"," +
                "oauth_signature_method=\"" + HMAC_SHA1 + "\"," +
                "oauth_timestamp=\"" + timeStamp + "\"," +
                "oauth_token=\"" + token + "\"," +
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

    public String getUserName() {
        return userName;
    }

    private String updateSignature(boolean isEncoded) {
        String result;
        String singatureBaseUrl = "GET" + "&" +
                OAuth.percentEncode(UserData.BASE_URL +
                        UserData.REQUEST_USER_TIMELINE) + "&" +
                "oauth_consumer_key" + OAuth.percentEncode("=" + UserData.cKey + "&") +
                "oauth_nonce" + OAuth.percentEncode("=" + nonce + "&") +
                "oauth_signature_method%3DHMAC-SHA1%26o" +
                "auth_timestamp" + OAuth.percentEncode("=" + timeStamp + "&") +
                "oauth_token" + OAuth.percentEncode("=" + UserData.aToken + "&") +
                "oauth_version" + OAuth.percentEncode("=" + "1.0" + "&") +
                OAuth.percentEncode("screen_name=" + userName);
        if (isEncoded) {
            result = OAuth.percentEncode(generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret));
        } else {
            result = generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret);
        }
        return result;
    }

}
