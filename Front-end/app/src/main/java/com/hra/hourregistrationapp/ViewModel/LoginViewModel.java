package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LocalDatabase localDatabase;
    private LoginRepository  LoginRepository;
    private List<Company> companies;
    private CompanyRepository companyRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
    }

    //check if a user is logged in
    public boolean userLoggedInSuccessful() {
        return(!localDatabase.userDao().getAll().isEmpty());
    }


    public List<Company> getLocalCompanies(){
        return localDatabase.companyDao().getAll();
    }

    //get Company names from local database
    public ArrayList<String> getCompanyNames(){
        ArrayList<String> CompanyNames = new ArrayList<>();
        companies = getLocalCompanies();
        if(!companies.isEmpty()){
            for(Company c : companies ){
                CompanyNames.add(c.getCompanyname());
            }
        } else {
            CompanyNames.add("No existing companies");
        }
        return CompanyNames;
    }

    public void validateCompanyPassword(Company company, RetrofitResponseListener retrofitResponseListener){
        companyRepository.verifyCompanyToken(company, retrofitResponseListener);
    }


}
