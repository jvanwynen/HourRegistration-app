package com.hra.hourregistrationapp.Controller;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.Project;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CompanyService {

    @GET("company/allcompanies")
    Call<List<Company>> getAllCompanies();

    @FormUrlEncoded
    @POST("company/insert")
    Call<ResponseBody> createCompany(
            @Field("user_id") String id,
            @Field("name") String name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("company/validate")
    Call<ResponseBody> verifyCompanyPassword(
            @Field("id") int id,
            @Field("companypassword") String password
    );
}

