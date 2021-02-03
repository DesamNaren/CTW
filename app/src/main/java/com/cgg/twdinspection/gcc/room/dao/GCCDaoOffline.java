package com.cgg.twdinspection.gcc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.offline.drgodown.DrGodownOffline;

/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface GCCDaoOffline {

    @Insert
    void insertDRGodown(DrGodownOffline drGodownOffline);

    @Query("SELECT COUNT(*) FROM DR_GoDown_Offline")
    int DRGodownCount();

    @Query("DELETE FROM DR_GoDown_Offline where divisionId LIKE :divId AND societyId LIKE :socId AND drgownId LIKE :godownId")
    int deleteDRGodown(String divId, String socId, String godownId);

    @Query("SELECT * from DR_GoDown_Offline where divisionId LIKE :divId AND societyId LIKE :socId AND drgownId LIKE :godownId")
    LiveData<DrGodownOffline> getDrGoDowns(String divId, String socId, String godownId);

}
