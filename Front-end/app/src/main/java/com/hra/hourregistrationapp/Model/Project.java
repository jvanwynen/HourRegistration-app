package com.hra.hourregistrationapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity
public class Project {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "project_name")
    private String projectname;
    @ColumnInfo(name = "project_tag")
    private String project_tag;
    @ForeignKey(entity = Company.class , parentColumns = "id", childColumns = "company_id")
    private int company_id;

    public Project(int id, String projectname, String project_tag, int company_id) {
        this.id = id;
        this.projectname = projectname;
        this.project_tag = project_tag;
        this.company_id = company_id;
    }

    public int getId() {
        return id;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProject_tag() {
        return project_tag;
    }

    public void setProject_tag(String project_tag) {
        this.project_tag = project_tag;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}
