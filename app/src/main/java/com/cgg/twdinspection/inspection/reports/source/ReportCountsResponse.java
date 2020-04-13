package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportCountsResponse {

    @SerializedName("engineering_works")
    @Expose
    private Integer engineeringWorks;
    @SerializedName("schools")
    @Expose
    private Integer schools;
    @SerializedName("gcc")
    @Expose
    private Integer gcc;
    @SerializedName("schemes")
    @Expose
    private Integer schemes;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public Integer getEngineeringWorks() {
        return engineeringWorks;
    }

    public void setEngineeringWorks(Integer engineeringWorks) {
        this.engineeringWorks = engineeringWorks;
    }

    public Integer getSchools() {
        return schools;
    }

    public void setSchools(Integer schools) {
        this.schools = schools;
    }

    public Integer getGcc() {
        return gcc;
    }

    public void setGcc(Integer gcc) {
        this.gcc = gcc;
    }

    public Integer getSchemes() {
        return schemes;
    }

    public void setSchemes(Integer schemes) {
        this.schemes = schemes;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
