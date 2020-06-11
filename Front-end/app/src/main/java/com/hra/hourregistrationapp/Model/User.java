package com.hra.hourregistrationapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    private int companyId;

    @Ignore
    private int admin, calendarID;

    @Ignore
    private String firstName, lastName;

    public User(String id, int admin, int calendarID, int companyId, String firstName, String lastName) {
        this.id = id;
        this.admin = admin;
        this.calendarID = calendarID;
        this.companyId = companyId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Ignore
    public User(String id){
        this.id = id;
    }

    public User(String id, int companyId){
        this.id = id;
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }
}
