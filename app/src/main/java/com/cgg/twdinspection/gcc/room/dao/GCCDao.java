package com.cgg.twdinspection.gcc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

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

    @Query("SELECT * from Petrol_Pump where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<PetrolSupplierInfo>> getPetrolPumps(String divId, String socId);

    @Query("SELECT * from LPG where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<LPGSupplierInfo>> getLPGSuppliers(String divId, String socId);

    @Query("SELECT * from DR_GoDown")
    LiveData<List<DrGodowns>> getAllDrGoDowns();

    @Query("SELECT * from Divisions")
    LiveData<List<DivisionsInfo>> getAllDivisions();


    @Query("SELECT * from Petrol_Pump")
    LiveData<List<PetrolSupplierInfo>> getAllPetrolSuppliers();

    @Query("SELECT * from LPG")
    LiveData<List<LPGSupplierInfo>> getAllLPGSuppliers();

    @Query("SELECT * from DR_Depot where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<DRDepots>> getDRDepots(String divId, String socId);

    @Query("SELECT * from MFP_GoDown where divisionId LIKE :divId")
    LiveData<List<MFPGoDowns>> getMFPDepots(String divId);

    @Query("SELECT * from P_Unit where divisionId LIKE :divId AND societyId LIKE :socId")
    LiveData<List<PUnits>> getPUnits(String divId, String socId);

    @Query("SELECT * from DR_Depot")
    LiveData<List<DRDepots>> getAllDRDepots();

    @Query("SELECT * from MFP_GoDown")
    LiveData<List<MFPGoDowns>> getAllMFPDepots();

    @Query("SELECT * from P_Unit")
    LiveData<List<PUnits>> getAllPUnits();

    @Query("SELECT * from P_Unit where divisionId LIKE :divId")
    LiveData<List<PUnits>> getPUnitsDiv(String divId);

    @Query("SELECT divisionId from Divisions where divisionName LIKE :divisionName")
    LiveData<String> getDivisionID(String divisionName);


    @Query("SELECT societyId from Divisions where  divisionId LIKE :divisionID AND societyName LIKE :societyName")
    LiveData<String> getSocietyID(String divisionID, String societyName);

    @Query("SELECT * from DR_GoDown where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :goDownName")
    LiveData<DrGodowns> getGoDownID(String divisionID, String societyID, String goDownName);

    @Query("SELECT * from Petrol_Pump where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :goDownName")
    LiveData<PetrolSupplierInfo> getPetrolPumpID(String divisionID, String societyID, String goDownName);

    @Query("SELECT * from LPG where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :goDownName")
    LiveData<LPGSupplierInfo> getLPGID(String divisionID, String societyID, String goDownName);

    @Query("SELECT * from DR_Depot where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :depotName")
    LiveData<DRDepots> getDRDepotID(String divisionID, String societyID, String depotName);

    @Query("SELECT * from MFP_GoDown where  divisionId LIKE :divisionID AND godownName LIKE :mfpName")
    LiveData<MFPGoDowns> getMFPGoDownID(String divisionID, String mfpName);

    @Query("SELECT * from P_Unit where  divisionId LIKE :divisionID AND societyId LIKE :societyID AND godownName LIKE :pUnitNmae")
    LiveData<PUnits> getPUnitID(String divisionID, String societyID, String pUnitNmae);

    @Query("SELECT * from P_Unit where  divisionId LIKE :divisionID AND godownName LIKE :pUnitNmae")
    LiveData<PUnits> getPUnitID(String divisionID, String pUnitNmae);

}
