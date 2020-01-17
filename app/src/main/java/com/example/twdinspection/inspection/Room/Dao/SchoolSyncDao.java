package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;
import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;

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

    @Query("DELETE FROM SchoolDistrict")
    void deleteSchoolDistricts();

    @Insert
    void insertSchoolDistricts(List<SchoolDistrict> districtEntities);

    @Query("SELECT COUNT(*) FROM SchoolDistrict")
    int districtCount();

    @Query("DELETE FROM SchoolMandal")
    void deleteSchoolMandals();

    @Insert
    void insertSchoolMandals(List<SchoolMandal> mandalEntities);

    @Query("SELECT COUNT(*) FROM SchoolMandal")
    int mandalCount();

    @Query("DELETE FROM SchoolVillage")
    void deleteSchoolVillage();

    @Insert
    void insertSchoolVillages(List<SchoolVillage> villageEntities);

    @Query("SELECT COUNT(*) FROM SchoolVillage")
    int villageCount();
}
