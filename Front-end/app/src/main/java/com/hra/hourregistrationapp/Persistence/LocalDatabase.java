package com.hra.hourregistrationapp.Persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;


@Database(entities = {User.class, Company.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

   public abstract UserDao userDao();
   public abstract CompanyDao companyDao();

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LocalDatabase.class, "HRA").allowMainThreadQueries().build();
        }
        return instance;
    }
}
