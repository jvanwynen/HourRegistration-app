package com.hra.hourregistrationapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigInteger;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    @Ignore
    private int admin, calendarID, companyID;
    @Ignore
    private String firstName, lastName;

    public User(String id, int admin, int calendarID, int companyID, String firstName, String lastName) {
        this.id = id;
        this.admin = admin;
        this.calendarID = calendarID;
        this.companyID = companyID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String id, int companyID){
        this.id = id;
        this.companyID = companyID;
    }

    public User(String id){
        this.id = id;
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

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
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
