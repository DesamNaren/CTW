package com.example.twdinspection.engineeringWorks.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.engineeringWorks.source.SectorsEntity;

import java.util.List;

@Dao
public interface SectorsDao {
    @Insert
    void insertSectors(List<SectorsEntity> sectorsEntities);


    @Query("SELECT COUNT(*) FROM sectors")
    int sectorCount();

}
