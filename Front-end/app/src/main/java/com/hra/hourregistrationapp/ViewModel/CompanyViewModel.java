package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.List;

public class CompanyViewModel extends AndroidViewModel {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        companyRepository = new CompanyRepository(application);
        userRepository = new UserRepository(application);
    }
    public void createNewCompany(Company company, RetrofitResponseListener retrofitResponseListener){
        companyRepository.createCompany(getCurrentUser(), company, retrofitResponseListener);
    }
    public void loadDataFromRemote(RetrofitResponseListener retrofitResponseListener){
        companyRepository.getCompanies(retrofitResponseListener);
    }

    private User getCurrentUser(){
        return userRepository.getUser();
    }

}
