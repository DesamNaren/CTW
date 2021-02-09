package com.cgg.twdinspection.gcc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;

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
public interface GCCDaoOffline {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGCCRecord(GccOfflineEntity GCCOfflineEntity);

    @Query("SELECT COUNT(*) FROM GCC_Offline")
    int gccRecCount();

    @Query("DELETE FROM GCC_Offline where divisionId LIKE :divId AND societyId LIKE :socId AND drgownId LIKE :godownId")
    int deleteGCCRecord(String divId, String socId, String godownId);

    @Query("SELECT * from GCC_Offline where divisionId LIKE :divId AND societyId LIKE :socId AND drgownId LIKE :godownId")
    LiveData<GccOfflineEntity> getGCCRecords(String divId, String socId, String godownId);

    @Query("SELECT * from GCC_Offline where  divisionId LIKE :divId AND drgownId LIKE :godownId OR societyId LIKE :socId ")
    LiveData<GccOfflineEntity> getGCCRecordsPUnit(String divId, String socId, String godownId);

    @Query("SELECT * from GCC_Offline where type LIKE :type AND flag = 1")
    LiveData<List<GccOfflineEntity>> getGccRecCount(String type);

}
