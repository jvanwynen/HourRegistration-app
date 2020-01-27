package com.hra.hourregistrationapp.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository {

    RetrofitClient retrofitClient = RetrofitClient.getInstance();

    final MutableLiveData<List<Company>> companies = new MutableLiveData<>();

    public void getCompanies() {
        retrofitClient.getCompanyService().getAllCompanies().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    companies.postValue(response.body());
                    // retrofitResponseListener.onSuccess();
                } else {
                    //  retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
//                List<Project> projectData = null;
                companies.setValue(null);
                //  retrofitResponseListener.onFailure();
            }
        });


    }

    public MutableLiveData<List<Company>> giveLiveResponses() {
        return companies;
    }

    public void createCompany(Company company) {

        retrofitClient.getCompanyService().CreateCompany(company.getName(), company.getPassword()).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("http", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("httpFailure", t.getMessage());
            }
        });
    }
}
