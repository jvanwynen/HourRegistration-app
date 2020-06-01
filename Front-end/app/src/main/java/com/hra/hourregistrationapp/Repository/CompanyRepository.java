package com.hra.hourregistrationapp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;
import com.hra.hourregistrationapp.Persistence.CompanyDao;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Persistence.UserDao;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
this class is responsible for sending the requests and receiving responses from remote server
 */
public class CompanyRepository {

    // Context context;
    private RetrofitClient retrofitClient;
    private LocalDatabase localDatabase;
    private CompanyDao companyDao;
    private LiveData<List<Company>> companyLiveList;
    private List<Company> companyList;

    public CompanyRepository(Application application) {
        localDatabase = LocalDatabase.getInstance(application.getApplicationContext());
        companyDao = localDatabase.companyDao();
        companyLiveList = companyDao.getAll();
        companyList = new ArrayList<>();
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public void getCompanies(RetrofitResponseListener retrofitResponseListener) {
        retrofitClient.getCompanyService().getAllCompanies().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //companies.setValue(response.body());
                        companyList.addAll(response.body());
                        new CompanyAsyncTask(companyDao).execute(companyList);
                    }
                } else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public LiveData<List<Company>> getAllLocalCompanies() {
        return companyLiveList;
    }

    public void createCompany(User user, Company company, RetrofitResponseListener retrofitResponseListener) {

        retrofitClient.getCompanyService().createCompany(user.getId(), company.getCompanyname(), company.getPassword()).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    retrofitResponseListener.onSuccess();
                } else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public void verifyCompanyToken(Company company, RetrofitResponseListener retrofitResponseListener) {

        retrofitClient.getCompanyService().verifyCompanyPassword(company.getId(), company.getPassword()).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    retrofitResponseListener.onSuccess();
                } else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    private static class CompanyAsyncTask extends AsyncTask<List<Company>, Void, Void> {
        private CompanyDao companyDao;


        CompanyAsyncTask(CompanyDao companyDao) {
            this.companyDao = companyDao;
        }


        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Company>... companies) {
            companyDao.upsert(companies[0]);
            return null;
        }
    }
}

