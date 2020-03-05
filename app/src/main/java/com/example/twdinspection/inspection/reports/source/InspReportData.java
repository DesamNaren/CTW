package com.example.twdinspection.inspection.reports.source;

import com.example.twdinspection.gcc.reports.source.ReportPhoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspReportData {
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("village_name")
    @Expose
    private String villageName;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("mandal_name")
    @Expose
    private String mandalName;
    @SerializedName("dist_name")
    @Expose
    private String distName;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("mandal_id")
    @Expose
    private String mandalId;
    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("institute_name")
    @Expose
    private String instituteName;
    @SerializedName("general_info")
    @Expose
    private GeneralInfo generalInfo;
    @SerializedName("entitlements")
    @Expose
    private Entitlements entitlements;
    @SerializedName("diet_issues")
    @Expose
    private DietIssues dietIssues;
    @SerializedName("academic_overview")
    @Expose
    private AcademicOverview academicOverview;
    @SerializedName("medical_issues")
    @Expose
    private MedicalIssues medicalIssues;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("infra_maintenance")
    @Expose
    private InfraMaintenance infraMaintenance;
    @SerializedName("staff_attendence_info")
    @Expose
    private List<StaffAttendenceInfo> staffAttendenceInfo = null;
    @SerializedName("general_comments")
    @Expose
    private GeneralComments generalComments;
    @SerializedName("coCurricular_info")
    @Expose
    private CoCurricularInfo coCurricularInfo = null;
    @SerializedName("registers")
    @Expose
    private Registers registers;
    @SerializedName("student_attendence_info")
    @Expose
    private List<StudentAttendenceInfo> studentAttendenceInfo = null;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
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

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        this.generalInfo = generalInfo;
    }

    public Entitlements getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(Entitlements entitlements) {
        this.entitlements = entitlements;
    }

    public DietIssues getDietIssues() {
        return dietIssues;
    }

    public void setDietIssues(DietIssues dietIssues) {
        this.dietIssues = dietIssues;
    }

    public AcademicOverview getAcademicOverview() {
        return academicOverview;
    }

    public void setAcademicOverview(AcademicOverview academicOverview) {
        this.academicOverview = academicOverview;
    }

    public MedicalIssues getMedicalIssues() {
        return medicalIssues;
    }

    public void setMedicalIssues(MedicalIssues medicalIssues) {
        this.medicalIssues = medicalIssues;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public InfraMaintenance getInfraMaintenance() {
        return infraMaintenance;
    }

    public void setInfraMaintenance(InfraMaintenance infraMaintenance) {
        this.infraMaintenance = infraMaintenance;
    }

    public List<StaffAttendenceInfo> getStaffAttendenceInfo() {
        return staffAttendenceInfo;
    }

    public void setStaffAttendenceInfo(List<StaffAttendenceInfo> staffAttendenceInfo) {
        this.staffAttendenceInfo = staffAttendenceInfo;
    }

    public GeneralComments getGeneralComments() {
        return generalComments;
    }

    public void setGeneralComments(GeneralComments generalComments) {
        this.generalComments = generalComments;
    }

    public CoCurricularInfo getCoCurricularInfo() {
        return coCurricularInfo;
    }

    public void setCoCurricularInfo(CoCurricularInfo coCurricularInfo) {
        this.coCurricularInfo = coCurricularInfo;
    }

    public Registers getRegisters() {
        return registers;
    }

    public void setRegisters(Registers registers) {
        this.registers = registers;
    }

    public List<StudentAttendenceInfo> getStudentAttendenceInfo() {
        return studentAttendenceInfo;
    }

    public void setStudentAttendenceInfo(List<StudentAttendenceInfo> studentAttendenceInfo) {
        this.studentAttendenceInfo = studentAttendenceInfo;
    }
}
