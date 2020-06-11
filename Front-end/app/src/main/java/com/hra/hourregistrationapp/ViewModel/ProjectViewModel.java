package com.hra.hourregistrationapp.ViewModel;


import android.app.Application;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends AndroidViewModel {

    private static ProjectRepository projectRepository;
    private static UserRepository userRepository;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository();
        userRepository = new UserRepository(application);
    }

    public void sendGetProjectRequest(){
        projectRepository.getProjectsById();
    }

    public void addProject(String name, String tag) {

        //Current companyID ophalen
//        int currentCompany = userRepository.getUser().getCompanyId();

        int currentCompany = 16;
        projectRepository.addProject(name, tag, currentCompany); }

    public int getCurrentUser(){
            User user = userRepository.getUser();
            return user.getCompanyId();
    }

    public MutableLiveData<List<Project>> getResponse(){
        return projectRepository.giveLiveResponses();
    }

}