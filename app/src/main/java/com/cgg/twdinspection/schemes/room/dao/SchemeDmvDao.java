package com.cgg.twdinspection.schemes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.dmv.SchemeMandal;
import com.cgg.twdinspection.schemes.source.dmv.SchemeVillage;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;

import java.util.List;


@Dao
public interface SchemeDmvDao {

    @Query("SELECT * from FinancialYearsEntity")
    LiveData<List<FinancialYearsEntity>> getFinancialYrs();

    @Query("SELECT * from SchemeDistrict")
    LiveData<List<SchemeDistrict>> getDistricts();

    @Query("SELECT distId from SchemeDistrict where distName LIKE :dist_name")
    LiveData<String> getDistId(String dist_name);

    @Query("SELECT mandalID from SchemeMandal where mandalName LIKE :mandalName AND distId LIKE :distId")
    LiveData<String> getMandalId(String mandalName, String distId);

    @Query("SELECT villageID from SchemeVillage where villageName LIKE :villageName AND mandalID LIKE :manId AND distId LIKE :distId")
    LiveData<String> getVillageId(String villageName, String manId, String distId);

    @Query("SELECT finId from FinancialYearsEntity where finYear LIKE :finYear")
    LiveData<String> getFinYearId(String finYear);

    @Query("SELECT * from SchemeMandal where distId LIKE :dist_id")
    LiveData<List<SchemeMandal>> getMandals(String dist_id);

    @Query("SELECT * from SchemeVillage where mandalID LIKE :mandalId AND distId LIKE :distId")
    LiveData<List<SchemeVillage>> getVillages(String mandalId, String distId);

}
