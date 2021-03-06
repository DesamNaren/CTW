package com.cgg.twdinspection.engineering_works.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.engineering_works.source.SectorsEntity;

import java.util.List;

@Dao
public interface SectorsDao {
    @Insert
    void insertSectors(List<SectorsEntity> sectorsEntities);

    @Query("SELECT * from sectorsentity")
    LiveData<List<SectorsEntity>> getSectors();

    @Query("SELECT COUNT(*) FROM sectorsentity")
    int sectorCount();

    @Query("SELECT sectorId from sectorsentity where sectorName LIKE :sectorName")
    LiveData<Integer> getSectorId(String sectorName);


}
