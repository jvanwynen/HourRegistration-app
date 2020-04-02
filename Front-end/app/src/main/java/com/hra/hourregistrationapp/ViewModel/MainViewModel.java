package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private LoginRepository LoginRepository;
   private CompanyRepository companyRepository;
   private ProjectRepository projectRepository;
   private List<Company> companies = new ArrayList<>();
   private LocalDatabase localDatabase;
   private Application application;
   private boolean isSuccessful;


    public MainViewModel(Application application) {
        super(application);
        this.application = application;
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        projectRepository = new ProjectRepository();
        isSuccessful = false;
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());

    }

    public boolean loadDataFromRemote()  {
        companyRepository.getCompanies(new RetrofitResponseListener(){
            @Override
            public void onSuccess() {
                companies = companyRepository.getCompanielist();
                localDatabase.companyDao().upsert(companies);
                isSuccessful = true;
            }

            @Override
            public void onFailure() {
                isSuccessful = false;
            }
        });
        projectRepository.getProjects();
        return isSuccessful;
    }

    public boolean verifyIdToken(String idToken){
        LoginRepository.sendToken(idToken, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                isSuccessful = true;
            }

            @Override
            public void onFailure() {
                isSuccessful = false;
            }
        });
        return isSuccessful;
    }


}
