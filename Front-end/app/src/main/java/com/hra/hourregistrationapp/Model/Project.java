package com.hra.hourregistrationapp.Model;

import java.util.ArrayList;

public class Project {

    private int id;
    private String name;
    private int hours;
    private int tag;

    public Project(String name, int hours, int tag) {
        this.name = name;
        this.hours = hours;
        this.tag = tag;
    }

    public Project(int id, String name, int hours, int tag) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.tag = tag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
