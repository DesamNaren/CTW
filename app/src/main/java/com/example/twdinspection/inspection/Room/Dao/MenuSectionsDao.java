package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.example.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface MenuSectionsDao {

    @Insert()
    void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities);

    @Query("SELECT * from inst_menu_info")
    LiveData<List<InstMenuInfoEntity>> getSections();

    @Query("Update inst_menu_info set flag_completed=1, section_time=:time where section_id=:id and instId=:instId")
    void updateSectionInfo(String time, int id, String instId);

    @Query("SELECT section_id from inst_menu_info WHERE section_short_name=:name")
    LiveData<Integer> getSectionId(String name);

    @Query("Select count(*) from inst_menu_info")
    LiveData<Integer> getMenuRecordsCount();

    @Query("SELECT * from general_info")
    LiveData<GeneralInfoEntity> getGeneralInfo();

    @Query("SELECT * from class_info")
    LiveData<List<StudAttendInfoEntity>> getStudAttendInfo();

    @Query("SELECT * from staff_info")
    LiveData<List<StaffAttendanceEntity>> getStaffAttendInfo();

    @Query("SELECT * from medical_info")
    LiveData<MedicalInfoEntity> getMedicalInfo();

    @Query("SELECT * from diet_issues_info")
    LiveData<DietIssuesEntity> getDietInfo();

    @Query("SELECT * from infrastructure_info")
    LiveData<InfraStructureEntity> getInfraInfo();

    @Query("SELECT * from academic_info")
    LiveData<AcademicEntity> getAcademicInfo();

    @Query("SELECT * from cocurricular_info")
    LiveData<CoCurricularEntity> getCocurricularInfo();

    @Query("SELECT * from entitlements_info")
    LiveData<EntitlementsEntity> getEntitlementInfo();

    @Query("SELECT * from registers_info")
    LiveData<RegistersEntity> getRegisterInfo();

    @Query("SELECT * from general_comments_info")
    LiveData<GeneralCommentsEntity> getGeneralCommentsInfo();


    @Query("Delete from academic_info")
    void deleteAcademicEntity();

    @Query("Delete from academic_grade")
    void deleteAcademicGradeEntity();

    @Query("Delete from call_health_info")
    void deleteCallHealthInfoEntity();

    @Query("Delete from class_info")
    void deleteClassInfo();
    @Query("Delete from cocurricular_info")
    void deleteCoCurricularEntity();


    @Query("Delete from diet_issues_info")
    void deleteDietIssuesInfo();
    @Query("Delete from diet_list_info")
    void deleteDietListInfo();



    @Query("Delete from entitlements_info")
    void deleteEntitlementsInfo();
    @Query("Delete from general_comments_info")
    void deleteGeneralCommentsInfo();


    @Query("Delete from general_info")
    void deleteGeneralInfo();
    @Query("Delete from infrastructure_info")
    void deleteInfraStructureInfo();



    @Query("Delete from inst_menu_info")
    void deleteInstMenuInfoEntity();

    @Query("Delete from medical_details_info")
    void deleteMedicalDetailsBean();


    @Query("Delete from medical_info")
    void deleteMedicalInfoEntity();
    @Query("Delete from plants_info")
    void deletePlantsEntity();



    @Query("Delete from registers_info")
    void deleteRegistersInfo();

    @Query("Delete from staff_info")
    void deleteStaff_Info();


    @Query("Delete from stud_achievements_info")
    void deleteStudAchievementEntity();

    @Query("Delete from photos")
    void deletePhotos();

}
