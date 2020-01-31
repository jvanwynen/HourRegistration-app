package com.hra.hourregistrationapp.Services;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.hra.hourregistrationapp.Model.Project;

public interface ProjectService {
    @GET("getallprojects")
    Call<List<Project>> getProjects();

    @POST("createproject")
    Call<Project> addProject(@Body Project project);
}
