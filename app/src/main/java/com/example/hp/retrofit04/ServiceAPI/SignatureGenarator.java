package com.example.hp.retrofit04.ServiceAPI;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import oauth.signpost.OAuth;


public class SignatureGenarator {

    public static String generateSignature(String signatueBaseStr, String oAuthConsumerSecret, String oAuthTokenSecret) {
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

    private static String base64Encode(byte[] b) {
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(b));
    }
}
