package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private LoginRepository LoginRepository;
   private CompanyRepository companyRepository;
   private ProjectRepository projectRepository;
   private List<Company> companies = new ArrayList<>();
   private LocalDatabase localDatabase;


    public MainViewModel(Application application) {
        super(application);
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        projectRepository = new ProjectRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        loadDataFromRemote();
        if(!companies.isEmpty()) {
            insertCompaniesInLocalDB(companies.toArray(new Company[1]));
        }
    }

    private void loadDataFromRemote()  {
        companies = companyRepository.getCompanies();
        projectRepository.getProjects();
    }

    public void verifyIdToken(String idToken){
        LoginRepository.sendToken(idToken);
    }

    private void insertCompaniesInLocalDB(Company... companies){
        localDatabase.companyDao().insert(companies);
    }
}
