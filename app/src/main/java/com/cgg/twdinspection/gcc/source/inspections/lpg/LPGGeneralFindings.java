package com.cgg.twdinspection.gcc.source.inspections.lpg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGGeneralFindings {

    @SerializedName("hygienic_condition")
    @Expose
    private String hygienicCondition;
    @SerializedName("society_manager_inspection")
    @Expose
    private String societyManagerInspection;
    @SerializedName("division_manager_inspection")
    @Expose
    private String divisionManagerInspection;
    @SerializedName("repairs_required")
    @Expose
    private String repairsRequired;
    @SerializedName("repairs_type")
    @Expose
    private String repairsType;
    @SerializedName("cc_cameras_avail")
    @Expose
    private String ccCamerasAvail;
    @SerializedName("society_manager_inspection_date")
    @Expose
    private String societyManagerInspectionDate;
    @SerializedName("division_manager_inspection_date")
    @Expose
    private String divisionManagerInspectionDate;
    @SerializedName("fire_saftey_avail")
    @Expose
    private String fireSafteyAvail;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("computerized_system")
    @Expose
    private String computerizedSystem;

    public String getHygienicCondition() {
        return hygienicCondition;
    }

    public void setHygienicCondition(String hygienicCondition) {
        this.hygienicCondition = hygienicCondition;
    }

    public String getSocietyManagerInspection() {
        return societyManagerInspection;
    }

    public void setSocietyManagerInspection(String societyManagerInspection) {
        this.societyManagerInspection = societyManagerInspection;
    }

    public String getDivisionManagerInspection() {
        return divisionManagerInspection;
    }

    public void setDivisionManagerInspection(String divisionManagerInspection) {
        this.divisionManagerInspection = divisionManagerInspection;
    }

    public String getRepairsRequired() {
        return repairsRequired;
    }

    public void setRepairsRequired(String repairsRequired) {
        this.repairsRequired = repairsRequired;
    }

    public String getRepairsType() {
        return repairsType;
    }

    public void setRepairsType(String repairsType) {
        this.repairsType = repairsType;
    }

    public String getCcCamerasAvail() {
        return ccCamerasAvail;
    }

    public void setCcCamerasAvail(String ccCamerasAvail) {
        this.ccCamerasAvail = ccCamerasAvail;
    }

    public String getSocietyManagerInspectionDate() {
        return societyManagerInspectionDate;
    }

    public void setSocietyManagerInspectionDate(String societyManagerInspectionDate) {
        this.societyManagerInspectionDate = societyManagerInspectionDate;
    }

    public String getDivisionManagerInspectionDate() {
        return divisionManagerInspectionDate;
    }

    public void setDivisionManagerInspectionDate(String divisionManagerInspectionDate) {
        this.divisionManagerInspectionDate = divisionManagerInspectionDate;
    }

    public String getFireSafteyAvail() {
        return fireSafteyAvail;
    }

    public void setFireSafteyAvail(String fireSafteyAvail) {
        this.fireSafteyAvail = fireSafteyAvail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComputerizedSystem() {
        return computerizedSystem;
    }

    public void setComputerizedSystem(String computerizedSystem) {
        this.computerizedSystem = computerizedSystem;
    }

}
