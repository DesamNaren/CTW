package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralInfo {

    @SerializedName("mandalName")
    @Expose
    private String mandalName;
    @SerializedName("captureDistance")
    @Expose
    private String captureDistance;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("movementRegisterEntry")
    @Expose
    private String movementRegisterEntry;
    @SerializedName("staffQuarters")
    @Expose
    private String staffQuarters;
    @SerializedName("HM_HWO_presence")
    @Expose
    private String hMHWOPresence;
    @SerializedName("capturetype")
    @Expose
    private String capturetype;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("stayingFacilitiesType")
    @Expose
    private String stayingFacilitiesType;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("leaveType")
    @Expose
    private String leaveType;
    @SerializedName("having_headquarters")
    @Expose
    private String havingHeadquarters;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("instName")
    @Expose
    private String instName;

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getCaptureDistance() {
        return captureDistance;
    }

    public void setCaptureDistance(String captureDistance) {
        this.captureDistance = captureDistance;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getMovementRegisterEntry() {
        return movementRegisterEntry;
    }

    public void setMovementRegisterEntry(String movementRegisterEntry) {
        this.movementRegisterEntry = movementRegisterEntry;
    }

    public String getStaffQuarters() {
        return staffQuarters;
    }

    public void setStaffQuarters(String staffQuarters) {
        this.staffQuarters = staffQuarters;
    }

    public String getHMHWOPresence() {
        return hMHWOPresence;
    }

    public void setHMHWOPresence(String hMHWOPresence) {
        this.hMHWOPresence = hMHWOPresence;
    }

    public String getCapturetype() {
        return capturetype;
    }

    public void setCapturetype(String capturetype) {
        this.capturetype = capturetype;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getStayingFacilitiesType() {
        return stayingFacilitiesType;
    }

    public void setStayingFacilitiesType(String stayingFacilitiesType) {
        this.stayingFacilitiesType = stayingFacilitiesType;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getHavingHeadquarters() {
        return havingHeadquarters;
    }

    public void setHavingHeadquarters(String havingHeadquarters) {
        this.havingHeadquarters = havingHeadquarters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}
