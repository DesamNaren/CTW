package com.example.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralInfo {

    @SerializedName("HM_HWO_presence")
    @Expose
    private String hMHWOPresence;
    @SerializedName("captureDistance")
    @Expose
    private String captureDistance;
    @SerializedName("having_headquarters")
    @Expose
    private String havingHeadquarters;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("leaveType")
    @Expose
    private String leaveType;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("staffQuarters")
    @Expose
    private String staffQuarters;

    public String getHMHWOPresence() {
        return hMHWOPresence;
    }

    public void setHMHWOPresence(String hMHWOPresence) {
        this.hMHWOPresence = hMHWOPresence;
    }

    public String getCaptureDistance() {
        return captureDistance;
    }

    public void setCaptureDistance(String captureDistance) {
        this.captureDistance = captureDistance;
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

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getStaffQuarters() {
        return staffQuarters;
    }

    public void setStaffQuarters(String staffQuarters) {
        this.staffQuarters = staffQuarters;
    }
}
