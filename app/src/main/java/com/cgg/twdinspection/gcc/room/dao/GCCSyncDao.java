package com.cgg.twdinspection.gcc.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
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
//

@Dao
public interface GCCSyncDao {

    @Query("DELETE FROM Divisions")
    void deleteDivisions();

    @Insert
    void insertDivisions(List<DivisionsInfo> divisionsInfos);

    @Query("SELECT COUNT(*) FROM Divisions")
    int divisionCount();

    @Query("DELETE FROM DR_Depot")
    void deleteDRDepots();

    @Insert
    void insertDRDepots(List<DRDepots> DRDepots);

    @Query("SELECT COUNT(*) FROM DR_Depot")
    int drDepotCount();


    @Query("DELETE FROM DR_GoDown")
    void deleteDRGoDowns();

    @Insert
    void insertDRGoDowns(List<DrGodowns> drGodowns);

    @Query("SELECT COUNT(*) FROM DR_GoDown")
    int drGoDownCount();

    @Query("DELETE FROM MFP_GoDown")
    void deleteMFPDowns();

    @Insert
    void insertMFPGoDowns(List<MFPGoDowns> mfpGoDowns);

    @Query("SELECT COUNT(*) FROM MFP_GoDown")
    int mfpGoDownCount();

    @Query("DELETE FROM P_Unit")
    void deletePUnits();

    @Insert
    void insertPUnits(List<PUnits> pUnits);

    @Query("SELECT COUNT(*) FROM P_Unit")
    int pUnitCount();
}
