package com.hra.hourregistrationapp.Services;

import com.hra.hourregistrationapp.Model.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CompanyService {

        @GET("getallcompanies")
        Call<List<Company>> getCompanies();
}

