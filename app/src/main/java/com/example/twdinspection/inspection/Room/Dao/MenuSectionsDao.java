package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface MenuSectionsDao {
    @Insert()
    void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities);

    @Query("SELECT * from InstMenuInfoEntity")
    LiveData<List<InstMenuInfoEntity>> getSections();

    @Query("Update InstMenuInfoEntity set flag_completed=1, section_time=:time where section_id=:id and instId=:instId")
    void updateSectionInfo(String time, int id, String instId);

    @Query("SELECT section_id from InstMenuInfoEntity WHERE section_short_name=:name")
    LiveData<Integer> getSectionId(String name);

    @Query("Select count(*) from InstMenuInfoEntity")
    LiveData<Integer> getMenuRecordsCount();

    @Query("SELECT * from GeneralInfo")
    LiveData<GeneralInfoEntity> getGeneralInfo();

    @Query("SELECT * from ClassInfo")
    LiveData<List<StudAttendInfoEntity>> getStudAttendInfo();

    @Query("SELECT * from Staff_Info")
    LiveData<List<StaffAttendanceEntity>> getStaffAttendInfo();

    @Query("SELECT * from medicalinfoentity")
    LiveData<MedicalInfoEntity> getMedicalInfo();

    @Query("SELECT * from DietIssuesInfo")
    LiveData<DietIssuesEntity> getDietInfo();

    @Query("SELECT * from InfraStructureInfo")
    LiveData<InfraStructureEntity> getInfraInfo();

    @Query("SELECT * from AcademicEntity")
    LiveData<AcademicEntity> getAcademicInfo();

    @Query("SELECT * from cocurricularentity")
    LiveData<CoCurricularEntity> getCocurricularInfo();

    @Query("SELECT * from EntitlementsInfo")
    LiveData<EntitlementsEntity> getEntitlementInfo();

    @Query("SELECT * from RegistersInfo")
    LiveData<RegistersEntity> getRegisterInfo();

    @Query("SELECT * from GeneralCommentsInfo")
    LiveData<GeneralCommentsEntity> getGeneralCommentsInfo();


    @Query("Delete from AcademicEntity")
    void deleteAcademicEntity();
    @Query("Delete from CallHealthInfoEntity")
    void deleteCallHealthInfoEntity();

    @Query("Delete from ClassInfo")
    void deleteClassInfo();
    @Query("Delete from CoCurricularEntity")
    void deleteCoCurricularEntity();


    @Query("Delete from DietIssuesInfo")
    void deleteDietIssuesInfo();
    @Query("Delete from DietListInfo")
    void deleteDietListInfo();



    @Query("Delete from EntitlementsInfo")
    void deleteEntitlementsInfo();
    @Query("Delete from GeneralCommentsInfo")
    void deleteGeneralCommentsInfo();


    @Query("Delete from GeneralInfo")
    void deleteGeneralInfo();
    @Query("Delete from InfraStructureInfo")
    void deleteInfraStructureInfo();



    @Query("Delete from InstMenuInfoEntity")
    void deleteInstMenuInfoEntity();
    @Query("Delete from MedicalDetailsBean")
    void deleteMedicalDetailsBean();


    @Query("Delete from MedicalInfoEntity")
    void deleteMedicalInfoEntity();
    @Query("Delete from PlantsEntity")
    void deletePlantsEntity();



    @Query("Delete from RegistersInfo")
    void deleteRegistersInfo();
    @Query("Delete from Staff_Info")
    void deleteStaff_Info();


    @Query("Delete from StudAchievementEntity")
    void deleteStudAchievementEntity();



}
