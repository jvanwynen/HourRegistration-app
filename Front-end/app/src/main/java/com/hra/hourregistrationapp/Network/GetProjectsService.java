package com.hra.hourregistrationapp.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import com.hra.hourregistrationapp.Model.Project;
public interface GetProjectsService {
    @GET("getallprojects")
    Call<List<Project>> getProjects();
}
