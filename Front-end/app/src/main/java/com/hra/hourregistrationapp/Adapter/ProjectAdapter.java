package com.hra.hourregistrationapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ProjectAdapter extends
        RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView projectNameTextView;
        ConstraintLayout layoutItemView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            int colorCounter;
            projectNameTextView = itemView.findViewById(R.id.projectNameTextView);
            layoutItemView = itemView.findViewById(R.id.projectItemLayout);
        }
    }

    private List<Project> mProjects;

    public ProjectAdapter(FragmentActivity activity, List<Project> mProjects) {
        this.mProjects = mProjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //inflate the custom layout
        View projectView = inflater.inflate(R.layout.project_item, parent, false);
        //return a new holder instance

        return new ViewHolder(projectView);
    }

    //Involves populating the data into the item through holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Get the data model based on position
        Project project = mProjects.get(position);

        //Set item views based on your views and data model
        TextView ProjectNameTv = holder.projectNameTextView;

        String projectName = project.getProjectname();

          String capitalProjectName = projectName.substring(0, 1).toUpperCase() + projectName.substring(1).toLowerCase();

        ProjectNameTv.setText(capitalProjectName);
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}