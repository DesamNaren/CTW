package com.example.twdinspection.schemes.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
public interface SchemeSyncDao {

    @Query("DELETE FROM SchemeDistrict")
    void deleteSchemeDistricts();

    @Insert
    void insertSchemeDistricts(List<SchemeDistrict> districtEntities);

    @Query("SELECT COUNT(*) FROM SchemeDistrict")
    int districtCount();

    @Query("DELETE FROM SchemeMandal")
    void deleteSchemeMandals();

    @Insert
    void insertSchemeMandals(List<SchemeMandal> mandalEntities);

    @Query("SELECT COUNT(*) FROM SchemeMandal")
    int mandalCount();

    @Query("DELETE FROM SchemeVillage")
    void deleteSchemeVillage();

    @Insert
    void insertSchemeVillages(List<SchemeVillage> villageEntities);

    @Query("SELECT COUNT(*) FROM SchemeVillage")
    int villageCount();

    @Query("DELETE FROM FinancialYearsEntity")
    void deleteFinYears();

    @Insert
    void insertFinYears(List<FinancialYearsEntity> financialYrsEntities);

    @Query("SELECT COUNT(*) FROM FinancialYearsEntity")
    int finYearCount();

    @Query("DELETE FROM InspectionRemarksEntity")
    void deleteInsRemarks();

    @Insert
    void insertInsRemark(List<InspectionRemarksEntity> inspectionRemarksEntities);

    @Query("SELECT COUNT(*) FROM InspectionRemarksEntity")
    int insRemarksCount();

    @Query("DELETE FROM SchemeEntity")
    void deleteschemes();

    @Insert
    void insertSchemes(List<SchemeEntity> schemeEntities);

    @Query("SELECT COUNT(*) FROM SchemeEntity")
    int schemeCount();
}
