package com.hra.hourregistrationapp.Controller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import com.hra.hourregistrationapp.Model.Project;

public interface ProjectService {
    @GET("project/allprojects")
    Call<List<Project>> getProjects();
}
