package com.hra.hourregistrationapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Company {

    @ColumnInfo(name = "name")
    String companyname;

    @Ignore
    String password;

    @PrimaryKey
    int id;

    public Company(String companyName, String password) {
        this(companyName);
        this.password = password;
    }
    public Company(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyname='" + companyname + '\'' +
                '}';
    }
}
