package com.example.twdinspection.inspection.source.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffAttendenceInfo {
    @SerializedName("absentFlag")
    @Expose
    private String absentFlag;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("emp_presence")
    @Expose
    private String empPresence;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("inst_name")
    @Expose
    private String instName;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("ondepFlag")
    @Expose
    private String ondepFlag;
    @SerializedName("presentFlag")
    @Expose
    private String presentFlag;
    @SerializedName("yday_duty_allotted")
    @Expose
    private String ydayDutyAllotted;

    public String getAbsentFlag() {
        return absentFlag;
    }

    public void setAbsentFlag(String absentFlag) {
        this.absentFlag = absentFlag;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPresence() {
        return empPresence;
    }

    public void setEmpPresence(String empPresence) {
        this.empPresence = empPresence;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getOndepFlag() {
        return ondepFlag;
    }

    public void setOndepFlag(String ondepFlag) {
        this.ondepFlag = ondepFlag;
    }

    public String getPresentFlag() {
        return presentFlag;
    }

    public void setPresentFlag(String presentFlag) {
        this.presentFlag = presentFlag;
    }

    public String getYdayDutyAllotted() {
        return ydayDutyAllotted;
    }

    public void setYdayDutyAllotted(String ydayDutyAllotted) {
        this.ydayDutyAllotted = ydayDutyAllotted;
    }
}
