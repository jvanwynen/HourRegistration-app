package com.hra.hourregistrationapp.Controller;

import com.hra.hourregistrationapp.Model.Login;
import com.hra.hourregistrationapp.Model.User;

import java.math.BigInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @FormUrlEncoded
    @POST("/token/verify")
    Call<String> verifyToken(
            @Field("id_token") String token
    );

    @GET("/getuserbyid/{user_id}")
    Call<User> getUserById(@Path(value = "user_id", encoded = true) String userId
    );

    @POST("/user/createUser")
    Call<ResponseBody> createUser(
            @Field("userid") String userId,
            @Field("companyid") int companyId
    );

}
