package com.hra.hourregistrationapp.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hra.hourregistrationapp.Controller.CompanyService;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.Ui.popup.Popup;
import com.hra.hourregistrationapp.Ui.setup.MainActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository  {

   // Context context;
    private RetrofitClient retrofitClient;
    private LocalDatabase localDatabase ;
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository(Context context) {
        this.retrofitClient = RetrofitClient.getInstance();
        this.localDatabase = LocalDatabase.getInstance(context);;
    }
    public CompanyRepository() {
        this.retrofitClient = RetrofitClient.getInstance();
    }

    public List<Company> getCompanies() {
        retrofitClient.getCompanyService().getAllCompanies().enqueue(new Callback<List<Company>>()  {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        companies.addAll(response.body());
                        companies.get(1);
                    }
                    //insertCompaniesInLocalDB(companies.toArray(new Company[1]));
                    // retrofitResponseListener.onSuccess();
                } else {
                    //  retrofitResponseListener.onFailure();
                    System.out.println("failure");
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t){
                companies = null;
            }
        });
        return companies;
    }

    public List<Company> giveLiveResponses() {
        return companies;
    }

    public void createCompany(Company company) {

        retrofitClient.getCompanyService().createCompany(company.getName(), company.getPassword()).enqueue(new Callback<ResponseBody>() {

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

    public void verifyCompanyToken(Company company){

        retrofitClient.getCompanyService().verifyCompanyPassword(company.getName(), company.getPassword()).enqueue(new Callback<ResponseBody>(){

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



    public List<Company> getCompaniesLocalDB(){
        return localDatabase.companyDao().getAll();
    }

}
