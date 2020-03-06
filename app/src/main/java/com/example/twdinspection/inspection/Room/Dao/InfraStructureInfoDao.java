package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;

@Dao
public interface InfraStructureInfoDao {

    @Insert()
    void insertInfraStructureInfo(InfraStructureEntity infrastuctureEntity);

}
