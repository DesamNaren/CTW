package com.example.twdinspection.gcc.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.DRDepots;

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
}
