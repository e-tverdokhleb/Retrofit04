package com.example.hp.retrofit04;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.OAuthMessageSigner;
import retrofit2.Call;

import com.example.hp.retrofit04.TwitterService;

public class TwitterAuth {
    private static String cKey = "";
    private static String cSecret = "";
    private static String aToken = "";
    private static String aTokenSecret = "";

    final private String HMAC_SHA1 = "HMAC-SHA1";
    private String method = "GET";
    private String consumer_key = "OewqCxpycFUv0SD2ia1dqFWA1";
    private String nonce = "08127e81208b7ceeea754ed1114a1bd7";
    private String signature = "";   //  v%2FnBLJ2XoJZLdxHpHq0fQoNhplA%3D
    private String time_stamp = "1466129461";
    private String token = "725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
    private String version = "1.0";

    public static final String ENCODING = "UTF-8";

    private transient org.apache.commons.codec.binary.Base64 base64;

    private String authHeader;
    private String getTokenHeader;

    public TwitterAuth(String cKey, String cSecret, String aToken, String aTokenSecret) {
        this.cKey = cKey;
        this.cSecret = cSecret;
        this.aToken = aToken;
        this.aTokenSecret = aTokenSecret;

        this.getSignature(true);
        update();
    }

    public String getGetTokenHeader() {
        this.getTokenHeader = "OAuth " +
                "oauth_consumer_key=\"" + consumer_key + "\"," +
                "oauth_callback=\"http%3A%2F%2Flocalhost.com%2Fretrofit%2F\"," +
                "oauth_nonce=\"" + "08127e81208b7ceeea754ed1114a1bd2" + "\"," +
                "oauth_signature=\"" + getGetTokenSignature(true) + "\"," +
                "oauth_signature_method=\"" + HMAC_SHA1 + "\"," +
                "oauth_timestamp=\"" + time_stamp + "\"," +
                "oauth_version=\"" + version + "\"";
        ;
        return getTokenHeader;
    }

    private void update() {
        this.authHeader = "OAuth " +
                "oauth_consumer_key=\"" + consumer_key + "\"," +
                "oauth_nonce=\"" + nonce + "\"," +
                "oauth_signature=\"" + signature + "\"," +
                "oauth_signature_method=\"" + HMAC_SHA1 + "\"," +
                "oauth_timestamp=\"" + time_stamp + "\"," +
                "oauth_token=\"" + token + "\"," +
                "oauth_version=\"" + version + "\"";
    }


    public String getHeader() {
        return authHeader;
    }

    public String generateNonce() {
        char abc[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        Random rnd = new Random();
        char[] newKey = nonce.toCharArray();
        int num;
        for (int i = 0; i < newKey.length; i++) {
            newKey[i] = abc[rnd.nextInt(abc.length)];
        }

        nonce = String.valueOf(newKey);
        update();
        return nonce;
    }

    private String getSignature(boolean isEncoded) {
        String singatureBaseUrl = "GET" + "&" +
                OAuth.percentEncode(UserData.BASE_URL +                            //https%3A%2F%2Fapi.twitter.com%2F
                        UserData.REQUEST_USER_TIMELINE) + "&" +
                "oauth_consumer_key" + OAuth.percentEncode("=" + UserData.cKey + "&") +           //%3DOewqCxpycFUv0SD2ia1dqFWA1%26
                "oauth_nonce" + OAuth.percentEncode("=" + nonce + "&") +
                "oauth_signature_method%3DHMAC-SHA1%26o" +
                "auth_timestamp" + OAuth.percentEncode("=" + time_stamp + "&") +
                "oauth_token" + OAuth.percentEncode("=" + UserData.aToken + "&") +
                "oauth_version" + OAuth.percentEncode("=" + "1.0" + "&") +
                "screen_name%3DHromadskeUA";
        Log.d("TwitterAuth:","GetSignature:" + singatureBaseUrl);

        if (isEncoded) {
            signature = OAuth.percentEncode(generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret));
        } else {
            signature = generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret);
        }
        update();
        return signature;
    }

    private String getGetTokenSignature(boolean isEncoded) {
        String singatureBaseUrl = "POST" + "&" +
                OAuth.percentEncode(UserData.BASE_URL + "oauth/request_token") + "&" +
                "oauth_callback" + OAuth.percentEncode("=" + "http://localhost.com/retrofit/" + "&") +
                "oauth_consumer_key" + OAuth.percentEncode("=" + UserData.cKey + "&") +
                "oauth_nonce" + OAuth.percentEncode("=" + "08127e81208b7ceeea754ed1114a1bd2" + "&") +
                "oauth_signature_method%3DHMAC-SHA1%26o" +
                "auth_timestamp" + OAuth.percentEncode("=" + time_stamp + "&") +
                "oauth_version%3D1.0";

        Log.d("TwitterAuth:","GetTokenSignature :" + singatureBaseUrl);
        if (isEncoded) {
            signature = OAuth.percentEncode(generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret));
        } else {
            signature = generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret);
        }
        return signature;
    }


    private String generateSignature(String signatueBaseStr, String oAuthConsumerSecret, String oAuthTokenSecret) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec;
            if (null == oAuthTokenSecret) {
                String signingKey = OAuth.percentEncode(oAuthConsumerSecret) + '&';
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            } else {
                String signingKey = OAuth.percentEncode(oAuthConsumerSecret) + '&' + OAuth.percentEncode(oAuthTokenSecret);
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            }
            mac.init(spec);
            byteHMAC = mac.doFinal(signatueBaseStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Encode(byteHMAC);
    }

    private String base64Encode(byte[] b) {
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(b));
    }

    private String generateTimeStamp() {
        time_stamp = String.valueOf(System.currentTimeMillis() / 100000);
        return time_stamp;
    }
}
