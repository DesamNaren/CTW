package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface MenuSectionsDao {

    @Insert()
    void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities);

    @Query("SELECT * from inst_menu_info where instId LIKE:inst_id")
    LiveData<List<InstMenuInfoEntity>> getSections(String inst_id);

    @Query("Update inst_menu_info set flag_completed=1, section_time=:time where section_id=:id and instId=:instId")
    void updateSectionInfo(String time, int id, String instId);

    @Query("SELECT section_id from inst_menu_info WHERE section_short_name=:name")
    LiveData<Integer> getSectionId(String name);

    @Query("Select count(*) from inst_menu_info")
    LiveData<Integer> getMenuRecordsCount();

    @Query("SELECT * from general_info where institute_id LIKE:inst_id")
    LiveData<GeneralInfoEntity> getGeneralInfo(String inst_id);

    @Query("SELECT * from class_info where institute_id LIKE:inst_id")
    LiveData<List<StudAttendInfoEntity>> getStudAttendInfo(String inst_id);

    @Query("SELECT * from staff_info where institute_id LIKE:inst_id")
    LiveData<List<StaffAttendanceEntity>> getStaffAttendInfo(String inst_id);

    @Query("SELECT * from medical_info where institute_id LIKE:inst_id")
    LiveData<MedicalInfoEntity> getMedicalInfo(String inst_id);

    @Query("SELECT * from diet_issues_info where institute_id LIKE:inst_id")
    LiveData<DietIssuesEntity> getDietInfo(String inst_id);

    @Query("SELECT * from infrastructure_info where institute_id LIKE:inst_id")
    LiveData<InfraStructureEntity> getInfraInfo(String inst_id);

    @Query("SELECT * from academic_info where institute_id LIKE:inst_id")
    LiveData<AcademicEntity> getAcademicInfo(String inst_id);

    @Query("SELECT * from cocurricular_info where institute_id LIKE:inst_id")
    LiveData<CoCurricularEntity> getCocurricularInfo(String inst_id);

    @Query("SELECT * from entitlements_info where institute_id LIKE:inst_id")
    LiveData<EntitlementsEntity> getEntitlementInfo(String inst_id);

    @Query("SELECT * from registers_info where institute_id LIKE:inst_id")
    LiveData<RegistersEntity> getRegisterInfo(String inst_id);

    @Query("SELECT * from general_comments_info where institute_id LIKE:inst_id")
    LiveData<GeneralCommentsEntity> getGeneralCommentsInfo(String inst_id);


    @Query("Delete from academic_info where institute_id LIKE:inst_id")
    void deleteAcademicEntity(String inst_id);

    @Query("Delete from inst_selection_info where inst_id LIKE:inst_id")
    void deleteInstSelectionInfo(String inst_id);

    @Query("Delete from academic_grade where institute_id LIKE:inst_id")
    void deleteAcademicGradeEntity(String inst_id);

    @Query("Delete from call_health_info")
    void deleteCallHealthInfoEntity();

    @Query("Delete from class_info where institute_id LIKE:inst_id")
    void deleteClassInfo(String inst_id);

    @Query("Delete from cocurricular_info where institute_id LIKE:inst_id")
    void deleteCoCurricularEntity(String inst_id);


    @Query("Delete from diet_issues_info where institute_id LIKE:inst_id")
    void deleteDietIssuesInfo(String inst_id);

    @Query("Delete from diet_list_info where institute_id LIKE:inst_id")
    void deleteDietListInfo(String inst_id);


    @Query("Delete from entitlements_info where institute_id LIKE:inst_id")
    void deleteEntitlementsInfo(String inst_id);

    @Query("Delete from general_comments_info where institute_id LIKE:inst_id")
    void deleteGeneralCommentsInfo(String inst_id);


    @Query("Delete from general_info where institute_id LIKE:inst_id")
    void deleteGeneralInfo(String inst_id);

    @Query("Delete from infrastructure_info where institute_id LIKE:inst_id")
    void deleteInfraStructureInfo(String inst_id);


    @Query("Delete from inst_menu_info where instId LIKE:inst_id")
    void deleteInstMenuInfoEntity(String inst_id);

    @Query("Delete from medical_details_info")
    void deleteMedicalDetailsBean();


    @Query("Delete from medical_info where institute_id LIKE:inst_id")
    void deleteMedicalInfoEntity(String inst_id);

    @Query("Delete from plants_info")
    void deletePlantsEntity();


    @Query("Delete from registers_info where institute_id LIKE:inst_id")
    void deleteRegistersInfo(String inst_id);

    @Query("Delete from staff_info where institute_id LIKE:inst_id")
    void deleteStaff_Info(String inst_id);


    @Query("Delete from stud_achievements_info")
    void deleteStudAchievementEntity();

    @Query("Delete from photos where institute_id LIKE:inst_id")
    void deletePhotos(String inst_id);

}
