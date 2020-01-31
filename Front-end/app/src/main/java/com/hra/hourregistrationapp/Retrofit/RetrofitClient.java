package com.hra.hourregistrationapp.Retrofit;

import com.hra.hourregistrationapp.Services.CompanyService;
import com.hra.hourregistrationapp.Services.LoginService;
import com.hra.hourregistrationapp.Services.ProjectService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String BASE_URL = "http://145.109.175.137/";
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

    public LoginService getLoginService(){
        return retrofit.create(LoginService.class);
    }

    public ProjectService getProjectService() {
        return retrofit.create( ProjectService.class);
    }

    public CompanyService getCompanyService(){
        return retrofit.create(CompanyService.class);
    }

}
