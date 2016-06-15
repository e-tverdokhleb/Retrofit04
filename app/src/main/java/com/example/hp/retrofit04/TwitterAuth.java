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
    private static String cKey="";
    private static String cSecret="";
    private static String aToken="";
    private static String aTokenSecret="";

    final private String HMAC_SHA1 = "HMAC-SHA1";
            private String method = "GET";
            private String consumer_key = "OewqCxpycFUv0SD2ia1dqFWA1";
            private String nonce = "d67c4dc51ab3073b952399cbd1bced80";
            private String signature = "";   //  v%2FnBLJ2XoJZLdxHpHq0fQoNhplA%3D
            private String time_stamp = "1465998688";
            private String token = "725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
            private String version = "1.0";

    public static final String ENCODING = "UTF-8";

    private transient org.apache.commons.codec.binary.Base64 base64;

    private String authHeader;

    public TwitterAuth(String cKey, String cSecret,String aToken, String aTokenSecret) {
        this.cKey = cKey; this.cSecret = cSecret; this.aToken = aToken; this.aTokenSecret = aTokenSecret;

        this.getSignature(true);
        update();
    }

    private void update(){
        this.authHeader = "OAuth " +
                "oauth_consumer_key=\""+consumer_key+"\"," +
                "oauth_nonce=\""+nonce+"\"," +
                "oauth_signature=\""+ signature +"\"," +
                "oauth_signature_method=\""+HMAC_SHA1+"\"," +
                "oauth_timestamp=\""+time_stamp+"\"," +
                "oauth_token=\""+token+"\"," +
                "oauth_version=\""+version+"\"";
    }


    public String getHeader() {
        return authHeader;
    }

    public String generateNonce(){
       char abc[] = {'a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
               '0','1','2','3','4','5','6','7','8','9'};

        Random rnd = new Random();
        char[] newKey = nonce.toCharArray();
        int num;
        for (int i = 0; i < newKey.length ; i++){
            newKey[i] = abc[rnd.nextInt(abc.length)];
        }

        nonce = String.valueOf(newKey);
        update();
        return nonce;
    }

    private String getSignature(boolean isEncoded) {
        String singatureBaseUrl = "GET" + "&" +
                OAuth.percentEncode(UserData.BASE_URL) + //https%3A%2F%2Fapi.twitter.com%2F
                "1.1%2Fstatuses%2Fuser_timeline.json&" +
                "oauth_consumer_key%3DOewqCxpycFUv0SD2ia1dqFWA1%26" +
                "oauth_nonce%3Dd67c4dc51ab3073b952399cbd1bced80%26" +
                "oauth_signature_method%3DHMAC-SHA1%26o" +
                "auth_timestamp%3D1465998688%26" +
                "oauth_token%3D725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB%26" +
                "oauth_version%3D1.0%26" +
                "screen_name%3DHromadskeUA";
        if (isEncoded){
            signature = OAuth.percentEncode(generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret));
        }else{
            signature = generateSignature(singatureBaseUrl, UserData.cSecret, UserData.aTokenSecret);
        }
        update();
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
}
