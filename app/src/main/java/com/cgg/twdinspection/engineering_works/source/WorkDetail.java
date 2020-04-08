package com.cgg.twdinspection.engineering_works.source;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity
public class WorkDetail {

    @PrimaryKey
    @NonNull
    @SerializedName("work_id")
    @Expose
    private Integer workId;
    @SerializedName("staff_dept")
    @Expose
    private String staffDept;
    @SerializedName("work_name")
    @Expose
    private String workName;
    @SerializedName("extension_time")
    @Expose
    private String extensionTime;
    @SerializedName("tech_sanction_date")
    @Expose
    private String techSanctionDate;
    @SerializedName("estimate_cost")
    @Expose
    private String estimateCost;
    @SerializedName("gp_name")
    @Expose
    private String gpName;
    @SerializedName("staff_ee")
    @Expose
    private String staffEe;
    @SerializedName("mand_name")
    @Expose
    private String mandName;
    @SerializedName("vill_name")
    @Expose
    private String villName;
    @SerializedName("vill_id")
    @Expose
    private String villId;
    @SerializedName("assembly_cont_id")
    @Expose
    private String assemblyContId;

    @SerializedName("assembly_const_name")
    @Expose
    private String assemblyConstName;
    @SerializedName("target_date")
    @Expose
    private String targetDate;
    @SerializedName("actual_expenditure_incurred")
    @Expose
    private String actualExpenditureIncurred;
    @SerializedName("dist_name")
    @Expose
    private String distName;
    @SerializedName("staff_mandal_name")
    @Expose
    private String staffMandalName;
    @SerializedName("staff_mandal_id")
    @Expose
    private String staffMandalId;
    @SerializedName("percentage_of_financial_progress_achieved")
    @Expose
    private String percentageOfFinancialProgressAchieved;
    @SerializedName("gp_id")
    @Expose
    private String gpId;
    @SerializedName("sanction_date")
    @Expose
    private String sanctionDate;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("commence_date")
    @Expose
    private String commenceDate;
    @SerializedName("staff_dyee")
    @Expose
    private String staffDyee;
    @SerializedName("staff_aee")
    @Expose
    private String staffAee;
    @SerializedName("mand_id")
    @Expose
    private String mandId;
    @SerializedName("execting_agency")
    @Expose
    private String exectingAgency;
    @SerializedName("area_of_operation")
    @Expose
    private String areaOfOperation;

    public String getStaffDept() {
        return staffDept;
    }

    public void setStaffDept(String staffDept) {
        this.staffDept = staffDept;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getExtensionTime() {
        return extensionTime;
    }

    public void setExtensionTime(String extensionTime) {
        this.extensionTime = extensionTime;
    }

    public String getTechSanctionDate() {
        return techSanctionDate;
    }

    public void setTechSanctionDate(String techSanctionDate) {
        this.techSanctionDate = techSanctionDate;
    }

    public String getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(String estimateCost) {
        this.estimateCost = estimateCost;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public String getStaffEe() {
        return staffEe;
    }

    public void setStaffEe(String staffEe) {
        this.staffEe = staffEe;
    }

    public String getMandName() {
        return mandName;
    }

    public void setMandName(String mandName) {
        this.mandName = mandName;
    }

    public String getVillName() {
        return villName;
    }

    public void setVillName(String villName) {
        this.villName = villName;
    }

    public String getVillId() {
        return villId;
    }

    public void setVillId(String villId) {
        this.villId = villId;
    }

    public String getAssemblyContId() {
        return assemblyContId;
    }

    public void setAssemblyContId(String assemblyContId) {
        this.assemblyContId = assemblyContId;
    }

    @NotNull
    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(@NotNull Integer workId) {
        this.workId = workId;
    }

    public String getAssemblyConstName() {
        return assemblyConstName;
    }

    public void setAssemblyConstName(String assemblyConstName) {
        this.assemblyConstName = assemblyConstName;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getActualExpenditureIncurred() {
        return actualExpenditureIncurred;
    }

    public void setActualExpenditureIncurred(String actualExpenditureIncurred) {
        this.actualExpenditureIncurred = actualExpenditureIncurred;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getStaffMandalName() {
        return staffMandalName;
    }

    public void setStaffMandalName(String staffMandalName) {
        this.staffMandalName = staffMandalName;
    }

    public String getStaffMandalId() {
        return staffMandalId;
    }

    public void setStaffMandalId(String staffMandalId) {
        this.staffMandalId = staffMandalId;
    }

    public String getPercentageOfFinancialProgressAchieved() {
        return percentageOfFinancialProgressAchieved;
    }

    public void setPercentageOfFinancialProgressAchieved(String percentageOfFinancialProgressAchieved) {
        this.percentageOfFinancialProgressAchieved = percentageOfFinancialProgressAchieved;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getCommenceDate() {
        return commenceDate;
    }

    public void setCommenceDate(String commenceDate) {
        this.commenceDate = commenceDate;
    }

    public String getStaffDyee() {
        return staffDyee;
    }

    public void setStaffDyee(String staffDyee) {
        this.staffDyee = staffDyee;
    }

    public String getStaffAee() {
        return staffAee;
    }

    public void setStaffAee(String staffAee) {
        this.staffAee = staffAee;
    }

    public String getMandId() {
        return mandId;
    }

    public void setMandId(String mandId) {
        this.mandId = mandId;
    }

    public String getExectingAgency() {
        return exectingAgency;
    }

    public void setExectingAgency(String exectingAgency) {
        this.exectingAgency = exectingAgency;
    }

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        this.areaOfOperation = areaOfOperation;
    }

}