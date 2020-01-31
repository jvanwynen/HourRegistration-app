package com.hra.hourregistrationapp.Repository;

import android.util.Log;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectRepository {
    RetrofitClient retrofitClient = RetrofitClient.getInstance();

    public void addProject(Project project){
        retrofitClient.getProjectService().addProject(project).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                Log.i("Dit is de response", "post submitted to API." + response.body().toString());
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {

            }
        });
    }
}
