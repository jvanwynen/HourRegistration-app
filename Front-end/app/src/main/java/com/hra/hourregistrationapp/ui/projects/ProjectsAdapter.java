package com.hra.hourregistrationapp.ui.projects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ProjectsAdapter extends
        RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

     // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView projectNameTextView;
        public LinearLayout layoutItemView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            int colorCounter;
            projectNameTextView = (TextView) itemView.findViewById(R.id.projectNameTextView);
            layoutItemView = itemView.findViewById(R.id.projectItemLayout);
        }
    }

    private List<Project> mProjects;

    public ProjectsAdapter(FragmentActivity activity, List<Project> mProjects) {
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
        ViewHolder viewHolder = new ViewHolder(projectView);

        return viewHolder;
    }

    //Involves populating the data into the item through holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List itemcolors = new ArrayList();
//        if()
        itemcolors.add("@drawable/border_yellow");
        itemcolors.add("YellowAccent");

        Drawable yellow = holder.layoutItemView.getContext().getResources().getDrawable(R.drawable.border_yellow);
        Drawable blue = holder.layoutItemView.getContext().getResources().getDrawable(R.drawable.border_blue);

        if(holder.layoutItemView.getBackground() ==  blue){
            holder.layoutItemView.setBackground(yellow);
        }else{
            holder.layoutItemView.setBackground(blue);
        }



        //Get the data model based on position
        Project project = mProjects.get(position);

        //Set item views based on your views and data model
        TextView ProjectNameTv = holder.projectNameTextView;

        ProjectNameTv.setText(project.getName());
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}