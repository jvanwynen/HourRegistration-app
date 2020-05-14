package com.hra.hourregistrationapp.Repository;

import android.util.Log;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.math.BigInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private RetrofitClient retrofitClient ;
    private static User user;


    public LoginRepository() {
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public void sendToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getLoginService().verifyToken(idToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                    retrofitResponseListener.onSuccess();
                }
                else{
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public void AddCompanytoUser(User user, Company company, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getLoginService().createUser(user.getId(), company.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    retrofitResponseListener.onSuccess();
                }else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public User getUser() {
        return user;
    }
}
