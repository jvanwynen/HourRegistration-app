package com.hra.hourregistrationapp.Model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("id_token")
    String Token;

    public Login(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
