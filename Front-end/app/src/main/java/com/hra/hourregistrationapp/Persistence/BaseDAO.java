package com.hra.hourregistrationapp.Persistence;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

//This is interface defines all base methods for DAO's
public interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<T> obj);

    @Update
    public abstract void update(List<T> obj);

    @Transaction
    public void upsert(List<T> objList) ;
}