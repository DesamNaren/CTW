package com.example.twdinspection.inspection.reports.source;

import com.example.twdinspection.gcc.reports.source.ReportPhoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspReportData {
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("mandal_id")
    @Expose
    private String mandalId;
    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("academic_overview")
    @Expose
    private AcademicOverview academicOverview;
    @SerializedName("coCurricular_info")
    @Expose
    private CoCurricularInfo coCurricularInfo;
    @SerializedName("diet_issues")
    @Expose
    private DietIssues dietIssues;
    @SerializedName("entitlements")
    @Expose
    private Entitlements entitlements;
    @SerializedName("general_comments")
    @Expose
    private GeneralComments generalComments;
    @SerializedName("general_info")
    @Expose
    private GeneralInfo generalInfo;
    @SerializedName("infra_maintenance")
    @Expose
    private InfraMaintenance infraMaintenance;
    @SerializedName("medical_issues")
    @Expose
    private MedicalIssues medicalIssues;
    @SerializedName("registers")
    @Expose
    private Registers registers;
    @SerializedName("staff_attendence_info")
    @Expose
    private List<StaffAttendenceInfo> staffAttendenceInfo = null;
    @SerializedName("student_attendence_info")
    @Expose
    private List<StudentAttendenceInfo> studentAttendenceInfo = null;
    @SerializedName("photos")
    @Expose
    private List<ReportPhoto> photos = null;

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getMandalId() {
        return mandalId;
    }

    public void setMandalId(String mandalId) {
        this.mandalId = mandalId;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public AcademicOverview getAcademicOverview() {
        return academicOverview;
    }

    public void setAcademicOverview(AcademicOverview academicOverview) {
        this.academicOverview = academicOverview;
    }

    public CoCurricularInfo getCoCurricularInfo() {
        return coCurricularInfo;
    }

    public void setCoCurricularInfo(CoCurricularInfo coCurricularInfo) {
        this.coCurricularInfo = coCurricularInfo;
    }

    public DietIssues getDietIssues() {
        return dietIssues;
    }

    public void setDietIssues(DietIssues dietIssues) {
        this.dietIssues = dietIssues;
    }

    public Entitlements getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(Entitlements entitlements) {
        this.entitlements = entitlements;
    }

    public GeneralComments getGeneralComments() {
        return generalComments;
    }

    public void setGeneralComments(GeneralComments generalComments) {
        this.generalComments = generalComments;
    }

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
    }

    public InfraMaintenance getInfraMaintenance() {
        return infraMaintenance;
    }

    public void setInfraMaintenance(InfraMaintenance infraMaintenance) {
        this.infraMaintenance = infraMaintenance;
    }

    public MedicalIssues getMedicalIssues() {
        return medicalIssues;
    }

    public void setMedicalIssues(MedicalIssues medicalIssues) {
        this.medicalIssues = medicalIssues;
    }

    public Registers getRegisters() {
        return registers;
    }

    public void setRegisters(Registers registers) {
        this.registers = registers;
    }

    public List<StaffAttendenceInfo> getStaffAttendenceInfo() {
        return staffAttendenceInfo;
    }

    public void setStaffAttendenceInfo(List<StaffAttendenceInfo> staffAttendenceInfo) {
        this.staffAttendenceInfo = staffAttendenceInfo;
    }

    public List<StudentAttendenceInfo> getStudentAttendenceInfo() {
        return studentAttendenceInfo;
    }

    public void setStudentAttendenceInfo(List<StudentAttendenceInfo> studentAttendenceInfo) {
        this.studentAttendenceInfo = studentAttendenceInfo;
    }

    public List<ReportPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ReportPhoto> photos) {
        this.photos = photos;
    }
}
