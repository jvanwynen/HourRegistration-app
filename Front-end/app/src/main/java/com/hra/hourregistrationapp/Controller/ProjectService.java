package com.hra.hourregistrationapp.Controller;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("project/getbycompanyid?")
    Call<List<Project>> getProjectsByCompanyId(@Query("companyid") int companyId);

    @GET("project/getbyid?")
    Call<Project> getProjectById(@Query("id") int projectId);

    @POST("/project/insert")
    @FormUrlEncoded
    Call<ResponseBody> addProject(@Field("name") String name,
                                  @Field("tag") String tag,
                                  @Field("company_id") int company_id);

    @POST("/project/updatebyid")
    @FormUrlEncoded
    Call<ResponseBody> updateProject(@Field("id") int projectId,
                                     @Field("projectname") String name,
                                     @Field("project_tag") String tag);
}

