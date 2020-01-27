package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hra.hourregistrationapp.Adapter.ProjectAdapter;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.Project;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.ProjectRepository;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.List;

public class CompanyViewModel extends AndroidViewModel {

    private CompanyRepository mRepository;
    private LiveData<List<Company>> mCompanyList;

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CompanyRepository();
        mRepository.getCompanies();
        mCompanyList = mRepository.giveLiveResponses();

    }

    public LiveData<List<Company>> getAllCompanies() {
        return mCompanyList;
    }
}
