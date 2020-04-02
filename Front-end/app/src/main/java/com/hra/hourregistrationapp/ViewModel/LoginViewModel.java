package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LocalDatabase localDatabase;
    private LoginRepository  mLoginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mLoginRepository = new LoginRepository();
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());

    }

    //check if a user is logged in, returns false if not
    public boolean userLoggedInSuccessful() {
        return(!localDatabase.userDao().getAll().isEmpty());
    }


    public List<Company> getLocalCompanies(){
        return localDatabase.companyDao().getAll();
    }
}
