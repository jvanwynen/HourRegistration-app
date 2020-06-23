package com.hra.hourregistrationapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class Company {

    @ColumnInfo(name = "name")
    private String companyname;

    @Ignore
    private String password;

    @PrimaryKey
    private int id;

    public Company(int id, String companyName, String password) {
        this(id, companyName);
        this.password = password;
    }
    public Company(int id, String companyname) {
        this.id = id;
        this.companyname = companyname;
    }

    public Company(String companyname, String password) {
        this.companyname = companyname;
        this.password = password;
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
        return companyname ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return id == company.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
