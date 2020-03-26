package com.hra.hourregistrationapp.Persistence;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class CompanyDao implements BaseDAO<Company>{

    @Query("SELECT * FROM Company")
    public abstract List<Company> getAll();

    @Transaction
    public void upsert(List<Company> objList) {
        List<Long> insertResult = insert(objList);
        List<Company> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.size(); i++) {
            if (insertResult.get(i) == -1) {
                updateList.add(objList.get(i));
            }
        }

        if (!updateList.isEmpty()) {
            update(updateList);
        }
    }


}
