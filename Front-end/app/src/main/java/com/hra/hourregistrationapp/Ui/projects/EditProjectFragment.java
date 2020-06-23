package com.hra.hourregistrationapp.Ui.projects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.ViewModel.ProjectViewModel;

import java.util.List;

import static android.text.TextUtils.isEmpty;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProjectFragment extends Fragment {
    private TextView projectEditTv;
    private EditText editProjectNameEt;
    private EditText editProjectTagEt;
    private Button editProjectBtn;
    private Button deleteProjectBtn;
    private ProjectViewModel projectViewModel;

    public EditProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit project");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_project, container, false);

        projectEditTv = root.findViewById(R.id.edit_project_text);
        editProjectNameEt = root.findViewById(R.id.editProjectNameInputField);
        editProjectTagEt = root.findViewById(R.id.editProjectTagInputField);
        editProjectBtn = root.findViewById(R.id.editProjectBtn);
        deleteProjectBtn = root.findViewById(R.id.deleteProjectBtn);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        String projectTitle = EditProjectFragmentArgs.fromBundle(getArguments()).getProjectTitle();
        projectEditTv.setText("Edit project: " + projectTitle);
        ProjectViewModel projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int projectID = EditProjectFragmentArgs.fromBundle(getArguments()).getUserID();
                String projectName = editProjectNameEt.getText().toString();
                String projectTag = editProjectTagEt.getText().toString();

                if (!isEmpty(projectName) && !isEmpty(projectTag) ){
                    projectViewModel.updateProject(projectID, projectName, projectTag, new RetrofitResponseListener() {
                        @Override
                        public void onSuccess() {
                            projectViewModel.sendGetProjectByCompanyRequest();
                            Navigation.findNavController(view).navigate(R.id.action_editProjectFragment_to_nav_projects);
                        }

                        @Override
                        public void onFailure() {
                            //TODO toast tonen (lukte nog niet om de juiste context op te halen)
                        }
                    });
                }
            }
        });
    }
}
