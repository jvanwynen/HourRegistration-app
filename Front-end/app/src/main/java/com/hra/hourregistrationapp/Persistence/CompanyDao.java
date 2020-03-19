package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;

import java.util.List;

@Dao
public interface CompanyDao{

    @Insert
    void insert(Company... companies);

    @Query("SELECT * FROM Company")
    List<Company> getAll();
}
