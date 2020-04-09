package com.hra.hourregistrationapp.Controller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.google.gson.JsonObject;
import com.hra.hourregistrationapp.Model.Project;

public interface ProjectService {
//    @GET("project/getbyid")
//    Call<JsonObject> getProjectsByCompanyId(@Query("?companyid=2") Integer companyId);

    @GET("project/getbycompanyid?}")
    Call<List<Project>> getProjectsByCompanyId(@Query("companyid") int companyId);
}
