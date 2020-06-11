package com.hra.hourregistrationapp.Repository;

import android.util.Log;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProjectRepository {

    //Make instance of retrofit Class
    RetrofitClient retrofitClient = RetrofitClient.getInstance();

    final MutableLiveData<List<Project>> projects = new MutableLiveData<>();

    public void getProjectsById(){
        retrofitClient.getProjectService().getProjectsByCompanyId(2).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful()){
                    projects.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
//                List<Project> projectData = null;
                projects.setValue(null);
            }
        });


    }
    public MutableLiveData<List<Project>> giveLiveResponses(){
        return projects;
    }

    public void addProject(String name, String tag, int companyID) {
        RetrofitClient.getInstance().getProjectService().addProject(name, tag, companyID).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                Log.i(TAG, "Project submitted to API.");
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Log.i(TAG, "Project NOT submitted to API.");
            }
        });
    }
}
