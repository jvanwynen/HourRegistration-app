package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private UserRepository UserRepository;
   private CompanyRepository companyRepository;
   private  UserRepository userRepository;
   
   Application application;

   public MainViewModel(Application application) {
        super(application);
        this.application= application;
        companyRepository = new CompanyRepository(application);
        userRepository = new UserRepository(application);
        userRepository.deleteAll();
    }

    public void loadDataFromRemote(RetrofitResponseListener retrofitResponseListener){
        companyRepository.getCompanies(retrofitResponseListener);
    }

    public void verifyIdToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        userRepository.sendToken(idToken, retrofitResponseListener);
    }

}
