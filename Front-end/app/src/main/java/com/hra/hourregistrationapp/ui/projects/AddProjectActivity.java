package com.hra.hourregistrationapp.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hra.hourregistrationapp.MainActivity;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.Project.AddProjectViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class AddProjectActivity extends AppCompatActivity {

    private EditText projectnameET;
    private EditText tagET;
    private Button saveProjectBtn;
    private AddProjectViewModel addProjectViewModel;
    private String projectName;
    private int projectHours;
    private int projectTag;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        addProjectViewModel = ViewModelProviders.of(this).get(AddProjectViewModel.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addProjectToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add project");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        projectnameET = (EditText) findViewById(R.id.AddProjectNameEditText);
        tagET = (EditText) findViewById(R.id.AddProjectTagEditText);
        saveProjectBtn = (Button) findViewById(R.id.AddProjectSaveBtn);
        saveProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                projectName = projectnameET.getText().toString();
                projectHours = projectHours = 0;
                projectTag = Integer.parseInt(tagET.getText().toString());

                Project project = new Project(projectName, projectHours, projectTag);

                addProjectViewModel.sendAddProjectRequest(project);
            }
        });
    }


}
