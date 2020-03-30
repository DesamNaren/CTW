package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffAttendenceInfo {
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("emp_presence")
    @Expose
    private String empPresence;
    @SerializedName("last_week_teacher_attended")
    @Expose
    private String lastWeekTeacherAttended;
    @SerializedName("absentFlag")
    @Expose
    private String absentFlag;
    @SerializedName("yday_duty_allotted")
    @Expose
    private String ydayDutyAllotted;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("inst_name")
    @Expose
    private String instName;
    @SerializedName("ondepFlag")
    @Expose
    private String ondepFlag;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("presentFlag")
    @Expose
    private String presentFlag;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getEmpPresence() {
        return empPresence;
    }

    public void setEmpPresence(String empPresence) {
        this.empPresence = empPresence;
    }

    public String getLastWeekTeacherAttended() {
        return lastWeekTeacherAttended;
    }

    public void setLastWeekTeacherAttended(String lastWeekTeacherAttended) {
        this.lastWeekTeacherAttended = lastWeekTeacherAttended;
    }

    public String getAbsentFlag() {
        return absentFlag;
    }

    public void setAbsentFlag(String absentFlag) {
        this.absentFlag = absentFlag;
    }

    public String getYdayDutyAllotted() {
        return ydayDutyAllotted;
    }

    public void setYdayDutyAllotted(String ydayDutyAllotted) {
        this.ydayDutyAllotted = ydayDutyAllotted;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getOndepFlag() {
        return ondepFlag;
    }

    public void setOndepFlag(String ondepFlag) {
        this.ondepFlag = ondepFlag;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPresentFlag() {
        return presentFlag;
    }

    public void setPresentFlag(String presentFlag) {
        this.presentFlag = presentFlag;
    }
}
