package com.hra.hourregistrationapp.ui.projects;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import java.util.List;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectsViewModel extends ViewModel {

    private static ProjectRepository projectRepository = new ProjectRepository();
    private ProjectsAdapter projectsAdapter;

    public void sendGetProjectRequest(){
        projectRepository.getProjects();
    }

    public MutableLiveData<List<Project>> getResponse(){
        return projectRepository.giveLiveResponses();
    }

}