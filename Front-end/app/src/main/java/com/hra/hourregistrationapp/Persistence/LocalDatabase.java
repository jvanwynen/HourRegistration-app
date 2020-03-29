package com.hra.hourregistrationapp.Persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Model.User;

/*
this class defines the local database, and its migrations to newer versions
 */
@Database(entities = {User.class, Company.class}, version = 2, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

   public abstract UserDao userDao();
   public abstract CompanyDao companyDao();

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LocalDatabase.class, "HRA").allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL(
                    "CREATE TABLE company_new (id INTEGER NOT NULL, name TEXT, PRIMARY KEY(id))");
            // Copy the data
            database.execSQL(
                    "INSERT INTO company_new (id, name) SELECT id, name FROM company");
                    // Remove the old table
                    database.execSQL("DROP TABLE company");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE company_new RENAME TO company");
        }
    };

}
