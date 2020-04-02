package com.hra.hourregistrationapp.Repository;

import android.util.Log;

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
    private String Id;

    public LoginRepository() {
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public void sendToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getLoginService().verifyToken(idToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Id = response.body();
                    retrofitResponseListener.onSuccess();
                }
                retrofitResponseListener.onFailure();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                retrofitResponseListener.onSuccess();
            }
        });
    }

    public String getId() {
        return Id;
    }
}
