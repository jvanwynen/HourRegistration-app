package com.hra.hourregistrationapp.Persistence;

import com.hra.hourregistrationapp.Model.Project;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

/*
This DAO is responsible for communication with local database for all Company related queries
 */
@Dao
public abstract class ProjectDao implements DAO<Project> {

    @Query("SELECT * FROM Project")
    public abstract LiveData<List<Project>> getAll();

    @Query("SELECT * FROM project WHERE id = :projectId")
    public abstract Project getProjectById(int projectId);

    @Query("DELETE FROM Project")
    public abstract void DeleteAll();

    @Transaction
    public void upsert(List<Project> objList) {
        List<Long> insertResult = insert(objList);
        List<Project> updateList = new ArrayList<>();

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
