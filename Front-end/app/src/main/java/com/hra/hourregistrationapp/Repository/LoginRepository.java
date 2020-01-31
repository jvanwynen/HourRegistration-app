package com.hra.hourregistrationapp.Repository;

import android.util.Log;

import com.hra.hourregistrationapp.Retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    RetrofitClient retrofitClient = RetrofitClient.getInstance();

    public void sendToken(String idToken){
        Call<ResponseBody> call = retrofitClient
                .getLoginService()
                .verifyToken(idToken);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("http", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("httpFailure", t.getMessage());
            }
        });
        // updateUI(account);

    }
}
