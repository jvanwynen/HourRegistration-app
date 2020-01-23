package com.hra.hourregistrationapp.Retrofit;

import com.google.android.gms.common.api.Api;
import com.hra.hourregistrationapp.Controller.LoginInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String BASE_URL = "http://192.168.1.187/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public LoginInterface getInterface(){
        return retrofit.create(LoginInterface.class);
    }

}
