package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;

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
public interface SchoolsDaoOffline {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchoolRecord(SchoolsOfflineEntity schoolsOfflineEntity);

    @Query("SELECT COUNT(*) FROM Schools_Offline")
    int schoolRecCount();

    @Query("DELETE FROM Schools_Offline where inst_id LIKE :instId")
    int deleteSchoolsRecord(String instId);


    @Query("DELETE FROM Schools_Offline where inst_time LIKE :time")
    int deletePreviousDayRecords(String time);

    @Query("SELECT * from Schools_Offline")
    LiveData<List<SchoolsOfflineEntity>> getSchoolsRecords();

}
