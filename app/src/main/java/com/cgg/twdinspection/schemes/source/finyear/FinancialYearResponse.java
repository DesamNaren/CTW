package com.cgg.twdinspection.schemes.source.finyear;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinancialYearResponse {

    @SerializedName("fin_years")
    @Expose
    private List<FinancialYearsEntity> finYears = null;
    @SerializedName("status_Code")
    @Expose
    private String statusCode;
    @SerializedName("status_Message")
    @Expose
    private String statusMessage;

    public List<FinancialYearsEntity> getFinYears() {
        return finYears;
    }

    public void setFinYears(List<FinancialYearsEntity> finYears) {
        this.finYears = finYears;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
