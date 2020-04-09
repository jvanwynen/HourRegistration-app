package com.hra.hourregistrationapp.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
this class is responsible for sending the requests and receiving responses from remote server
 */
public class CompanyRepository  {

   // Context context;
    private RetrofitClient retrofitClient;
    private LocalDatabase localDatabase ;
    private List<Company> companielist = new ArrayList<>();
    private final MutableLiveData<List<Company>> companies = new MutableLiveData<>();

    public CompanyRepository() {
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public void getCompanies(RetrofitResponseListener retrofitResponseListener) {
        retrofitClient.getCompanyService().getAllCompanies().enqueue(new Callback<List<Company>>()  {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //companies.setValue(response.body());
                        companielist.addAll(response.body());
                        retrofitResponseListener.onSuccess();
                    }

                } else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t){
                retrofitResponseListener.onFailure();
            }
        });
    }

    public MutableLiveData<List<Company>> giveLiveResponses() {
        return companies;
    }

    public void createCompany(Company company) {

        retrofitClient.getCompanyService().createCompany(company.getCompanyname(), company.getPassword()).enqueue(new Callback<ResponseBody>() {

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

    public void verifyCompanyToken(Company company, RetrofitResponseListener retrofitResponseListener){

        retrofitClient.getCompanyService().verifyCompanyPassword(company.getId(), company.getPassword()).enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    retrofitResponseListener.onSuccess();
                }
                else {
                    retrofitResponseListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitResponseListener.onFailure();
            }
        });
    }

    public List<Company> getCompanielist() {
        return companielist;
    }
}
