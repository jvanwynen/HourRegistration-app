package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.popup.Popup;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private LoginRepository LoginRepository;
   private CompanyRepository companyRepository;
   private ProjectRepository projectRepository;
   private List<Company> companies = new ArrayList<>();
   private LocalDatabase localDatabase;
   private Application application;


    public MainViewModel(Application application) {
        super(application);
        this.application = application;
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        projectRepository = new ProjectRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        loadDataFromRemote();
    }

    private void loadDataFromRemote()  {
        companyRepository.getCompanies(new RetrofitResponseListener(){
            @Override
            public void onSuccess() {
                companies = companyRepository.getCompanielist();
                localDatabase.companyDao().upsert(companies);
            }

            @Override
            public void onFailure() {
            }
        });
        projectRepository.getProjects();
    }

    public void verifyIdToken(String idToken){
        LoginRepository.sendToken(idToken);
    }


}
