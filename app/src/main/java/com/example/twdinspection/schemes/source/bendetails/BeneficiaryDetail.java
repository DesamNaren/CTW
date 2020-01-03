package com.example.twdinspection.schemes.source.bendetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryDetail implements Parcelable {
    @SerializedName("scheme_Id")
    @Expose
    private String schemeID;
    @SerializedName("scheme_type")
    @Expose
    private String schemeType;
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
    @SerializedName("status_Value")
    @Expose
    private String statusValue;


    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schemeID);
        dest.writeValue(this.bankLoan);
        dest.writeString(this.benName);
        dest.writeString(this.benID);
        dest.writeString(this.activity);
        dest.writeValue(this.contribution);
        dest.writeValue(this.unitCost);
        dest.writeValue(this.subsidy);
        dest.writeString(this.status);
        dest.writeString(this.statusValue);
        dest.writeString(this.schemeType);
    }

    public BeneficiaryDetail() {
    }

    protected BeneficiaryDetail(Parcel in) {
        this.schemeID = in.readString();
        this.bankLoan = (Long) in.readValue(Long.class.getClassLoader());
        this.benName = in.readString();
        this.benID = in.readString();
        this.activity = in.readString();
        this.contribution = (Long) in.readValue(Long.class.getClassLoader());
        this.unitCost = (Long) in.readValue(Long.class.getClassLoader());
        this.subsidy = (Long) in.readValue(Long.class.getClassLoader());
        this.status = in.readString();
        this.statusValue = in.readString();
        this.schemeType = in.readString();
    }

    public static final Parcelable.Creator<BeneficiaryDetail> CREATOR = new Parcelable.Creator<BeneficiaryDetail>() {
        @Override
        public BeneficiaryDetail createFromParcel(Parcel source) {
            return new BeneficiaryDetail(source);
        }

        @Override
        public BeneficiaryDetail[] newArray(int size) {
            return new BeneficiaryDetail[size];
        }
    };
}
