package com.example.twdinspection.inspection.source.submit;

import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

public class InstSubmitRequest {
    private String officer_id;

    private String inspection_time;

    private String institute_id;

    private String dist_id;

    private String mandal_id;

    private String village_id;

    GeneralInfoEntity general_info;
    List<StudAttendInfoEntity> student_attendence_info;
    List<StaffAttendanceEntity> staff_attendence_info;
    MedicalInfoEntity medical_issues;
    DietIssuesEntity diet_issues;
    InfraStructureEntity infra_maintenance;
    AcademicEntity academic_overview;
    CoCurricularEntity coCurricular_info;
    EntitlementsEntity entitlements;
    RegistersEntity registers;
    GeneralCommentsEntity general_comments;

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getMandal_id() {
        return mandal_id;
    }

    public void setMandal_id(String mandal_id) {
        this.mandal_id = mandal_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }


    public GeneralInfoEntity getGeneral_info() {
        return general_info;
    }

    public void setGeneral_info(GeneralInfoEntity general_info) {
        this.general_info = general_info;
    }

    public List<StudAttendInfoEntity> getStudent_attendence_info() {
        return student_attendence_info;
    }

    public void setStudent_attendence_info(List<StudAttendInfoEntity> student_attendence_info) {
        this.student_attendence_info = student_attendence_info;
    }

    public List<StaffAttendanceEntity> getStaff_attendence_info() {
        return staff_attendence_info;
    }

    public void setStaff_attendence_info(List<StaffAttendanceEntity> staff_attendence_info) {
        this.staff_attendence_info = staff_attendence_info;
    }

    public MedicalInfoEntity getMedical_issues() {
        return medical_issues;
    }

    public void setMedical_issues(MedicalInfoEntity medical_issues) {
        this.medical_issues = medical_issues;
    }

    public DietIssuesEntity getDiet_issues() {
        return diet_issues;
    }

    public void setDiet_issues(DietIssuesEntity diet_issues) {
        this.diet_issues = diet_issues;
    }

    public InfraStructureEntity getInfra_maintenance() {
        return infra_maintenance;
    }

    public void setInfra_maintenance(InfraStructureEntity infra_maintenance) {
        this.infra_maintenance = infra_maintenance;
    }

    public AcademicEntity getAcademic_overview() {
        return academic_overview;
    }

    public void setAcademic_overview(AcademicEntity academic_overview) {
        this.academic_overview = academic_overview;
    }

    public CoCurricularEntity getCoCurricular_info() {
        return coCurricular_info;
    }

    public void setCoCurricular_info(CoCurricularEntity coCurricular_info) {
        this.coCurricular_info = coCurricular_info;
    }

    public EntitlementsEntity getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(EntitlementsEntity entitlements) {
        this.entitlements = entitlements;
    }

    public RegistersEntity getRegisters() {
        return registers;
    }

    public void setRegisters(RegistersEntity registers) {
        this.registers = registers;
    }

    public GeneralCommentsEntity getGeneral_comments() {
        return general_comments;
    }

    public void setGeneral_comments(GeneralCommentsEntity general_comments) {
        this.general_comments = general_comments;
    }
}

