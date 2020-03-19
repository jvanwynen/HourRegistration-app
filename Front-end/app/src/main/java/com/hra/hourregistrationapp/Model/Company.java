package com.hra.hourregistrationapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Company {

    String name, password;

    @PrimaryKey
    int id;

    public Company(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
