package com.cgg.twdinspection.inspection.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cgg.twdinspection.inspection.source.diet_issues.MasterDietListInfo;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
//

@Dao
public interface SchoolSyncDao {

    @Query("DELETE FROM districts_info")
    void deleteSchoolDistricts();

    @Insert
    void insertSchoolDistricts(List<SchoolDistrict> districtEntities);

    @Query("SELECT COUNT(*) FROM districts_info")
    int districtCount();

    @Query("DELETE FROM mandal_info")
    void deleteSchoolMandals();

    @Insert
    void insertSchoolMandals(List<SchoolMandal> mandalEntities);

    @Query("SELECT COUNT(*) FROM mandal_info")
    int mandalCount();

    @Query("DELETE FROM village_info")
    void deleteSchoolVillage();

    @Insert
    void insertSchoolVillages(List<SchoolVillage> villageEntities);

    @Query("SELECT COUNT(*) FROM village_info")
    int villageCount();

    @Query("DELETE FROM master_inst_info")
    void deleteMasterInst();

    @Insert
    void insertMasterInst(List<MasterInstituteInfo> masterInstituteInfos);

    @Query("SELECT COUNT(*) FROM master_inst_info")
    int instCount();

    @Query("DELETE FROM master_diet_info")
    void deleteMasterDietList();

    @Insert
    void insertMasterDietList(List<MasterDietListInfo> masterDietListInfos);

    @Query("SELECT COUNT(*) FROM master_diet_info")
    int DietCount();

    @Query("UPDATE master_inst_info SET classInfo = :masterClassInfos WHERE instId LIKE :inst_id")
    int updateStudentMasterInfo(String masterClassInfos, String inst_id);

    @Query("UPDATE master_inst_info SET staffInfo = :masterStaffInfos WHERE instId LIKE :inst_id")
    void updateStaffMasterInfo(String masterStaffInfos, String inst_id);

    @Query("UPDATE master_diet_info SET dietInfo = :masterDietInfos WHERE instId LIKE :inst_id")
    void updateDietMasterInfo(String masterDietInfos, String inst_id);

}
