package com.example.twdinspection.schemes.source.bendetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryDetail {
    @SerializedName("scheme_Id")
    @Expose
    private String schemeID;
    @SerializedName("bank_Loan")
    @Expose
    private Long bankLoan;
    @SerializedName("ben_Name")
    @Expose
    private String benName;
    @SerializedName("ben_ID")
    @Expose
    private String benID;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("contribution")
    @Expose
    private Long contribution;
    @SerializedName("unit_Cost")
    @Expose
    private Long unitCost;
    @SerializedName("subsidy")
    @Expose
    private Long subsidy;
    @SerializedName("status")
    @Expose
    private String status;


    public String getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(String schemeID) {
        this.schemeID = schemeID;
    }

    public Long getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(Long bankLoan) {
        this.bankLoan = bankLoan;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenID() {
        return benID;
    }

    public void setBenID(String benID) {
        this.benID = benID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Long getContribution() {
        return contribution;
    }

    public void setContribution(Long contribution) {
        this.contribution = contribution;
    }

    public Long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public Long getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Long subsidy) {
        this.subsidy = subsidy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
