package com.hra.hourregistrationapp.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hra.hourregistrationapp.R;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel projectsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectsViewModel =
                ViewModelProviders.of(this).get(ProjectsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        final TextView textView = root.findViewById(R.id.project_text_projects);
        projectsViewModel.getText().observe(this, s -> textView.setText(s));
        return root;
    }
}