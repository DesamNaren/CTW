package com.cgg.twdinspection.engineering_works.reports.source;

import com.cgg.twdinspection.gcc.reports.source.ReportPhoto;
import com.cgg.twdinspection.inspection.reports.source.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportWorkDetails {
    @SerializedName("work_name")
    @Expose
    private String workName;
    @SerializedName("extension_time")
    @Expose
    private String extensionTime;
    @SerializedName("photos")
    @Expose
    private List<ReportPhoto> photos = null;
    @SerializedName("qual_care")
    @Expose
    private String qualCare;
    @SerializedName("gp_name")
    @Expose
    private String gpName;
    @SerializedName("staff_ee")
    @Expose
    private String staffEe;
    @SerializedName("Version_No")
    @Expose
    private String versionNo;
    @SerializedName("mand_name")
    @Expose
    private String mandName;
    @SerializedName("vill_name")
    @Expose
    private String villName;
    @SerializedName("scheme_id")
    @Expose
    private String schemeId;
    @SerializedName("vill_id")
    @Expose
    private String villId;
    @SerializedName("assembly_cont_id")
    @Expose
    private String assemblyContId;
    @SerializedName("sector_other_value")
    @Expose
    private String sectorOtherValue;
    @SerializedName("officerId")
    @Expose
    private String officerId;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("target_date")
    @Expose
    private String targetDate;
    @SerializedName("staff_mandal_name")
    @Expose
    private String staffMandalName;
    @SerializedName("satisfaction_level")
    @Expose
    private String satisfactionLevel;
    @SerializedName("staff_dept_name")
    @Expose
    private String staffDeptName;
    @SerializedName("work_in_prog_id")
    @Expose
    private String workInProgId;
    @SerializedName("qual_mat")
    @Expose
    private String qualMat;
    @SerializedName("workmen_skill")
    @Expose
    private String workmenSkill;
    @SerializedName("officerName")
    @Expose
    private String officerName;
    @SerializedName("staff_aee")
    @Expose
    private String staffAee;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("execting_agency")
    @Expose
    private String exectingAgency;
    @SerializedName("sector_name")
    @Expose
    private String sectorName;
    @SerializedName("surface_finish")
    @Expose
    private String surfaceFinish;
    @SerializedName("photo_key_id")
    @Expose
    private String photoKeyId;
    @SerializedName("overall_exp")
    @Expose
    private String overallExp;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("act_exp_incurred")
    @Expose
    private String actExpIncurred;
    @SerializedName("stage_of_work")
    @Expose
    private String stageOfWork;
    @SerializedName("tech_sanction_date")
    @Expose
    private String techSanctionDate;
    @SerializedName("estimate_cost")
    @Expose
    private String estimateCost;
    @SerializedName("Device_Id")
    @Expose
    private String deviceId;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("assembly_const_name")
    @Expose
    private String assemblyConstName;
    @SerializedName("observation")
    @Expose
    private String observation;
    @SerializedName("dist_name")
    @Expose
    private String distName;
    @SerializedName("staff_mandal_id")
    @Expose
    private String staffMandalId;
    @SerializedName("gp_id")
    @Expose
    private String gpId;
    @SerializedName("scheme_name")
    @Expose
    private String schemeName;
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
    @SerializedName("phys_progress_rating")
    @Expose
    private String physProgressRating;
    @SerializedName("sector_id")
    @Expose
    private String sectorId;
    @SerializedName("place_of_work")
    @Expose
    private String placeOfWork;
    @SerializedName("fin_progress_achieved")
    @Expose
    private String finProgressAchieved;
    @SerializedName("mand_id")
    @Expose
    private String mandId;
    @SerializedName("area_of_operation")
    @Expose
    private String areaOfOperation;
    @SerializedName("work_in_prog_name")
    @Expose
    private String workInProgName;

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

    public List<ReportPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ReportPhoto> photos) {
        this.photos = photos;
    }

    public String getQualCare() {
        return qualCare;
    }

    public void setQualCare(String qualCare) {
        this.qualCare = qualCare;
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

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
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

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
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

    public String getSectorOtherValue() {
        return sectorOtherValue;
    }

    public void setSectorOtherValue(String sectorOtherValue) {
        this.sectorOtherValue = sectorOtherValue;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getStaffMandalName() {
        return staffMandalName;
    }

    public void setStaffMandalName(String staffMandalName) {
        this.staffMandalName = staffMandalName;
    }

    public String getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(String satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }

    public String getStaffDeptName() {
        return staffDeptName;
    }

    public void setStaffDeptName(String staffDeptName) {
        this.staffDeptName = staffDeptName;
    }

    public String getWorkInProgId() {
        return workInProgId;
    }

    public void setWorkInProgId(String workInProgId) {
        this.workInProgId = workInProgId;
    }

    public String getQualMat() {
        return qualMat;
    }

    public void setQualMat(String qualMat) {
        this.qualMat = qualMat;
    }

    public String getWorkmenSkill() {
        return workmenSkill;
    }

    public void setWorkmenSkill(String workmenSkill) {
        this.workmenSkill = workmenSkill;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getStaffAee() {
        return staffAee;
    }

    public void setStaffAee(String staffAee) {
        this.staffAee = staffAee;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExectingAgency() {
        return exectingAgency;
    }

    public void setExectingAgency(String exectingAgency) {
        this.exectingAgency = exectingAgency;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSurfaceFinish() {
        return surfaceFinish;
    }

    public void setSurfaceFinish(String surfaceFinish) {
        this.surfaceFinish = surfaceFinish;
    }

    public String getPhotoKeyId() {
        return photoKeyId;
    }

    public void setPhotoKeyId(String photoKeyId) {
        this.photoKeyId = photoKeyId;
    }

    public String getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(String overallExp) {
        this.overallExp = overallExp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getActExpIncurred() {
        return actExpIncurred;
    }

    public void setActExpIncurred(String actExpIncurred) {
        this.actExpIncurred = actExpIncurred;
    }

    public String getStageOfWork() {
        return stageOfWork;
    }

    public void setStageOfWork(String stageOfWork) {
        this.stageOfWork = stageOfWork;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getAssemblyConstName() {
        return assemblyConstName;
    }

    public void setAssemblyConstName(String assemblyConstName) {
        this.assemblyConstName = assemblyConstName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getStaffMandalId() {
        return staffMandalId;
    }

    public void setStaffMandalId(String staffMandalId) {
        this.staffMandalId = staffMandalId;
    }

    public String getGpId() {
        return gpId;
    }

    public void setGpId(String gpId) {
        this.gpId = gpId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
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

    public String getPhysProgressRating() {
        return physProgressRating;
    }

    public void setPhysProgressRating(String physProgressRating) {
        this.physProgressRating = physProgressRating;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getFinProgressAchieved() {
        return finProgressAchieved;
    }

    public void setFinProgressAchieved(String finProgressAchieved) {
        this.finProgressAchieved = finProgressAchieved;
    }

    public String getMandId() {
        return mandId;
    }

    public void setMandId(String mandId) {
        this.mandId = mandId;
    }

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        this.areaOfOperation = areaOfOperation;
    }

    public String getWorkInProgName() {
        return workInProgName;
    }

    public void setWorkInProgName(String workInProgName) {
        this.workInProgName = workInProgName;
    }

}
