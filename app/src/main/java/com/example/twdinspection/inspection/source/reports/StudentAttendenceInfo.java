package com.example.twdinspection.inspection.source.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAttendenceInfo {
    @SerializedName("attendence_marked")
    @Expose
    private String attendenceMarked;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("class_type")
    @Expose
    private String classType;
    @SerializedName("r_id")
    @Expose
    private String rId;
    @SerializedName("flag_completed")
    @Expose
    private String flagCompleted;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("student_count_during_inspection")
    @Expose
    private String studentCountDuringInspection;
    @SerializedName("student_count_in_register")
    @Expose
    private String studentCountInRegister;
    @SerializedName("total_students")
    @Expose
    private String totalStudents;
    @SerializedName("variance")
    @Expose
    private String variance;

    public String getAttendenceMarked() {
        return attendenceMarked;
    }

    public void setAttendenceMarked(String attendenceMarked) {
        this.attendenceMarked = attendenceMarked;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public String getFlagCompleted() {
        return flagCompleted;
    }

    public void setFlagCompleted(String flagCompleted) {
        this.flagCompleted = flagCompleted;
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

    public String getStudentCountDuringInspection() {
        return studentCountDuringInspection;
    }

    public void setStudentCountDuringInspection(String studentCountDuringInspection) {
        this.studentCountDuringInspection = studentCountDuringInspection;
    }

    public String getStudentCountInRegister() {
        return studentCountInRegister;
    }

    public void setStudentCountInRegister(String studentCountInRegister) {
        this.studentCountInRegister = studentCountInRegister;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getVariance() {
        return variance;
    }

    public void setVariance(String variance) {
        this.variance = variance;
    }

}
