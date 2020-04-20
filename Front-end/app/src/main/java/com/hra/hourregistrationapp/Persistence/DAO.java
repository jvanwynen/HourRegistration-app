package com.hra.hourregistrationapp.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

//This is interface defines all base methods for DAO's
public interface DAO<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insert(T obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public List<Long> insert(List<T> obj);

    @Update
    public void update(T obj);

    @Update
    public void update(List<T> obj);

//    @Transaction
//    public void upsert(T obj);
//
//    @Transaction
//    public void upsert(List< T> objList);
}