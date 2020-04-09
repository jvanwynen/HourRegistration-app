package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.registration.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private LoginRepository LoginRepository;
   private CompanyRepository companyRepository;
   private LoginRepository loginRepository;
   private List<Company> companies = new ArrayList<>();
   private LocalDatabase localDatabase;
   Application application;



    public MainViewModel(Application application) {
        super(application);
        this.application= application;
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        loginRepository = new LoginRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        localDatabase.userDao().deleteAllFromTable();
    }

    public void loadDataFromRemote(RetrofitResponseListener retrofitResponseListener){
        companyRepository.getCompanies(retrofitResponseListener);
    }

    public void upsertCompaniesLocally(){
        companies = companyRepository.getCompanielist();
        localDatabase.companyDao().upsert(companies);
    }

    public void verifyIdToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        LoginRepository.sendToken(idToken, retrofitResponseListener);
    }

    public void setSignedInUser(){
        User user = new User(loginRepository.getUser().getId());
        localDatabase.userDao().upsert(user);
    }
}
