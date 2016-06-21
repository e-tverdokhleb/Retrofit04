package com.example.hp.retrofit04.Auth;

public class OAuthDataConvertor {
    String oauth_token;
    String oauth_token_secret;
    boolean oauth_callback_confirmed;

    String id_str;

    @Override
    public String toString(){
        return "hi from authconvereter";
    }
}
