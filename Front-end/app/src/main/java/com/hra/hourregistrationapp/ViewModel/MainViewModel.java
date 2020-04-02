package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
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
   private LoginRepository loginRepository;
   private ProjectRepository projectRepository;
   private List<Company> companies = new ArrayList<>();
   private LocalDatabase localDatabase;



    public MainViewModel(Application application) {
        super(application);
        LoginRepository = new LoginRepository();
        companyRepository = new CompanyRepository();
        projectRepository = new ProjectRepository();
        loginRepository = new LoginRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        localDatabase.userDao().deleteAllFromTable();
    }

    public void loadDataFromRemote()  {
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
        LoginRepository.sendToken(idToken, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                User user = new User(loginRepository.getId());
                localDatabase.userDao().upsert(user);
            }

            @Override
            public void onFailure() {

            }
        });
    }


}
