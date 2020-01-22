package com.hra.hourregistrationapp.Controller;

import com.hra.hourregistrationapp.Model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInterface {

    @POST("verifyidtoken")
    Call<Login> CreatePost(@Body Login post);
}
