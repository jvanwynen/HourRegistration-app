package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hra.hourregistrationapp.Repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository mLoginRepository  = new LoginRepository();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void verifyIdToken(String idToken){
        mLoginRepository.sendToken(idToken);
    }
}
