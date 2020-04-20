package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LocalDatabase localDatabase;
    private LoginRepository loginRepository;
    private List<Company> companies;
    private CompanyRepository companyRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
    }

    public void upsertCompaniesLocally(){
        companies = companyRepository.getCompanielist();
        localDatabase.companyDao().upsert(companies);
    }

    public List<Company> getLocalCompanies(){
        return localDatabase.companyDao().getAll();
    }

    public void validateCompanyPassword(Company company, RetrofitResponseListener retrofitResponseListener){
        companyRepository.verifyCompanyToken(company, retrofitResponseListener);
    }

    public void addCompanyToUser(Company company, RetrofitResponseListener retrofitResponseListener){
        loginRepository.AddCompanytoUser(getCurrentUser(), company, retrofitResponseListener);
    }

    private User getCurrentUser(){
        return localDatabase.userDao().getAll().get(0);
    }

}
