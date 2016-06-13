package com.example.hp.retrofit04;

import android.util.Log;

    import java.util.Random;

public class TwitterAuth {
    final private String HMAC_SHA1 = "HMAC-SHA1";

            private String consumer_key = "OewqCxpycFUv0SD2ia1dqFWA1";
    static  private String nonce = "ecadfe0f8b12b6cea334c624af2c0f30";
            private String signature = "TaOEAdBfz0E8GcDZWhD3zAiqHkk%3D";
            private String time_stamp = "1465770455";
            private String token = "725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
            private String version = "1.0";

    private String authHeader;

    public TwitterAuth(){
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
}
