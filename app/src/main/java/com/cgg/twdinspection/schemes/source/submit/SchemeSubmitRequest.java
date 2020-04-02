package com.cgg.twdinspection.schemes.source.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemeSubmitRequest {
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
    @SerializedName("ben_id")
    @Expose
    private String benId;
    @SerializedName("ben_name")
    @Expose
    private String benName;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("unit_cost")
    @Expose
    private long unitCost;
    @SerializedName("subsidy")
    @Expose
    private long subsidy;
    @SerializedName("bank_loan")
    @Expose
    private long bankLoan;
    @SerializedName("contribution")
    @Expose
    private long contribution;
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @SerializedName("status_value")
    @Expose
    private String statusValue;
    @SerializedName("Status_field_match")
    @Expose
    private String statusFieldMatch;
    @SerializedName("Remarks_Id")
    @Expose
    private String remarksId;
    @SerializedName("Remarks_Type")
    @Expose
    private String remarksType;

    @SerializedName("scheme_Id")
    @Expose
    private String schemeId;

    @SerializedName("scheme_type")
    @Expose
    private String schemeType;
    @SerializedName("Device_Id")
    @Expose
    private String deviceId;

    @SerializedName("Version_No")
    @Expose
    private String versionNo;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
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

    public String getBenId() {
        return benId;
    }

    public void setBenId(String benId) {
        this.benId = benId;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(long unitCost) {
        this.unitCost = unitCost;
    }

    public long getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(long subsidy) {
        this.subsidy = subsidy;
    }

    public long getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(long bankLoan) {
        this.bankLoan = bankLoan;
    }

    public long getContribution() {
        return contribution;
    }

    public void setContribution(long contribution) {
        this.contribution = contribution;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusFieldMatch() {
        return statusFieldMatch;
    }

    public void setStatusFieldMatch(String statusFieldMatch) {
        this.statusFieldMatch = statusFieldMatch;
    }

    public String getRemarksId() {
        return remarksId;
    }

    public void setRemarksId(String remarksId) {
        this.remarksId = remarksId;
    }

    public String getRemarksType() {
        return remarksType;
    }

    public void setRemarksType(String remarksType) {
        this.remarksType = remarksType;
    }
}
