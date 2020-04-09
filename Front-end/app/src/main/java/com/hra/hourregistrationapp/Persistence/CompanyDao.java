package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hra.hourregistrationapp.Model.Company;

import java.util.ArrayList;
import java.util.List;

/*
This DAO is responsible for communication with local database for all Company related queries
 */
@Dao
public abstract class CompanyDao implements DAO<Company> {

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
