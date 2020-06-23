package com.hra.hourregistrationapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Persistence.CompanyDao;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Persistence.ProjectDao;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProjectRepository {

   private LocalDatabase localDatabase;
   private RetrofitClient retrofitClient;
   private ProjectDao projectDao;
   private LiveData<List<Project>> projects;
   private List<Project> projectList;

   public ProjectRepository(Application application){
       localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
       retrofitClient = RetrofitClient.getInstance();
       projectList = new ArrayList<>();
       projectDao = localDatabase.projectDao();
       projects = projectDao.getAll();
   }

    public void getProjectsByCompanyId(int companyId){
       new ProjectAsyncTask(projectDao, 3).execute();
        retrofitClient.getProjectService().getProjectsByCompanyId(companyId).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        projectList.addAll(response.body());
                        new ProjectAsyncTask(projectDao, 2).execute(projectList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
            }
        });
    }

    //Update een project in de remote db
    public void updateProject(int projectId, String name, String tag, RetrofitResponseListener retrofitResponseListener){
        retrofitClient.getProjectService().updateProject(projectId, name, tag).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    retrofitResponseListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    //Post een nieuw project naar de API
    public void addProject(String name, String tag, int companyID, RetrofitResponseListener retrofitResponseListener) {
        retrofitClient.getProjectService().addProject(name, tag, companyID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                retrofitResponseListener.onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    private static class ProjectAsyncTask extends AsyncTask<List<Project>, Void, Project> {
        private ProjectDao projectDao;
        private int taskCode;
        private int projectId;

        private static final int TASK_GET = 1;
        private static final int TASK_INSERT = 2;
        private static final int TASK_DELETE = 3;

        ProjectAsyncTask(ProjectDao projectDao, int taskcode) {
            this.projectDao = projectDao;
            this.taskCode = taskcode;
        }

        @SafeVarargs
        @Override
        protected final Project doInBackground(List<Project>... projects) {
            switch (taskCode){
                case TASK_GET:
                   return projectDao.getProjectById(projectId);
                case TASK_INSERT:
                    projectDao.upsert(projects[0]);
                    break;
                case TASK_DELETE:
                    projectDao.DeleteAll();
                    break;
            }
            return null;
        }

        void setProjectId(int projectId) {
            this.projectId = projectId;
        }
    }



}
