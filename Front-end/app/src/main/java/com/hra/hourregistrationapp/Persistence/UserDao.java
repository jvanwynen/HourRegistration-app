package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hra.hourregistrationapp.Model.User;


@Dao
public abstract class UserDao implements DAO<User> {

    @Query("DELETE FROM user")
    public abstract void deleteAllFromTable();

    @Query("SELECT * FROM user")
    public abstract User getAll();

    @Transaction
    public void upsert(User obj) {
        long id = insert(obj);
        if (id == -1) {
            update(obj);
        }
    }
}
