package com.hra.hourregistrationapp.Repository;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ProjectRepository {

    RetrofitClient retrofitClient = RetrofitClient.getInstance();

    final MutableLiveData<List<Project>> projects = new MutableLiveData<>();

    public void getProjects(){
        retrofitClient.getProjectService().getProjects().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful()){
                    projects.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                projects.setValue(null);
            }
        });


    }
    public MutableLiveData<List<Project>> giveLiveResponses(){
        return projects;
    }
}
