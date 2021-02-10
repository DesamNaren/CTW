package com.cgg.twdinspection.schemes.source.bendetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeneficiaryReport {

    @SerializedName("beneficiary_Details")
    @Expose
    private List<BeneficiaryDetail> beneficiaryDetails = null;
    @SerializedName("status_Code")
    @Expose
    private String statusCode;
    @SerializedName("status_Message")
    @Expose
    private String statusMessage;

    public List<BeneficiaryDetail> getBeneficiaryDetails() {
        return beneficiaryDetails;
    }

    public void setBeneficiaryDetails(List<BeneficiaryDetail> beneficiaryDetails) {
        this.beneficiaryDetails = beneficiaryDetails;
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


