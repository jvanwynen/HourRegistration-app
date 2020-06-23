package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Repository.UserRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private CompanyRepository companyRepository;
   private ProjectRepository projectRepository;
   private  UserRepository userRepository;

   public MainViewModel(Application application) {
        super(application);
        companyRepository = new CompanyRepository(application);
        userRepository = new UserRepository(application);
        projectRepository = new ProjectRepository(application);
        userRepository.deleteAll();
    }

    public void loadDataFromRemote(RetrofitResponseListener retrofitResponseListener){
        companyRepository.getCompanies(retrofitResponseListener);
    }

    public void verifyIdToken(String idToken, RetrofitResponseListener retrofitResponseListener){
        userRepository.sendToken(idToken, retrofitResponseListener);
    }

    public void sendGetProjectByCompanyRequest(){
        int companyId  = getCurrentUser().getCompanyId();
        projectRepository.getProjectsByCompanyId(companyId);
    }

    public User getCurrentUser(){
        return userRepository.getUser();
    }
    public boolean userHasCompany(){
       return (getCurrentUser().getCompanyId() != 0);
    }

}
