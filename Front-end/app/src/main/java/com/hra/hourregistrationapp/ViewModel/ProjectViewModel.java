package com.hra.hourregistrationapp.ViewModel;


import android.app.Application;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ProjectViewModel extends AndroidViewModel {

    private static ProjectRepository projectRepository;
    private static UserRepository userRepository;
    private int projectId;
    private User user;
    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository();
        userRepository = new UserRepository(application);
        user = userRepository.getUser();
    }

    public void sendGetProjectByCompanyRequest(){
        int companyId  = user.getCompanyId();
        projectRepository.getProjectsByCompanyId(companyId);
    }

    public void sendGetProjectByIdRequest(int projectId){
        this.projectId = projectId;
        projectRepository.getProjectById(projectId);
    }

    public void updateProject(int projectId, String name, String tag, RetrofitResponseListener retrofitResponseListener){
        projectRepository.updateProject(projectId, name, tag, retrofitResponseListener);
    }

    public void addProject(String name, String tag, RetrofitResponseListener retrofitResponseListener) {
        //Current companyID ophalen
        User user = userRepository.getUser();
        int currentCompany = user.getCompanyId();
        projectRepository.addProject(name, tag, currentCompany, retrofitResponseListener); }

    public int getCurrentUser(){
            User user = userRepository.getUser();
            return user.getCompanyId();
    }

    public MutableLiveData<List<Project>> getProjectsResponse(){
        return projectRepository.giveLiveProjectsResponses();
    }

    public MutableLiveData<Project> getProjectResponse(){
        return projectRepository.giveLiveSingleProjectResponse();
    }


}