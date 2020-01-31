package com.hra.hourregistrationapp.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("verifyidtoken")
    Call<ResponseBody> CreatePost(
            @Field("id_token") String token);
}
