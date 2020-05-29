package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LocalDatabase localDatabase;
    private UserRepository userRepository;
    private LiveData<List<Company>> allCompanies;
    private CompanyRepository companyRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        companyRepository = new CompanyRepository(application);
        allCompanies = companyRepository.getAllLocalCompanies();
    }

    public LiveData<List<Company>> getLocalCompanies(){
        return allCompanies;
    }

    public void validateCompanyPassword(Company company, RetrofitResponseListener retrofitResponseListener){
        companyRepository.verifyCompanyToken(company, retrofitResponseListener);
    }

    public void addCompanyToUser(Company company, RetrofitResponseListener retrofitResponseListener){
        userRepository.AddCompanytoUser(getCurrentUser(), company, retrofitResponseListener);
    }

    private User getCurrentUser(){
        return userRepository.getUser();
    }

}
