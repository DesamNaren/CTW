package com.example.twdinspection.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeeResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_salary")
    @Expose
    private String employeeSalary;
    @SerializedName("employee_age")
    @Expose
    private String employeeAge;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    private boolean presentFlag;
    private boolean absentFlag;
    private boolean ondepFlag;

    public boolean isAbsentFlag() {
        return absentFlag;
    }

    public void setAbsentFlag(boolean absentFlag) {
        this.absentFlag = absentFlag;
    }

    public boolean isOndepFlag() {
        return ondepFlag;
    }

    public void setOndepFlag(boolean ondepFlag) {
        this.ondepFlag = ondepFlag;
    }

    public boolean isPresentFlag() {
        return presentFlag;
    }

    public void setPresentFlag(boolean presentFlag) {
        this.presentFlag = presentFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
