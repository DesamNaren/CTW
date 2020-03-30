package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicGradeEntity {

    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("grade_a_stu_count")
    @Expose
    private String gradeAStuCount;
    @SerializedName("grade_c_stu_count")
    @Expose
    private String gradeCStuCount;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("grade_aplus_stu_count")
    @Expose
    private String gradeAplusStuCount;
    @SerializedName("grade_d_stu_count")
    @Expose
    private String gradeDStuCount;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("grade_e_stu_count")
    @Expose
    private String gradeEStuCount;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("grade_bplus_stu_count")
    @Expose
    private String gradeBplusStuCount;
    @SerializedName("class_type")
    @Expose
    private String classType;
    @SerializedName("inspection_id")
    @Expose
    private Integer inspectionId;
    @SerializedName("total_students")
    @Expose
    private String totalStudents;
    @SerializedName("grade_b_stu_count")
    @Expose
    private String gradeBStuCount;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getGradeAStuCount() {
        return gradeAStuCount;
    }

    public void setGradeAStuCount(String gradeAStuCount) {
        this.gradeAStuCount = gradeAStuCount;
    }

    public String getGradeCStuCount() {
        return gradeCStuCount;
    }

    public void setGradeCStuCount(String gradeCStuCount) {
        this.gradeCStuCount = gradeCStuCount;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getGradeAplusStuCount() {
        return gradeAplusStuCount;
    }

    public void setGradeAplusStuCount(String gradeAplusStuCount) {
        this.gradeAplusStuCount = gradeAplusStuCount;
    }

    public String getGradeDStuCount() {
        return gradeDStuCount;
    }

    public void setGradeDStuCount(String gradeDStuCount) {
        this.gradeDStuCount = gradeDStuCount;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getGradeEStuCount() {
        return gradeEStuCount;
    }

    public void setGradeEStuCount(String gradeEStuCount) {
        this.gradeEStuCount = gradeEStuCount;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getGradeBplusStuCount() {
        return gradeBplusStuCount;
    }

    public void setGradeBplusStuCount(String gradeBplusStuCount) {
        this.gradeBplusStuCount = gradeBplusStuCount;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getGradeBStuCount() {
        return gradeBStuCount;
    }

    public void setGradeBStuCount(String gradeBStuCount) {
        this.gradeBStuCount = gradeBStuCount;
    }
}
