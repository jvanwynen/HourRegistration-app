package com.hra.hourregistrationapp.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Repository.CompanyRepository;

import java.util.List;

public class CompanyViewModel extends AndroidViewModel {

    private CompanyRepository mCompanyRepository;
    private LiveData<List<Company>> mCompanyList;

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        mCompanyRepository = new CompanyRepository();
    }

//    public LiveData<List<Company>> getAllCompanies() {
//        mCompanyRepository.getCompanies();
//        mCompanyList = mCompanyRepository.giveLiveResponses();
//        return mCompanyList;
//    }


//    public List<Company> getAllCompanies(){
//        mCompanyRepository = new
//    }

    public void createNewCompany(Company company){
        mCompanyRepository.createCompany(company);
    }
}
