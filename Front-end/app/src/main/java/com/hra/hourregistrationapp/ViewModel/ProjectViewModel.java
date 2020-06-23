package com.hra.hourregistrationapp.ViewModel;


import android.app.Application;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProjectViewModel extends AndroidViewModel {

    private static ProjectRepository projectRepository;
    private static UserRepository userRepository;

    private static LiveData<List<Project>> allProjects;

    private int projectId;
    private User user;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        userRepository = new UserRepository(application);
        user = userRepository.getUser();
        allProjects = projectRepository.getProjects();
    }

    public void updateProject(int projectId, String name, String tag, RetrofitResponseListener retrofitResponseListener){
        projectRepository.updateProject(projectId, name, tag, retrofitResponseListener);
    }

    public void addProject(String name, String tag, RetrofitResponseListener retrofitResponseListener) {
        //Current companyID ophalen
        User user = userRepository.getUser();
        int currentCompany = user.getCompanyId();
        projectRepository.addProject(name, tag, currentCompany, retrofitResponseListener); }

    public void sendGetProjectByCompanyRequest(){
        int companyId  = user.getCompanyId();
        projectRepository.getProjectsByCompanyId(companyId);
    }

    public LiveData<List<Project>> getLocalProjectList(){
        return allProjects;
    }


}