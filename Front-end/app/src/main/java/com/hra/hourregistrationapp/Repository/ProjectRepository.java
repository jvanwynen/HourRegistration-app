package com.hra.hourregistrationapp.Repository;

import android.util.Log;
import android.view.View;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProjectRepository {

    //Make instance of retrofit Class
    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private View view;

    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>();
    private final MutableLiveData<Project> project = new MutableLiveData<>();

    public void getProjectsByCompanyId(int companyId){
        retrofitClient.getProjectService().getProjectsByCompanyId(companyId).enqueue(new Callback<List<Project>>() {
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

    public MutableLiveData<List<Project>> giveLiveProjectsResponses(){
        return projects;
    }

    public void getProjectById(int projectId){
        retrofitClient.getProjectService().getProjectById(projectId).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if(response.isSuccessful()){
                    project.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                project.setValue(null);
            }
        });
    }

    public MutableLiveData<Project> giveLiveSingleProjectResponse(){
        return project;
    }

    //Update een project in de db
    public void updateProject(int projectId, String name, String tag, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getProjectService().updateProject(projectId, name, tag).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    retrofitResponseListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

//    Post een nieuw project naar de API
    public void addProject(String name, String tag, int companyID, RetrofitResponseListener retrofitResponseListener) {
        retrofitClient.getProjectService().addProject(name, tag, companyID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                retrofitResponseListener.onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }
}
