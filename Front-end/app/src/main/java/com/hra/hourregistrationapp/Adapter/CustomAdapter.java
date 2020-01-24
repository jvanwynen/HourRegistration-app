//package com.hra.hourregistrationapp.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.hra.hourregistrationapp.Model.Project;
//import com.hra.hourregistrationapp.R;
//import com.hra.hourregistrationapp.ui.projects.ProjectsAdapter;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
//
//    private List<Project> projects;
//    private Context context;
//
//    public CustomAdapter(Context context,List projects){
//        this.context = context;
//        this.projects = projects;
//    }
//
//    class CustomViewHolder extends RecyclerView.ViewHolder {
//
//        public final View mView;
//
//        TextView txtTitle;
//
//        CustomViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//            txtTitle = mView.findViewById(R.id.projectNameTextView);
//        }
//    }
//
//    @Override
//    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.project_item, parent, false);
//        return new CustomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        holder.txtTitle.setText(projects.get(position).getName());
//
//        Builder builder = new Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getThumbnailUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//}package com.hra.hourregistrationapp.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.hra.hourregistrationapp.Model.Project;
//import com.hra.hourregistrationapp.R;
//import com.hra.hourregistrationapp.ui.projects.ProjectsAdapter;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
//
//    private List<Project> projects;
//    private Context context;
//
//    public CustomAdapter(Context context,List projects){
//        this.context = context;
//        this.projects = projects;
//    }
//
//    class CustomViewHolder extends RecyclerView.ViewHolder {
//
//        public final View mView;
//
//        TextView txtTitle;
//
//        CustomViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//            txtTitle = mView.findViewById(R.id.projectNameTextView);
//        }
//    }
//
//    @Override
//    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.project_item, parent, false);
//        return new CustomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        holder.txtTitle.setText(projects.get(position).getName());
//
//        Builder builder = new Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getThumbnailUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//}