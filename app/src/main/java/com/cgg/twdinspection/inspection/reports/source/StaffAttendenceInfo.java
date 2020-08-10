package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffAttendenceInfo {

    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("acad_panel_grade")
    @Expose
    private String acadPanelGrade;
    @SerializedName("categ_pos")
    @Expose
    private String categPos;
    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("leaves_bal")
    @Expose
    private String leavesBal;
    @SerializedName("leaves_availed")
    @Expose
    private String leavesAvailed;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("emp_presence")
    @Expose
    private String empPresence;
    @SerializedName("last_week_turn_duties_attended")
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
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("ondepFlag")
    @Expose
    private String ondepFlag;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("presentFlag")
    @Expose
    private String presentFlag;
    @SerializedName("leaves_taken")
    @Expose
    private String leavesTaken;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getAcadPanelGrade() {
        return acadPanelGrade;
    }

    public void setAcadPanelGrade(String acadPanelGrade) {
        this.acadPanelGrade = acadPanelGrade;
    }

    public String getCategPos() {
        return categPos;
    }

    public void setCategPos(String categPos) {
        this.categPos = categPos;
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

    public String getLeavesBal() {
        return leavesBal;
    }

    public void setLeavesBal(String leavesBal) {
        this.leavesBal = leavesBal;
    }

    public String getLeavesAvailed() {
        return leavesAvailed;
    }

    public void setLeavesAvailed(String leavesAvailed) {
        this.leavesAvailed = leavesAvailed;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getLeavesTaken() {
        return leavesTaken;
    }

    public void setLeavesTaken(String leavesTaken) {
        this.leavesTaken = leavesTaken;
    }
}
