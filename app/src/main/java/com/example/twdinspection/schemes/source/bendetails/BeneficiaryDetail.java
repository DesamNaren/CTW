package com.example.twdinspection.schemes.source.bendetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryDetail implements Parcelable {
    @SerializedName("scheme_Id")
    @Expose
    private String schemeId;
    @SerializedName("scheme_type")
    @Expose
    private String schemeType;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("mandal_id")
    @Expose
    private String mandalId;
    @SerializedName("mandal_name")
    @Expose
    private String mandalName;
    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("village_name")
    @Expose
    private String villageName;
    @SerializedName("fin_year_id")
    @Expose
    private String finYearId;
    @SerializedName("fin_year")
    @Expose
    private String finYear;


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

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMandalId() {
        return mandalId;
    }

    public void setMandalId(String mandalId) {
        this.mandalId = mandalId;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(String finYearId) {
        this.finYearId = finYearId;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

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
        dest.writeString(this.schemeId);
        dest.writeString(this.schemeType);
        dest.writeString(this.officerId);
        dest.writeString(this.inspectionTime);
        dest.writeString(this.distId);
        dest.writeString(this.districtName);
        dest.writeString(this.mandalId);
        dest.writeString(this.mandalName);
        dest.writeString(this.villageId);
        dest.writeString(this.villageName);
        dest.writeString(this.finYearId);
        dest.writeString(this.finYear);
        dest.writeValue(this.bankLoan);
        dest.writeString(this.benName);
        dest.writeString(this.benID);
        dest.writeString(this.activity);
        dest.writeValue(this.contribution);
        dest.writeValue(this.unitCost);
        dest.writeValue(this.subsidy);
        dest.writeString(this.status);
        dest.writeString(this.statusValue);
    }

    public BeneficiaryDetail() {
    }

    protected BeneficiaryDetail(Parcel in) {
        this.schemeId = in.readString();
        this.schemeType = in.readString();
        this.officerId = in.readString();
        this.inspectionTime = in.readString();
        this.distId = in.readString();
        this.districtName = in.readString();
        this.mandalId = in.readString();
        this.mandalName = in.readString();
        this.villageId = in.readString();
        this.villageName = in.readString();
        this.finYearId = in.readString();
        this.finYear = in.readString();
        this.bankLoan = (Long) in.readValue(Long.class.getClassLoader());
        this.benName = in.readString();
        this.benID = in.readString();
        this.activity = in.readString();
        this.contribution = (Long) in.readValue(Long.class.getClassLoader());
        this.unitCost = (Long) in.readValue(Long.class.getClassLoader());
        this.subsidy = (Long) in.readValue(Long.class.getClassLoader());
        this.status = in.readString();
        this.statusValue = in.readString();
    }

    public static final Creator<BeneficiaryDetail> CREATOR = new Creator<BeneficiaryDetail>() {
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
