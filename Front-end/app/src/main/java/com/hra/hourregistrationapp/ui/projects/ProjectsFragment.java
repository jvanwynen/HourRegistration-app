package com.hra.hourregistrationapp.ui.projects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import java.util.List;


public class ProjectsFragment extends Fragment {

    private ProjectsViewModel projectsViewModel;
    private ProjectsAdapter projectsAdapter;
    private RecyclerView rvProjects;
    private ProgressBar projectProgressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectsViewModel =
                ViewModelProviders.of(this).get(ProjectsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        projectProgressBar = root.findViewById(R.id.projectProgressBar);

        rvProjects = root.findViewById(R.id.rvProjects);
        projectsViewModel.sendGetProjectRequest();
        projectsViewModel.getResponse().observe(getActivity(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                setupRecyclerView(projects);

            }
        });
        //setupRecyclerView()
        return root;
    }
//
//        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
//
//        GetProjectsService api = retrofit.create(GetProjectsService.class);
//
//        Call<List<Project>> call = api.getProjects();
//
//        call.enqueue(new Callback<List<Project>>() {
//            @Override
//            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
//                if(response.isSuccessful()){
//                    List<Project> projectData = response.body();
//                    Log.i("1234", "hij doet t");
//                    setupRecyclerView(projectData);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Project>> call, Throwable t) {
//                List<Project> projectData = null;
//            }
//        });
//
//        return root;
//    }

    private void setupRecyclerView(List<Project> projects) {
        if(projects != null) {
            if (projectsAdapter == null) {
                projectProgressBar.setVisibility(View.INVISIBLE);
                rvProjects.setVisibility(View.VISIBLE);
                projectsAdapter = new ProjectsAdapter(getActivity(), projects);
                rvProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvProjects.setAdapter(projectsAdapter);
                rvProjects.setItemAnimator(new DefaultItemAnimator());
            } else {
                projectProgressBar.setVisibility(View.INVISIBLE);
                rvProjects.setVisibility(View.VISIBLE);
                projectsAdapter.notifyDataSetChanged();
            }
        }else{
            projectProgressBar.setVisibility(View.VISIBLE);
            rvProjects.setVisibility(View.INVISIBLE);
            projectProgressBar.setOnClickListener(new ProgressBar.OnClickListener() {
                @Override
                public void onClick(View v) {
                    projectsViewModel.sendGetProjectRequest();
                }
            });
        }
    }
}