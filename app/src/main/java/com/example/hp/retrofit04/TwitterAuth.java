package com.example.hp.retrofit04;

public class TwitterAuth {
    private String DEFAULT_SIGNATURE_METHOD = "HMAC-SHA1";

    private String consumer_key = "OewqCxpycFUv0SD2ia1dqFWA1";
    private String nonce = "ecadfe0f8b12b6cea334c624af2c0f30";
    private String signature = "TaOEAdBfz0E8GcDZWhD3zAiqHkk%3D";
    private String time_stamp = "1465770455";
    private String token = "725877051245387778-KJ4FDm76R2wgEOk0acRhy4lHNLIfKSB";
    private String version = "1.0";

    // containts only "value", do not forget to add key = "Authorization"
    private String authorizationHeader = "OAuth " +
            "oauth_consumer_key=\""+consumer_key+"\"," +
            "oauth_nonce=\""+nonce+"\"," +
            "oauth_signature=\""+ signature +"\"," +
            "oauth_signature_method=\""+DEFAULT_SIGNATURE_METHOD+"\"," +
            "oauth_timestamp=\""+time_stamp+"\"," +
            "oauth_token=\""+token+"\"," +
            "oauth_version=\""+version+"\"";


    public String TwitterAuthenticator() {
        return authorizationHeader;
    }

    public String getHeader() {
        return authorizationHeader;
    }
}
