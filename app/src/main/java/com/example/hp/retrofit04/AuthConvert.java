package com.example.hp.retrofit04;

public class AuthConvert {
    String oauth_token;
    String oauth_token_secret;
    boolean oauth_callback_confirmed;

    String id_str;

    @Override
    public String toString(){
        return "hi from authconvereter";
    }
}
