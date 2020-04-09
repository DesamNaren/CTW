package com.cgg.twdinspection.engineering_works.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

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
public interface EngWorksSyncDao {

    @Query("DELETE FROM sectorsentity")
    void deleteSectors();

    @Insert
    void insertSectors(List<SectorsEntity> sectorsEntities);

    @Query("SELECT COUNT(*) FROM SectorsEntity")
    int sectorsCount();

    @Query("DELETE FROM grantSchemes")
    void deleteSchemes();

    @Insert
    void insertEngSchemes(List<GrantScheme> schemeEntities);

    @Query("SELECT COUNT(*) FROM grantSchemes")
    int grantSchemesCnt();

    @Query("DELETE FROM workdetail")
    void deleteWorkDetails();

    @Insert
    void insertWorkDetails(List<WorkDetail> workDetails);

    @Query("SELECT COUNT(*) FROM workdetail")
    int worksCount();
}
