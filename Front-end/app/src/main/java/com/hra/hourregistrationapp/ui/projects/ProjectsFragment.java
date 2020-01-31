package com.hra.hourregistrationapp.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hra.hourregistrationapp.Adapter.ProjectAdapter;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.Project.ProjectViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProjectsFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private ProjectAdapter projectAdapter;
    private RecyclerView rvProjects;
    private ProgressBar projectProgressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        FloatingActionButton myFab = (FloatingActionButton) root.findViewById(R.id.project_btn_fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                startActivity(intent);
            }
        });
        projectProgressBar = root.findViewById(R.id.projectProgressBar);

        rvProjects = root.findViewById(R.id.rvProjects);
        projectViewModel.sendGetProjectRequest();
        setupRecyclerView(projectViewModel.getResponse().getValue());
        projectViewModel.getResponse().observe(getActivity(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                setupRecyclerView(projects);

            }
        });
        //setupRecyclerView()
        return root;
    }

    private void setupRecyclerView(List<Project> projects) {
        if (projects != null) {
            if (projectAdapter == null) {
                projectProgressBar.setVisibility(View.INVISIBLE);
                rvProjects.setVisibility(View.VISIBLE);
                projectAdapter = new ProjectAdapter(getActivity(), projects);
                rvProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvProjects.setAdapter(projectAdapter);
                rvProjects.setItemAnimator(new DefaultItemAnimator());
            } else {
                projectProgressBar.setVisibility(View.INVISIBLE);
                rvProjects.setVisibility(View.VISIBLE);
                projectAdapter.notifyDataSetChanged();
            }
        } else {
            projectProgressBar.setVisibility(View.VISIBLE);
            rvProjects.setVisibility(View.INVISIBLE);
            projectProgressBar.setOnClickListener(new ProgressBar.OnClickListener() {
                @Override
                public void onClick(View v) {
                    projectViewModel.sendGetProjectRequest();
                }
            });
        }
    }
}