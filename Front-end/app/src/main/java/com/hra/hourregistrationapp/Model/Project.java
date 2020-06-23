package com.hra.hourregistrationapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Project {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "project_name")
    private String projectName;
    @ColumnInfo(name = "project_tag")
    private String projectTag;
    @ForeignKey(entity = Company.class , parentColumns = "id", childColumns = "company_id")
    private int companyId;

    public Project(int id, String projectName, String projectTag, int companyId) {
        this.id = id;
        this.projectName = projectName;
        this.projectTag = projectTag;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTag() {
        return projectTag;
    }

    public void setProjectTag(String projectTag) {
        this.projectTag = projectTag;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int company_id) {
        this.companyId = company_id;
    }
}
