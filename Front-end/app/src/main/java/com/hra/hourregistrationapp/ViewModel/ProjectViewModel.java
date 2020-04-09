package com.hra.hourregistrationapp.ViewModel;


import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import java.util.List;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {

    private static ProjectRepository projectRepository = new ProjectRepository();

    public void sendGetProjectRequest(){
        projectRepository.getProjectsById();
    }

    public MutableLiveData<List<Project>> getResponse(){
        return projectRepository.giveLiveResponses();
    }

}