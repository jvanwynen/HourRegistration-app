package com.hra.hourregistrationapp.Model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("login")
    private Login login;

    public Result(Boolean error, String message, Login login) {
        this.error = error;
        this.message = message;
        this.login = login;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Login getLogin() {
        return login;
    }
}
