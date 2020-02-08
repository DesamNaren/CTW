package com.example.twdinspection.gcc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface GCCDao {
    @Query("SELECT DISTINCT divisionName from Divisions")
    LiveData<List<String>> getDivisionsInfo();

    @Query("SELECT * from Divisions where divisionId LIKE :divID")
    LiveData<List<DivisionsInfo>> getSocietyInfo(String divID);


    @Query("SELECT * from DR_GoDown where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<DrGodowns>> getDrGoDowns(String divId, String socId);

    @Query("SELECT * from DR_Depot where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<DRDepots>> getDRDepots(String divId, String socId);

    @Query("SELECT * from MFP_GoDown where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<MFPGoDowns>> getMFPDepots(String divId, String socId);

    @Query("SELECT * from P_Unit where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<PUnits>> getPUnits(String divId, String socId);

    @Query("SELECT divisionId from Divisions where divisionName LIKE :divisionName")
    LiveData<String> getDivisionID(String divisionName);


    @Query("SELECT societyId from Divisions where  divisionId LIKE :divisionID AND societyName LIKE :societyName")
    LiveData<String> getSocietyID(String divisionID, String societyName);

    @Query("SELECT * from DR_GoDown where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :goDownName")
    LiveData<DrGodowns> getGoDownID(String divisionID, String societyID, String goDownName);


    @Query("SELECT * from DR_Depot where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :depotName")
    LiveData<DRDepots> getDRDepotID(String divisionID, String societyID, String depotName);

    @Query("SELECT * from MFP_GoDown where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :mfpName")
    LiveData<MFPGoDowns> getMFPGoDownID(String divisionID, String societyID, String mfpName);

    @Query("SELECT * from P_Unit where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :pUnitNmae")
    LiveData<PUnits> getPUnitID(String divisionID, String societyID, String pUnitNmae);
}
