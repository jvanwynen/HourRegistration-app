package com.hra.hourregistrationapp.Ui.projects;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hra.hourregistrationapp.Persistence.DAO;
import com.hra.hourregistrationapp.Persistence.ProjectDao;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.ViewModel.ProjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProjectFragment extends Fragment {

    private TextView projectNameTv;
    private TextView projectTagTv;
    private Button addProjectBtn;
    private ProjectViewModel projectViewModel;
    public View root;



    public AddProjectFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Setup title of the actionbar in this nested fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add projects");
//        Load the projectViewModel to use with handling the insert of the projects
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
//      Load the layout into the view when root is not filled
        if(root == null) {
            // Inflate the layout for this fragment
            root = inflater.inflate(R.layout.fragment_add_project, container, false);
        }

//      Load all the form elements for adding the projects
        projectNameTv = root.findViewById(R.id.projectNameInputField);
        projectTagTv = root.findViewById(R.id.projectTagInputField);
        addProjectBtn = root.findViewById(R.id.addProjectBtn);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getActivity().getBaseContext(), projectViewModel.getCurrentUser(), Toast.LENGTH_SHORT).show();
                
                String ProjectName = projectNameTv.getText().toString();
                String ProjectTag = projectTagTv.getText().toString();

                if (!TextUtils.isEmpty(projectNameTv.getText()) && !TextUtils.isEmpty(projectTagTv.getText()) ){
                    projectViewModel.addProject(ProjectName, ProjectTag);
                }
            }
        });
    }
}
