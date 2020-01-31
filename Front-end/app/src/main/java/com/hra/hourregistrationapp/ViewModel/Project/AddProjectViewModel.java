package com.hra.hourregistrationapp.ViewModel.Project;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Repository.AddProjectRepository;

import androidx.lifecycle.ViewModel;

public class AddProjectViewModel extends ViewModel {
    private static AddProjectRepository addProjectRepository = new AddProjectRepository();

    public void sendAddProjectRequest(Project project) {
        addProjectRepository.addProject(project);
    }
}
