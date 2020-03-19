package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hra.hourregistrationapp.Model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert
    void insertAnswers(User... users);

}
