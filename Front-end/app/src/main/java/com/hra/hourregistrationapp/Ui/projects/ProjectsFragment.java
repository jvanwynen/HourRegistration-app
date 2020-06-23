package com.hra.hourregistrationapp.Ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hra.hourregistrationapp.Adapter.ProjectAdapter;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.ProjectViewModel;


import java.util.List;

public class ProjectsFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private RecyclerView rvProjects;
    private ProgressBar projectProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        View root = inflater.inflate(R.layout.fragment_projects, container, false);

        projectProgressBar = root.findViewById(R.id.projectProgressBar);

        FloatingActionButton fabButton = root.findViewById(R.id.fab);

        fabButton.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.addProjectFragment));

        rvProjects = root.findViewById(R.id.rvProjects);
        projectViewModel.sendGetProjectByCompanyRequest();
        projectViewModel.getLocalProjectList().observe(getActivity(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                setupRecyclerView(projects);
            }
        });

        return root;
    }

    private void setupRecyclerView(List<Project> projects) {
        if(projects != null) {
                projectProgressBar.setVisibility(View.INVISIBLE);
                rvProjects.setVisibility(View.VISIBLE);
            ProjectAdapter projectAdapter = new ProjectAdapter(getActivity(), projects);
                rvProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvProjects.setAdapter(projectAdapter);
                rvProjects.setItemAnimator(new DefaultItemAnimator());
        }else{
            projectProgressBar.setVisibility(View.VISIBLE);
            rvProjects.setVisibility(View.INVISIBLE);
        }
    }
}