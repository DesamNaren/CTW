package com.example.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitEngWorksRequest {

    @SerializedName("officerId")
    @Expose
    private String officerId;
    @SerializedName("officerName")
    @Expose
    private String officerName;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("place_of_work")
    @Expose
    private String placeOfWork;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("area_of_operation")
    @Expose
    private String areaOfOperation;
    @SerializedName("dist_name")
    @Expose
    private String distName;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("mand_name")
    @Expose
    private String mandName;
    @SerializedName("mand_id")
    @Expose
    private String mandId;
    @SerializedName("gp_name")
    @Expose
    private String gpName;
    @SerializedName("gp_id")
    @Expose
    private String gpId;
    @SerializedName("vill_name")
    @Expose
    private String villName;
    @SerializedName("vill_id")
    @Expose
    private String villId;
    @SerializedName("assembly_const_name")
    @Expose
    private String assemblyConstName;
    @SerializedName("assembly_cont_id")
    @Expose
    private String assemblyContId;
    @SerializedName("sector_id")
    @Expose
    private String sectorId;
    @SerializedName("work_name")
    @Expose
    private String workName;
    @SerializedName("estimate_cost")
    @Expose
    private Double estimateCost;
    @SerializedName("scheme_id")
    @Expose
    private String schemeId;
    @SerializedName("execting_agency")
    @Expose
    private String exectingAgency;
    @SerializedName("sanction_date")
    @Expose
    private String sanctionDate;
    @SerializedName("tech_sanction_date")
    @Expose
    private String techSanctionDate;
    @SerializedName("commence_date")
    @Expose
    private String commenceDate;
    @SerializedName("target_date")
    @Expose
    private String targetDate;
    @SerializedName("extension_time")
    @Expose
    private String extensionTime;
    @SerializedName("staff_dept_name")
    @Expose
    private String staffDeptName;
    @SerializedName("staff_mandal_name")
    @Expose
    private String staffMandalName;
    @SerializedName("staff_mandal_id")
    @Expose
    private String staffMandalId;
    @SerializedName("staff_ee")
    @Expose
    private String staffEe;
    @SerializedName("staff_dyee")
    @Expose
    private String staffDyee;
    @SerializedName("staff_aee")
    @Expose
    private String staffAee;
    @SerializedName("stage_of_work")
    @Expose
    private String stageOfWork;
    @SerializedName("work_in_prog_id")
    @Expose
    private String workInProgId;
    @SerializedName("work_in_prog_name")
    @Expose
    private String workInProgName;
    @SerializedName("phys_progress_rating")
    @Expose
    private String physProgressRating;
    @SerializedName("expenditure")
    @Expose
    private String expenditure;
    @SerializedName("fin_progress_targetted")
    @Expose
    private String finProgressTargetted;
    @SerializedName("act_exp_incurred")
    @Expose
    private String actExpIncurred;
    @SerializedName("fin_progress_achieved")
    @Expose
    private String finProgressAchieved;
    @SerializedName("shortfall")
    @Expose
    private String shortfall;
    @SerializedName("overall_exp")
    @Expose
    private String overallExp;
    @SerializedName("workmen_skill")
    @Expose
    private String workmenSkill;
    @SerializedName("qual_care")
    @Expose
    private String qualCare;
    @SerializedName("qual_mat")
    @Expose
    private String qualMat;
    @SerializedName("surface_finish")
    @Expose
    private String surfaceFinish;
    @SerializedName("observation")
    @Expose
    private String observation;
    @SerializedName("satisfaction_level")
    @Expose
    private String satisfactionLevel;

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        this.areaOfOperation = areaOfOperation;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getMandName() {
        return mandName;
    }

    public void setMandName(String mandName) {
        this.mandName = mandName;
    }

    public String getMandId() {
        return mandId;
    }

    public void setMandId(String mandId) {
        this.mandId = mandId;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
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

    public String getAssemblyConstName() {
        return assemblyConstName;
    }

    public void setAssemblyConstName(String assemblyConstName) {
        this.assemblyConstName = assemblyConstName;
    }

    public String getAssemblyContId() {
        return assemblyContId;
    }

    public void setAssemblyContId(String assemblyContId) {
        this.assemblyContId = assemblyContId;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Double getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(Double estimateCost) {
        this.estimateCost = estimateCost;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getExectingAgency() {
        return exectingAgency;
    }

    public void setExectingAgency(String exectingAgency) {
        this.exectingAgency = exectingAgency;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getTechSanctionDate() {
        return techSanctionDate;
    }

    public void setTechSanctionDate(String techSanctionDate) {
        this.techSanctionDate = techSanctionDate;
    }

    public String getCommenceDate() {
        return commenceDate;
    }

    public void setCommenceDate(String commenceDate) {
        this.commenceDate = commenceDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getExtensionTime() {
        return extensionTime;
    }

    public void setExtensionTime(String extensionTime) {
        this.extensionTime = extensionTime;
    }

    public String getStaffDeptName() {
        return staffDeptName;
    }

    public void setStaffDeptName(String staffDeptName) {
        this.staffDeptName = staffDeptName;
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

    public String getStaffEe() {
        return staffEe;
    }

    public void setStaffEe(String staffEe) {
        this.staffEe = staffEe;
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

    public String getStageOfWork() {
        return stageOfWork;
    }

    public void setStageOfWork(String stageOfWork) {
        this.stageOfWork = stageOfWork;
    }

    public String getWorkInProgId() {
        return workInProgId;
    }

    public void setWorkInProgId(String workInProgId) {
        this.workInProgId = workInProgId;
    }

    public String getWorkInProgName() {
        return workInProgName;
    }

    public void setWorkInProgName(String workInProgName) {
        this.workInProgName = workInProgName;
    }

    public String getPhysProgressRating() {
        return physProgressRating;
    }

    public void setPhysProgressRating(String physProgressRating) {
        this.physProgressRating = physProgressRating;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }

    public String getFinProgressTargetted() {
        return finProgressTargetted;
    }

    public void setFinProgressTargetted(String finProgressTargetted) {
        this.finProgressTargetted = finProgressTargetted;
    }

    public String getActExpIncurred() {
        return actExpIncurred;
    }

    public void setActExpIncurred(String actExpIncurred) {
        this.actExpIncurred = actExpIncurred;
    }

    public String getFinProgressAchieved() {
        return finProgressAchieved;
    }

    public void setFinProgressAchieved(String finProgressAchieved) {
        this.finProgressAchieved = finProgressAchieved;
    }

    public String getShortfall() {
        return shortfall;
    }

    public void setShortfall(String shortfall) {
        this.shortfall = shortfall;
    }

    public String getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(String overallExp) {
        this.overallExp = overallExp;
    }

    public String getWorkmenSkill() {
        return workmenSkill;
    }

    public void setWorkmenSkill(String workmenSkill) {
        this.workmenSkill = workmenSkill;
    }

    public String getQualCare() {
        return qualCare;
    }

    public void setQualCare(String qualCare) {
        this.qualCare = qualCare;
    }

    public String getQualMat() {
        return qualMat;
    }

    public void setQualMat(String qualMat) {
        this.qualMat = qualMat;
    }

    public String getSurfaceFinish() {
        return surfaceFinish;
    }

    public void setSurfaceFinish(String surfaceFinish) {
        this.surfaceFinish = surfaceFinish;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(String satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }
}