package com.example.twdinspection.schemes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYrsEntity;

import java.util.List;

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
//
//@Dao
//public interface SchemeDmvDao {
//    @Query("SELECT * from Districts")
//    LiveData<List<Districts>> getDistricts();
//


//    @Query("SELECT count(*) from Districts")
//    int getCount();
//}


@Dao
public interface SchemeDmvDao {

    @Query("SELECT * from financialyrsentity")
    LiveData<List<FinancialYrsEntity>> getFinancialYrs();

    @Query("SELECT * from SchemeDistrict")
    LiveData<List<SchemeDistrict>> getDistricts();

    @Query("SELECT distId from SchemeDistrict where distName LIKE :dist_name")
    LiveData<String> getDistId(String dist_name);

    @Query("SELECT mandalID from SchemeMandal where mandalName LIKE :mandalName AND distId LIKE :distId")
    LiveData<String> getMandalId(String mandalName, String distId);

    @Query("SELECT villageID from SchemeVillage where villageName LIKE :villageName AND mandalID LIKE :manId AND distId LIKE :distId")
    LiveData<String> getVillageId(String villageName, String manId, String distId);

    @Query("SELECT finId from FinancialYrsEntity where finYear LIKE :finYear")
    LiveData<String> getFinYearId(String finYear);

    @Query("SELECT * from SchemeMandal where distId LIKE :dist_id")
    LiveData<List<SchemeMandal>> getMandals(String dist_id);

    @Query("SELECT * from SchemeVillage where mandalID LIKE :mandalId AND distId LIKE :distId")
    LiveData<List<SchemeVillage>> getVillages(String mandalId, String distId);

}
