package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class UserDao implements BaseDAO<User>{

    @Query("DELETE FROM user")
   public abstract void deleteAllFromTable();

    @Query("SELECT * FROM user")
    public abstract List<User> getAll();

    @Transaction
    public void upsert(User obj) {
        long id = insert(obj);
        if (id == -1) {
            update(obj);
        }
    }

}
